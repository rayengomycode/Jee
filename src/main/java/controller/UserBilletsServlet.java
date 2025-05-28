package controller;

import dao.BilletDAO;
import model.Billet;
import model.User;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class UserBilletsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BilletDAO billetDAO;

    public UserBilletsServlet() {
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

        String filter = request.getParameter("filter");
        List<Billet> billets;

        if (filter != null) {
            switch (filter) {
                case "achetes":
                    billets = billetDAO.findByUserAndEtat(user, Billet.EtatBillet.ACHETE);
                    break;
                case "utilises":
                    billets = billetDAO.findByUserAndEtat(user, Billet.EtatBillet.UTILISE);
                    break;
                case "annules":
                    billets = billetDAO.findByUserAndEtat(user, Billet.EtatBillet.ANNULE);
                    break;
                default:
                    billets = billetDAO.findByUser(user);
            }
        } else {
            billets = billetDAO.findByUser(user);
        }

        request.setAttribute("billets", billets);
        request.setAttribute("filter", filter);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/mes-billets.jsp");
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

        String action = request.getParameter("action");
        String billetId = request.getParameter("billetId");

        if (action != null && billetId != null) {
            Billet billet = billetDAO.findById(Long.parseLong(billetId));

            if (billet != null && billet.getUser().getId().equals(user.getId())) {
                if (action.equals("annuler") && billet.getEtat() == Billet.EtatBillet.ACHETE) {
                    // Marquer le billet comme en attente d'annulation
                    // Pour simplifier, on change directement l'état à ANNULE
                    // Dans une version complète, il faudrait un état "EN_ATTENTE_ANNULATION"
                    billetDAO.updateEtat(billet.getId(), Billet.EtatBillet.ANNULE);

                    // Remettre la place disponible
                    billet.getVoyage().setPlacesDisponibles(billet.getVoyage().getPlacesDisponibles() + 1);

                    request.setAttribute("message", "Votre demande d'annulation a été prise en compte.");
                }
            }
        }

        response.sendRedirect(request.getContextPath() + "/mes-billets");
    }
}
