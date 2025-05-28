package controller;

import dao.BilletDAO;
import model.Billet;
import model.User;
import util.PDFGenerator;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class PDFServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BilletDAO billetDAO;

    public PDFServlet() {
        super();
        billetDAO = new BilletDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/auth?page=login");
            return;
        }

        String billetId = request.getParameter("billetId");

        if (billetId != null && !billetId.isEmpty()) {
            Billet billet = billetDAO.findByIdWithRelations(Long.parseLong(billetId));

            if (billet != null && billet.getUser().getId().equals(user.getId())) {
                try {
                    // Générer le PDF
                    byte[] pdfBytes = PDFGenerator.generateBilletPDF(billet);

                    // Configurer la réponse pour le téléchargement
                    response.setContentType("application/pdf");
                    response.setHeader("Content-Disposition",
                        "attachment; filename=\"billet_" + billet.getId() + ".pdf\"");
                    response.setContentLength(pdfBytes.length);

                    // Écrire le PDF dans la réponse
                    response.getOutputStream().write(pdfBytes);
                    response.getOutputStream().flush();

                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Erreur lors de la génération du PDF");
                }
            } else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès non autorisé");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de billet manquant");
        }
    }
}
