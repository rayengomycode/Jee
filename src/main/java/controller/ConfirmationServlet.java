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

public class ConfirmationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BilletDAO billetDAO;

    public ConfirmationServlet() {
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
                request.setAttribute("billet", billet);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/confirmation.jsp");
                dispatcher.forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/mes-billets");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/mes-billets");
        }
    }
}
