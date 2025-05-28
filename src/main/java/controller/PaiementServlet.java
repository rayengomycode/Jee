package controller;

import dao.BilletDAO;
import dao.ClasseDAO;
import dao.PaiementDAO;
import dao.VoyageDAO;
import model.*;
import service.NotificationService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

public class PaiementServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private VoyageDAO voyageDAO;
    private ClasseDAO classeDAO;
    private BilletDAO billetDAO;
    private PaiementDAO paiementDAO;

    public PaiementServlet() {
        super();
        voyageDAO = new VoyageDAO();
        classeDAO = new ClasseDAO();
        billetDAO = new BilletDAO();
        paiementDAO = new PaiementDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            // Rediriger vers la page de connexion
            response.sendRedirect(request.getContextPath() + "/auth?page=login");
            return;
        }

        String voyageId = (String) session.getAttribute("voyageId");
        String classeId = (String) session.getAttribute("classeId");

        if (voyageId == null || classeId == null) {
            response.sendRedirect(request.getContextPath() + "/recherche");
            return;
        }

        Voyage voyage = voyageDAO.findById(Long.parseLong(voyageId));
        Classe classe = classeDAO.findById(Long.parseLong(classeId));

        double prixTotal = voyage.getPrix() * classe.getCoefficientPrix();

        request.setAttribute("voyage", voyage);
        request.setAttribute("classe", classe);
        request.setAttribute("prixTotal", prixTotal);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/paiement.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/auth?page=login");
            return;
        }

        String voyageId = (String) session.getAttribute("voyageId");
        String classeId = (String) session.getAttribute("classeId");
        String preferences = (String) session.getAttribute("preferences");
        String methodePaiement = request.getParameter("methodePaiement");

        Voyage voyage = voyageDAO.findById(Long.parseLong(voyageId));
        Classe classe = classeDAO.findById(Long.parseLong(classeId));

        // Calculer le prix total
        double prixTotal = voyage.getPrix() * classe.getCoefficientPrix();

        // Générer un numéro de siège aléatoire (simplifié)
        String numeroSiege = "S" + (int)(Math.random() * 100);

        // Créer un nouveau billet
        Billet billet = new Billet(
            voyage,
            user,
            classe,
            numeroSiege,
            prixTotal,
            LocalDateTime.now(),
            Billet.EtatBillet.ACHETE,
            preferences
        );

        boolean success = billetDAO.create(billet);

        if (success) {
            // Mettre à jour le nombre de places disponibles
            voyageDAO.updatePlacesDisponibles(voyage.getId(), 1);

            // Créer un paiement
            Paiement paiement = new Paiement(
                billet,
                prixTotal,
                LocalDateTime.now(),
                Paiement.MethodePaiement.valueOf(methodePaiement),
                "PAY-" + UUID.randomUUID().toString().substring(0, 8),
                "Paiement pour le billet " + billet.getId()
            );

            paiementDAO.create(paiement);

            // Envoyer la notification de confirmation
            try {
                NotificationService notificationService = NotificationService.getInstance();
                boolean notificationSent = notificationService.sendBookingConfirmation(billet);

                if (notificationSent) {
                    System.out.println("✅ Notification de confirmation envoyée pour le billet #" + billet.getId());
                } else {
                    System.err.println("⚠️ Échec de l'envoi de la notification pour le billet #" + billet.getId());
                }
            } catch (Exception e) {
                System.err.println("❌ Erreur lors de l'envoi de la notification: " + e.getMessage());
                e.printStackTrace();
                // Ne pas bloquer le processus de paiement si la notification échoue
            }

            // Nettoyer la session
            session.removeAttribute("voyageId");
            session.removeAttribute("classeId");
            session.removeAttribute("preferences");

            // Rediriger vers la page de confirmation
            response.sendRedirect(request.getContextPath() + "/confirmation?billetId=" + billet.getId());
        } else {
            request.setAttribute("error", "Erreur lors de la création du billet");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/paiement.jsp");
            dispatcher.forward(request, response);
        }
    }
}
