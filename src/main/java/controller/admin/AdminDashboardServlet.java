package controller.admin;

import dao.*;
import model.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;
    private TrainDAO trainDAO;
    private GareDAO gareDAO;
    private TrajetDAO trajetDAO;
    private VoyageDAO voyageDAO;
    private BilletDAO billetDAO;
    
    public AdminDashboardServlet() {
        super();
        userDAO = new UserDAO();
        trainDAO = new TrainDAO();
        gareDAO = new GareDAO();
        trajetDAO = new TrajetDAO();
        voyageDAO = new VoyageDAO();
        billetDAO = new BilletDAO();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null || !user.getRole().getNom().equals("ADMIN")) {
            response.sendRedirect(request.getContextPath() + "/auth?page=login");
            return;
        }
        
        // Récupérer les statistiques
        int totalUsers = userDAO.findAll().size();
        int totalTrains = trainDAO.findAll().size();
        int totalGares = gareDAO.findAll().size();
        int totalTrajets = trajetDAO.findAll().size();
        int totalVoyages = voyageDAO.findAll().size();
        int totalBillets = billetDAO.findAll().size();
        
        // Récupérer les derniers billets achetés
        List<Billet> derniersBillets = billetDAO.findAll();
        // Limiter à 10 billets
        if (derniersBillets.size() > 10) {
            derniersBillets = derniersBillets.subList(0, 10);
        }
        
        request.setAttribute("totalUsers", totalUsers);
        request.setAttribute("totalTrains", totalTrains);
        request.setAttribute("totalGares", totalGares);
        request.setAttribute("totalTrajets", totalTrajets);
        request.setAttribute("totalVoyages", totalVoyages);
        request.setAttribute("totalBillets", totalBillets);
        request.setAttribute("derniersBillets", derniersBillets);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp");
        dispatcher.forward(request, response);
    }
}
