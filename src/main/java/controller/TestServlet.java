package controller;

import dao.*;
import model.*;
import util.HibernateUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Servlet de test pour vérifier le bon fonctionnement de l'application
 * Accessible uniquement en mode développement
 */
public class TestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Test de l'application</title>");
        out.println("<style>body{font-family:Arial,sans-serif;margin:20px;} .success{color:green;} .error{color:red;} .info{color:blue;}</style>");
        out.println("</head><body>");
        out.println("<h1>Test de l'application Train Ticket System</h1>");

        try {
            // Test de la connexion Hibernate
            out.println("<h2>1. Test de la connexion à la base de données</h2>");
            try {
                HibernateUtil.getSessionFactory();
                out.println("<p class='success'>✓ Connexion Hibernate réussie</p>");
            } catch (Exception e) {
                out.println("<p class='error'>✗ Erreur de connexion Hibernate: " + e.getMessage() + "</p>");
            }

            // Test des DAOs
            out.println("<h2>2. Test des DAOs</h2>");

            // Test RoleDAO
            try {
                RoleDAO roleDAO = new RoleDAO();
                List<Role> roles = roleDAO.findAll();
                out.println("<p class='success'>✓ RoleDAO - " + roles.size() + " rôles trouvés</p>");
                for (Role role : roles) {
                    out.println("<p class='info'>  - " + role.getNom() + "</p>");
                }
            } catch (Exception e) {
                out.println("<p class='error'>✗ Erreur RoleDAO: " + e.getMessage() + "</p>");
            }

            // Test ClasseDAO
            try {
                ClasseDAO classeDAO = new ClasseDAO();
                List<Classe> classes = classeDAO.findAll();
                out.println("<p class='success'>✓ ClasseDAO - " + classes.size() + " classes trouvées</p>");
                for (Classe classe : classes) {
                    out.println("<p class='info'>  - " + classe.getNom() + " (coef: " + classe.getCoefficientPrix() + ")</p>");
                }
            } catch (Exception e) {
                out.println("<p class='error'>✗ Erreur ClasseDAO: " + e.getMessage() + "</p>");
            }

            // Test GareDAO
            try {
                GareDAO gareDAO = new GareDAO();
                List<Gare> gares = gareDAO.findAll();
                out.println("<p class='success'>✓ GareDAO - " + gares.size() + " gares trouvées</p>");
                for (Gare gare : gares) {
                    out.println("<p class='info'>  - " + gare.getNom() + " (" + gare.getVille() + ")</p>");
                }
            } catch (Exception e) {
                out.println("<p class='error'>✗ Erreur GareDAO: " + e.getMessage() + "</p>");
            }

            // Test TrainDAO
            try {
                TrainDAO trainDAO = new TrainDAO();
                List<Train> trains = trainDAO.findAll();
                out.println("<p class='success'>✓ TrainDAO - " + trains.size() + " trains trouvés</p>");
                for (Train train : trains) {
                    out.println("<p class='info'>  - " + train.getNumero() + " - " + train.getNom() + " (capacité: " + train.getCapaciteTotal() + ")</p>");
                }
            } catch (Exception e) {
                out.println("<p class='error'>✗ Erreur TrainDAO: " + e.getMessage() + "</p>");
            }

            // Test TrajetDAO
            try {
                TrajetDAO trajetDAO = new TrajetDAO();
                List<Trajet> trajets = trajetDAO.findAll();
                out.println("<p class='success'>✓ TrajetDAO - " + trajets.size() + " trajets trouvés</p>");
                for (Trajet trajet : trajets) {
                    out.println("<p class='info'>  - " + trajet.getCode() + " (direct: " + trajet.isDirect() + ")</p>");
                }
            } catch (Exception e) {
                out.println("<p class='error'>✗ Erreur TrajetDAO: " + e.getMessage() + "</p>");
            }

            // Test VoyageDAO
            try {
                VoyageDAO voyageDAO = new VoyageDAO();
                List<Voyage> voyages = voyageDAO.findAll();
                out.println("<p class='success'>✓ VoyageDAO - " + voyages.size() + " voyages trouvés</p>");
                if (voyages.size() > 0) {
                    Voyage voyage = voyages.get(0);
                    out.println("<p class='info'>  - Exemple: " + voyage.getDate() + " à " + voyage.getHeureDepart() + " (" + voyage.getPrix() + " TND)</p>");
                }
            } catch (Exception e) {
                out.println("<p class='error'>✗ Erreur VoyageDAO: " + e.getMessage() + "</p>");
            }

            // Test UserDAO
            try {
                UserDAO userDAO = new UserDAO();
                List<User> users = userDAO.findAll();
                out.println("<p class='success'>✓ UserDAO - " + users.size() + " utilisateurs trouvés</p>");
                for (User user : users) {
                    out.println("<p class='info'>  - " + user.getUsername() + " (" + user.getRole().getNom() + ") - " + (user.isActif() ? "Actif" : "Bloqué") + "</p>");
                }
            } catch (Exception e) {
                out.println("<p class='error'>✗ Erreur UserDAO: " + e.getMessage() + "</p>");
            }

            out.println("<h2>3. Résumé</h2>");
            out.println("<p class='info'>Si tous les tests sont verts, l'application est prête à être utilisée.</p>");
            out.println("<p class='info'>Compte admin par défaut: admin / admin123</p>");
            out.println("<p><a href='" + request.getContextPath() + "/'>Retour à l'accueil</a></p>");

        } catch (Exception e) {
            out.println("<p class='error'>Erreur générale: " + e.getMessage() + "</p>");
            e.printStackTrace();
        }

        out.println("</body></html>");
    }
}
