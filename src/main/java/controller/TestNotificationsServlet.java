package controller;

import dao.*;
import model.*;
import service.NotificationService;
import service.EmailService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Servlet pour tester le système de notifications
 */
public class TestNotificationsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("test-email".equals(action)) {
            testEmailConfiguration(request, response);
        } else if ("test-booking".equals(action)) {
            testBookingConfirmation(request, response);
        } else if ("test-delay".equals(action)) {
            testDelayAlert(request, response);
        } else if ("test-cancellation".equals(action)) {
            testCancellationAlert(request, response);
        } else if ("test-reminder".equals(action)) {
            testTravelReminder(request, response);
        } else {
            showTestPage(request, response);
        }
    }
    
    private void showTestPage(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Test Notifications</title>");
        out.println("<style>");
        out.println("body{font-family:Arial,sans-serif;margin:20px;background:#f5f5f5;}");
        out.println(".container{max-width:800px;margin:0 auto;background:white;padding:20px;border-radius:10px;box-shadow:0 2px 10px rgba(0,0,0,0.1);}");
        out.println(".success{color:green;background:#d4edda;padding:10px;border-radius:5px;margin:10px 0;}");
        out.println(".error{color:red;background:#f8d7da;padding:10px;border-radius:5px;margin:10px 0;}");
        out.println(".info{color:blue;background:#e7f3ff;padding:10px;border-radius:5px;margin:10px 0;}");
        out.println(".btn{background:#007bff;color:white;padding:10px 20px;text-decoration:none;border-radius:5px;margin:5px;display:inline-block;}");
        out.println(".btn:hover{background:#0056b3;}");
        out.println(".test-section{border:1px solid #ddd;padding:15px;margin:15px 0;border-radius:5px;}");
        out.println("</style></head><body>");
        
        out.println("<div class='container'>");
        out.println("<h1>🔔 Test du Système de Notifications</h1>");
        
        // Informations sur la configuration
        out.println("<div class='info'>");
        out.println("<h3>ℹ️ Configuration actuelle</h3>");
        out.println("<p><strong>Mode :</strong> Simulation (emails affichés dans les logs)</p>");
        out.println("<p><strong>Pour activer les vrais emails :</strong> Configurez EmailConfig.java avec vos paramètres SMTP</p>");
        out.println("</div>");
        
        // Test de configuration email
        out.println("<div class='test-section'>");
        out.println("<h3>📧 Test de Configuration Email</h3>");
        out.println("<p>Teste la configuration SMTP et l'envoi d'un email simple</p>");
        out.println("<a href='?action=test-email' class='btn'>Tester Configuration Email</a>");
        out.println("</div>");
        
        // Test de confirmation de réservation
        out.println("<div class='test-section'>");
        out.println("<h3>✅ Test Confirmation de Réservation</h3>");
        out.println("<p>Simule l'envoi d'une confirmation de réservation avec PDF joint</p>");
        out.println("<a href='?action=test-booking' class='btn'>Tester Confirmation</a>");
        out.println("</div>");
        
        // Test d'alerte de retard
        out.println("<div class='test-section'>");
        out.println("<h3>⚠️ Test Alerte de Retard</h3>");
        out.println("<p>Simule l'envoi d'une alerte de retard (email + SMS si >30min)</p>");
        out.println("<a href='?action=test-delay' class='btn'>Tester Alerte Retard</a>");
        out.println("</div>");
        
        // Test d'annulation
        out.println("<div class='test-section'>");
        out.println("<h3>🚫 Test Annulation de Voyage</h3>");
        out.println("<p>Simule l'envoi d'une notification d'annulation</p>");
        out.println("<a href='?action=test-cancellation' class='btn'>Tester Annulation</a>");
        out.println("</div>");
        
        // Test de rappel
        out.println("<div class='test-section'>");
        out.println("<h3>🔔 Test Rappel de Voyage</h3>");
        out.println("<p>Simule l'envoi d'un rappel de voyage (24h avant)</p>");
        out.println("<a href='?action=test-reminder' class='btn'>Tester Rappel</a>");
        out.println("</div>");
        
        // Statistiques des notifications
        try {
            NotificationDAO notificationDAO = new NotificationDAO();
            List<Notification> recentNotifications = notificationDAO.findRecentNotifications();
            
            out.println("<div class='test-section'>");
            out.println("<h3>📊 Notifications Récentes (24h)</h3>");
            
            if (recentNotifications.isEmpty()) {
                out.println("<p>Aucune notification récente</p>");
            } else {
                out.println("<table border='1' style='width:100%;border-collapse:collapse;'>");
                out.println("<tr style='background:#f0f0f0;'>");
                out.println("<th>ID</th><th>Type</th><th>Utilisateur</th><th>Titre</th><th>Date</th><th>Email</th><th>SMS</th>");
                out.println("</tr>");
                
                for (Notification notif : recentNotifications) {
                    out.println("<tr>");
                    out.println("<td>" + notif.getId() + "</td>");
                    out.println("<td>" + notif.getType() + "</td>");
                    out.println("<td>" + (notif.getUser() != null ? notif.getUser().getUsername() : "N/A") + "</td>");
                    out.println("<td>" + notif.getTitre() + "</td>");
                    out.println("<td>" + notif.getDateCreation() + "</td>");
                    out.println("<td>" + (notif.isEmailEnvoye() ? "✅" : "❌") + "</td>");
                    out.println("<td>" + (notif.isSmsEnvoye() ? "✅" : "❌") + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
            }
            out.println("</div>");
            
        } catch (Exception e) {
            out.println("<div class='error'>Erreur lors de la récupération des notifications : " + e.getMessage() + "</div>");
        }
        
        // Liens utiles
        out.println("<div class='test-section'>");
        out.println("<h3>🔗 Liens Utiles</h3>");
        out.println("<p><a href='" + request.getContextPath() + "/auth?page=register'>Créer un compte</a> (pour tester les notifications)</p>");
        out.println("<p><a href='" + request.getContextPath() + "/recherche'>Effectuer une réservation</a> (déclenche confirmation)</p>");
        out.println("<p><a href='" + request.getContextPath() + "/diagnostic'>Diagnostic général</a></p>");
        out.println("</div>");
        
        out.println("</div>");
        out.println("</body></html>");
    }
    
    private void testEmailConfiguration(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Test Email</title>");
        out.println("<style>body{font-family:Arial,sans-serif;margin:20px;} .success{color:green;} .error{color:red;}</style>");
        out.println("</head><body>");
        out.println("<h2>Test de Configuration Email</h2>");
        
        try {
            EmailService emailService = EmailService.getInstance();
            boolean success = emailService.testEmailConfiguration();
            
            if (success) {
                out.println("<p class='success'>✅ Test email réussi ! Vérifiez les logs pour voir l'email simulé.</p>");
            } else {
                out.println("<p class='error'>❌ Échec du test email. Vérifiez la configuration SMTP.</p>");
            }
            
        } catch (Exception e) {
            out.println("<p class='error'>❌ Erreur lors du test : " + e.getMessage() + "</p>");
        }
        
        out.println("<p><a href='?'>Retour aux tests</a></p>");
        out.println("</body></html>");
    }
    
    private void testBookingConfirmation(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Test Confirmation</title>");
        out.println("<style>body{font-family:Arial,sans-serif;margin:20px;} .success{color:green;} .error{color:red;}</style>");
        out.println("</head><body>");
        out.println("<h2>Test Confirmation de Réservation</h2>");
        
        try {
            // Créer un billet fictif pour test
            Billet billet = createTestBillet();
            
            NotificationService notificationService = NotificationService.getInstance();
            boolean success = notificationService.sendBookingConfirmation(billet);
            
            if (success) {
                out.println("<p class='success'>✅ Confirmation de réservation envoyée ! Vérifiez les logs Tomcat.</p>");
                out.println("<p><strong>Email destinataire :</strong> " + billet.getUser().getEmail() + "</p>");
                out.println("<p><strong>Billet ID :</strong> " + billet.getId() + "</p>");
            } else {
                out.println("<p class='error'>❌ Échec de l'envoi de la confirmation.</p>");
            }
            
        } catch (Exception e) {
            out.println("<p class='error'>❌ Erreur : " + e.getMessage() + "</p>");
            e.printStackTrace();
        }
        
        out.println("<p><a href='?'>Retour aux tests</a></p>");
        out.println("</body></html>");
    }
    
    private void testDelayAlert(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Test Alerte Retard</title>");
        out.println("<style>body{font-family:Arial,sans-serif;margin:20px;} .success{color:green;} .error{color:red;}</style>");
        out.println("</head><body>");
        out.println("<h2>Test Alerte de Retard</h2>");
        
        try {
            Voyage voyage = createTestVoyage();
            int delayMinutes = 45; // Retard de 45 minutes
            
            NotificationService notificationService = NotificationService.getInstance();
            boolean success = notificationService.sendDelayAlert(voyage, delayMinutes, "Problème technique sur la voie");
            
            if (success) {
                out.println("<p class='success'>✅ Alerte de retard envoyée !</p>");
                out.println("<p><strong>Retard :</strong> " + delayMinutes + " minutes</p>");
                out.println("<p><strong>Train :</strong> " + voyage.getTrain().getNumero() + "</p>");
                out.println("<p><strong>Note :</strong> Email + SMS envoyés (simulation)</p>");
            } else {
                out.println("<p class='error'>❌ Échec de l'envoi de l'alerte.</p>");
            }
            
        } catch (Exception e) {
            out.println("<p class='error'>❌ Erreur : " + e.getMessage() + "</p>");
        }
        
        out.println("<p><a href='?'>Retour aux tests</a></p>");
        out.println("</body></html>");
    }
    
    private void testCancellationAlert(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Test Annulation</title>");
        out.println("<style>body{font-family:Arial,sans-serif;margin:20px;} .success{color:green;} .error{color:red;}</style>");
        out.println("</head><body>");
        out.println("<h2>Test Annulation de Voyage</h2>");
        
        try {
            Voyage voyage = createTestVoyage();
            
            NotificationService notificationService = NotificationService.getInstance();
            boolean success = notificationService.sendCancellationAlert(voyage, "Grève du personnel");
            
            if (success) {
                out.println("<p class='success'>✅ Notification d'annulation envoyée !</p>");
                out.println("<p><strong>Train :</strong> " + voyage.getTrain().getNumero() + "</p>");
                out.println("<p><strong>Raison :</strong> Grève du personnel</p>");
                out.println("<p><strong>Note :</strong> Remboursement automatique simulé</p>");
            } else {
                out.println("<p class='error'>❌ Échec de l'envoi de la notification.</p>");
            }
            
        } catch (Exception e) {
            out.println("<p class='error'>❌ Erreur : " + e.getMessage() + "</p>");
        }
        
        out.println("<p><a href='?'>Retour aux tests</a></p>");
        out.println("</body></html>");
    }
    
    private void testTravelReminder(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Test Rappel</title>");
        out.println("<style>body{font-family:Arial,sans-serif;margin:20px;} .success{color:green;} .error{color:red;}</style>");
        out.println("</head><body>");
        out.println("<h2>Test Rappel de Voyage</h2>");
        
        try {
            Billet billet = createTestBillet();
            
            NotificationService notificationService = NotificationService.getInstance();
            boolean success = notificationService.sendTravelReminder(billet);
            
            if (success) {
                out.println("<p class='success'>✅ Rappel de voyage envoyé !</p>");
                out.println("<p><strong>Destinataire :</strong> " + billet.getUser().getEmail() + "</p>");
                out.println("<p><strong>Voyage :</strong> Demain</p>");
            } else {
                out.println("<p class='error'>❌ Échec de l'envoi du rappel.</p>");
            }
            
        } catch (Exception e) {
            out.println("<p class='error'>❌ Erreur : " + e.getMessage() + "</p>");
        }
        
        out.println("<p><a href='?'>Retour aux tests</a></p>");
        out.println("</body></html>");
    }
    
    // Méthodes utilitaires pour créer des objets de test
    
    private Billet createTestBillet() {
        // Créer des objets fictifs pour test
        User user = new User();
        user.setId(999L);
        user.setUsername("testuser");
        user.setPrenom("Jean");
        user.setNom("Dupont");
        user.setEmail("jean.dupont@test.com");
        user.setTelephone("+33123456789");
        
        Voyage voyage = createTestVoyage();
        
        Classe classe = new Classe();
        classe.setId(1L);
        classe.setNom("Économique");
        
        Billet billet = new Billet();
        billet.setId(999L);
        billet.setNumeroSiege("12A");
        billet.setPrix(89.50);
        billet.setDateAchat(LocalDateTime.now());
        billet.setEtat(Billet.EtatBillet.ACHETE);
        billet.setUser(user);
        billet.setVoyage(voyage);
        billet.setClasse(classe);
        
        return billet;
    }
    
    private Voyage createTestVoyage() {
        Train train = new Train();
        train.setId(1L);
        train.setNumero("TGV001");
        train.setNom("Express Test");
        
        Gare gareDepart = new Gare();
        gareDepart.setId(1L);
        gareDepart.setNom("Gare Centrale");
        gareDepart.setVille("Paris");
        
        Gare gareArrivee = new Gare();
        gareArrivee.setId(2L);
        gareArrivee.setNom("Gare Sud");
        gareArrivee.setVille("Lyon");
        
        Trajet trajet = new Trajet() {
            @Override
            public Gare getGareDepart() { return gareDepart; }
            @Override
            public Gare getGareArrivee() { return gareArrivee; }
        };
        trajet.setId(1L);
        trajet.setCode("PAR-LYO-001");
        
        Voyage voyage = new Voyage();
        voyage.setId(1L);
        voyage.setDate(LocalDate.now().plusDays(1));
        voyage.setHeureDepart(LocalTime.of(14, 30));
        voyage.setHeureArrivee(LocalTime.of(16, 45));
        voyage.setTrain(train);
        voyage.setTrajet(trajet);
        
        return voyage;
    }
}
