package controller;

import dao.*;
import model.*;
import util.PDFGenerator;

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
 * Servlet pour tester la génération de PDF
 */
public class TestPDFServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("generate".equals(action)) {
            generateTestPDF(request, response);
        } else {
            showTestPage(request, response);
        }
    }

    private void showTestPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Test Génération PDF</title>");
        out.println("<style>body{font-family:Arial,sans-serif;margin:20px;} .success{color:green;} .error{color:red;} .info{color:blue;} .btn{background:#007bff;color:white;padding:10px 20px;text-decoration:none;border-radius:5px;margin:5px;}</style>");
        out.println("</head><body>");
        out.println("<h1>Test de Génération PDF</h1>");

        try {
            BilletDAO billetDAO = new BilletDAO();
            List<Billet> billets = billetDAO.findAll();

            out.println("<h2>Billets disponibles pour test :</h2>");

            if (billets.isEmpty()) {
                out.println("<p class='error'>Aucun billet trouvé en base de données</p>");
                out.println("<p class='info'>Créez d'abord des billets via l'interface utilisateur</p>");
            } else {
                out.println("<table border='1' style='border-collapse:collapse; width:100%;'>");
                out.println("<tr style='background:#f0f0f0;'>");
                out.println("<th>ID</th><th>Passager</th><th>Voyage</th><th>Prix</th><th>État</th><th>Action</th>");
                out.println("</tr>");

                for (Billet billet : billets) {
                    out.println("<tr>");
                    out.println("<td>" + billet.getId() + "</td>");

                    String passager = "N/A";
                    if (billet.getUser() != null) {
                        passager = billet.getUser().getPrenom() + " " + billet.getUser().getNom();
                    }
                    out.println("<td>" + passager + "</td>");

                    String voyage = "N/A";
                    if (billet.getVoyage() != null && billet.getVoyage().getTrajet() != null) {
                        Gare depart = billet.getVoyage().getTrajet().getGareDepart();
                        Gare arrivee = billet.getVoyage().getTrajet().getGareArrivee();
                        if (depart != null && arrivee != null) {
                            voyage = depart.getVille() + " → " + arrivee.getVille();
                        }
                    }
                    out.println("<td>" + voyage + "</td>");

                    out.println("<td>" + String.format("%.2f TND", billet.getPrix()) + "</td>");
                    out.println("<td>" + billet.getEtat() + "</td>");
                    out.println("<td>");
                    out.println("<a href='?action=generate&billetId=" + billet.getId() + "' class='btn'>Générer PDF</a>");
                    out.println("</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
            }

            out.println("<h2>Test avec billet fictif :</h2>");
            out.println("<p class='info'>Si aucun billet n'existe, vous pouvez tester avec un billet fictif :</p>");
            out.println("<a href='?action=generate&billetId=fake' class='btn'>Générer PDF Fictif</a>");

        } catch (Exception e) {
            out.println("<p class='error'>Erreur lors de la récupération des billets : " + e.getMessage() + "</p>");
            e.printStackTrace();
        }

        out.println("<h2>Liens utiles :</h2>");
        out.println("<p><a href='" + request.getContextPath() + "/mes-billets'>Mes billets</a></p>");
        out.println("<p><a href='" + request.getContextPath() + "/admin/billets'>Admin billets</a></p>");
        out.println("<p><a href='" + request.getContextPath() + "/diagnostic'>Diagnostic</a></p>");

        out.println("</body></html>");
    }

    private void generateTestPDF(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String billetId = request.getParameter("billetId");

        try {
            Billet billet = null;

            if ("fake".equals(billetId)) {
                // Créer un billet fictif pour test
                billet = createFakeBillet();
            } else {
                // Récupérer le billet réel
                BilletDAO billetDAO = new BilletDAO();
                billet = billetDAO.findByIdWithRelations(Long.parseLong(billetId));
            }

            if (billet == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Billet non trouvé");
                return;
            }

            // Générer le PDF
            byte[] pdfBytes = PDFGenerator.generateBilletPDF(billet);

            // Configurer la réponse
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition",
                "attachment; filename=\"test_billet_" + billetId + ".pdf\"");
            response.setContentLength(pdfBytes.length);

            // Envoyer le PDF
            response.getOutputStream().write(pdfBytes);
            response.getOutputStream().flush();

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "Erreur lors de la génération du PDF : " + e.getMessage());
        }
    }

    private Billet createFakeBillet() {
        // Créer des objets fictifs pour test
        User user = new User();
        user.setId(999L);
        user.setUsername("testuser");
        user.setPrenom("Jean");
        user.setNom("Dupont");
        user.setEmail("jean.dupont@test.com");

        Role role = new Role();
        role.setId(2L);
        role.setNom("USER");
        user.setRole(role);

        Classe classe = new Classe();
        classe.setId(1L);
        classe.setNom("Économique");
        classe.setCoefficientPrix(1.0);

        Train train = new Train();
        train.setId(1L);
        train.setNumero("TGV001");
        train.setNom("Express Test");
        train.setCapaciteTotal(300);

        Gare gareDepart = new Gare();
        gareDepart.setId(1L);
        gareDepart.setNom("Gare Centrale");
        gareDepart.setVille("Paris");

        Gare gareArrivee = new Gare();
        gareArrivee.setId(2L);
        gareArrivee.setNom("Gare Sud");
        gareArrivee.setVille("Lyon");

        Trajet trajet = new Trajet();
        trajet.setId(1L);
        trajet.setCode("PAR-LYO-001");
        trajet.setDirect(true);
        // Note: Pour le test, on simule les gares sans les relations complètes

        Voyage voyage = new Voyage();
        voyage.setId(1L);
        voyage.setDate(LocalDate.now().plusDays(1));
        voyage.setHeureDepart(LocalTime.of(14, 30));
        voyage.setHeureArrivee(LocalTime.of(16, 45));
        voyage.setPrix(75.0);
        voyage.setPlacesDisponibles(150);
        voyage.setTrain(train);
        voyage.setTrajet(trajet);

        Billet billet = new Billet();
        billet.setId(999L);
        billet.setNumeroSiege("12A");
        billet.setPrix(89.50);
        billet.setDateAchat(LocalDateTime.now().minusHours(2));
        billet.setEtat(Billet.EtatBillet.ACHETE);
        billet.setPreferences("Fenêtre, Silence");
        billet.setUser(user);
        billet.setVoyage(voyage);
        billet.setClasse(classe);

        // Simuler les méthodes getGareDepart/getGareArrivee du trajet
        // En créant une version simplifiée pour le test
        trajet = new Trajet() {
            @Override
            public Gare getGareDepart() {
                return gareDepart;
            }

            @Override
            public Gare getGareArrivee() {
                return gareArrivee;
            }
        };
        trajet.setId(1L);
        trajet.setCode("PAR-LYO-001");
        trajet.setDirect(true);

        voyage.setTrajet(trajet);

        return billet;
    }
}
