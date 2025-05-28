package controller.admin;

import dao.BilletDAO;
import dao.VoyageDAO;
import model.Billet;
import model.User;
import model.Voyage;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/billets")
public class GestionBilletsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BilletDAO billetDAO;
    private VoyageDAO voyageDAO;

    public GestionBilletsServlet() {
        super();
        billetDAO = new BilletDAO();
        voyageDAO = new VoyageDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || !user.getRole().getNom().equals("ADMIN")) {
            response.sendRedirect(request.getContextPath() + "/auth?page=login");
            return;
        }

        String action = request.getParameter("action");

        if (action == null) {
            // Afficher la liste des billets
            List<Billet> billets = billetDAO.findAll();
            request.setAttribute("billets", billets);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/admin/billets/liste.jsp");
            dispatcher.forward(request, response);
        } else if (action.equals("approuverAnnulation")) {
            // Approuver l'annulation d'un billet
            String id = request.getParameter("id");
            Billet billet = billetDAO.findById(Long.parseLong(id));

            if (billet != null && billet.getEtat() == Billet.EtatBillet.ACHETE) {
                // Changer l'état du billet
                billet.setEtat(Billet.EtatBillet.ANNULE);
                billetDAO.update(billet);

                // Remettre la place disponible
                Voyage voyage = billet.getVoyage();
                voyage.setPlacesDisponibles(voyage.getPlacesDisponibles() + 1);
                voyageDAO.update(voyage);
            }

            response.sendRedirect(request.getContextPath() + "/admin/billets");
        } else if (action.equals("refuserAnnulation")) {
            // Refuser l'annulation d'un billet - ne rien faire, garder l'état ACHETE
            response.sendRedirect(request.getContextPath() + "/admin/billets");
        }
    }
}
