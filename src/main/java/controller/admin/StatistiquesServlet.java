package controller.admin;

import dao.*;
import model.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class StatistiquesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private BilletDAO billetDAO;
    private VoyageDAO voyageDAO;
    private UserDAO userDAO;
    private TrajetDAO trajetDAO;
    private GareDAO gareDAO;
    
    public StatistiquesServlet() {
        super();
        billetDAO = new BilletDAO();
        voyageDAO = new VoyageDAO();
        userDAO = new UserDAO();
        trajetDAO = new TrajetDAO();
        gareDAO = new GareDAO();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null || !user.getRole().getNom().equals("ADMIN")) {
            response.sendRedirect(request.getContextPath() + "/auth?page=login");
            return;
        }
        
        try {
            // Statistiques générales
            generateGeneralStats(request);
            
            // Statistiques des ventes
            generateSalesStats(request);
            
            // Statistiques des trajets populaires
            generatePopularRoutes(request);
            
            // Statistiques des utilisateurs
            generateUserStats(request);
            
            // Statistiques financières
            generateFinancialStats(request);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/admin/statistiques.jsp");
            dispatcher.forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors du calcul des statistiques : " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/admin/statistiques.jsp");
            dispatcher.forward(request, response);
        }
    }
    
    private void generateGeneralStats(HttpServletRequest request) {
        Map<String, Object> generalStats = new HashMap<>();
        
        // Compter les entités principales
        generalStats.put("totalBillets", billetDAO.findAll().size());
        generalStats.put("totalVoyages", voyageDAO.findAll().size());
        generalStats.put("totalUtilisateurs", userDAO.findAll().size());
        generalStats.put("totalTrajets", trajetDAO.findAll().size());
        generalStats.put("totalGares", gareDAO.findAll().size());
        
        // Billets par état
        List<Billet> billets = billetDAO.findAll();
        Map<Billet.EtatBillet, Long> billetsParEtat = billets.stream()
            .collect(Collectors.groupingBy(Billet::getEtat, Collectors.counting()));
        
        generalStats.put("billetsAchetes", billetsParEtat.getOrDefault(Billet.EtatBillet.ACHETE, 0L));
        generalStats.put("billetsUtilises", billetsParEtat.getOrDefault(Billet.EtatBillet.UTILISE, 0L));
        generalStats.put("billetsAnnules", billetsParEtat.getOrDefault(Billet.EtatBillet.ANNULE, 0L));
        
        request.setAttribute("generalStats", generalStats);
    }
    
    private void generateSalesStats(HttpServletRequest request) {
        List<Billet> billets = billetDAO.findAll();
        
        // Ventes par jour (7 derniers jours)
        Map<String, Integer> ventesParJour = new LinkedHashMap<>();
        LocalDate today = LocalDate.now();
        
        for (int i = 6; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            String dateStr = date.format(DateTimeFormatter.ofPattern("dd/MM"));
            
            long ventesJour = billets.stream()
                .filter(b -> b.getDateAchat().toLocalDate().equals(date))
                .filter(b -> b.getEtat() != Billet.EtatBillet.ANNULE)
                .count();
            
            ventesParJour.put(dateStr, (int) ventesJour);
        }
        
        request.setAttribute("ventesParJour", ventesParJour);
        
        // Ventes par classe
        Map<String, Long> ventesParClasse = billets.stream()
            .filter(b -> b.getEtat() != Billet.EtatBillet.ANNULE)
            .collect(Collectors.groupingBy(
                b -> b.getClasse().getNom(),
                Collectors.counting()
            ));
        
        request.setAttribute("ventesParClasse", ventesParClasse);
    }
    
    private void generatePopularRoutes(HttpServletRequest request) {
        List<Billet> billets = billetDAO.findAll();
        
        // Trajets les plus populaires
        Map<String, Long> trajetsPopulaires = billets.stream()
            .filter(b -> b.getEtat() != Billet.EtatBillet.ANNULE)
            .filter(b -> b.getVoyage() != null && b.getVoyage().getTrajet() != null)
            .collect(Collectors.groupingBy(
                b -> {
                    Trajet trajet = b.getVoyage().getTrajet();
                    Gare depart = trajet.getGareDepart();
                    Gare arrivee = trajet.getGareArrivee();
                    if (depart != null && arrivee != null) {
                        return depart.getVille() + " → " + arrivee.getVille();
                    }
                    return "Route inconnue";
                },
                Collectors.counting()
            ));
        
        // Trier par popularité (top 5)
        List<Map.Entry<String, Long>> topTrajets = trajetsPopulaires.entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(5)
            .collect(Collectors.toList());
        
        request.setAttribute("trajetsPopulaires", topTrajets);
    }
    
    private void generateUserStats(HttpServletRequest request) {
        List<User> users = userDAO.findAll();
        
        Map<String, Object> userStats = new HashMap<>();
        
        // Utilisateurs par rôle
        Map<String, Long> usersParRole = users.stream()
            .collect(Collectors.groupingBy(
                u -> u.getRole().getNom(),
                Collectors.counting()
            ));
        
        userStats.put("usersParRole", usersParRole);
        
        // Utilisateurs actifs vs bloqués
        long usersActifs = users.stream().filter(User::isActif).count();
        long usersBloqués = users.stream().filter(u -> !u.isActif()).count();
        
        userStats.put("usersActifs", usersActifs);
        userStats.put("usersBloqués", usersBloqués);
        
        request.setAttribute("userStats", userStats);
    }
    
    private void generateFinancialStats(HttpServletRequest request) {
        List<Billet> billets = billetDAO.findAll();
        
        Map<String, Object> financialStats = new HashMap<>();
        
        // Revenus totaux
        double revenusTotal = billets.stream()
            .filter(b -> b.getEtat() != Billet.EtatBillet.ANNULE)
            .mapToDouble(Billet::getPrix)
            .sum();
        
        financialStats.put("revenusTotal", revenusTotal);
        
        // Revenus par classe
        Map<String, Double> revenusParClasse = billets.stream()
            .filter(b -> b.getEtat() != Billet.EtatBillet.ANNULE)
            .collect(Collectors.groupingBy(
                b -> b.getClasse().getNom(),
                Collectors.summingDouble(Billet::getPrix)
            ));
        
        financialStats.put("revenusParClasse", revenusParClasse);
        
        // Revenus du mois en cours
        LocalDate debutMois = LocalDate.now().withDayOfMonth(1);
        double revenusMois = billets.stream()
            .filter(b -> b.getEtat() != Billet.EtatBillet.ANNULE)
            .filter(b -> !b.getDateAchat().toLocalDate().isBefore(debutMois))
            .mapToDouble(Billet::getPrix)
            .sum();
        
        financialStats.put("revenusMois", revenusMois);
        
        // Prix moyen des billets
        double prixMoyen = billets.stream()
            .filter(b -> b.getEtat() != Billet.EtatBillet.ANNULE)
            .mapToDouble(Billet::getPrix)
            .average()
            .orElse(0.0);
        
        financialStats.put("prixMoyen", prixMoyen);
        
        request.setAttribute("financialStats", financialStats);
    }
}
