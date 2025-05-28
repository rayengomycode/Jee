package controller.admin;

import dao.TrainDAO;
import model.Train;
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

@WebServlet("/admin/trains")
public class GestionTrainsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TrainDAO trainDAO;
    
    public GestionTrainsServlet() {
        super();
        trainDAO = new TrainDAO();
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
            // Afficher la liste des trains
            List<Train> trains = trainDAO.findAll();
            request.setAttribute("trains", trains);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/admin/trains/liste.jsp");
            dispatcher.forward(request, response);
        } else if (action.equals("ajouter")) {
            // Afficher le formulaire d'ajout
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/admin/trains/formulaire.jsp");
            dispatcher.forward(request, response);
        } else if (action.equals("modifier")) {
            // Afficher le formulaire de modification
            String id = request.getParameter("id");
            Train train = trainDAO.findById(Long.parseLong(id));
            request.setAttribute("train", train);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/admin/trains/formulaire.jsp");
            dispatcher.forward(request, response);
        } else if (action.equals("supprimer")) {
            // Supprimer un train
            String id = request.getParameter("id");
            trainDAO.deleteById(Long.parseLong(id));
            response.sendRedirect(request.getContextPath() + "/admin/trains");
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null || !user.getRole().getNom().equals("ADMIN")) {
            response.sendRedirect(request.getContextPath() + "/auth?page=login");
            return;
        }
        
        String action = request.getParameter("action");
        
        if (action.equals("ajouter")) {
            // Ajouter un nouveau train
            String numero = request.getParameter("numero");
            String nom = request.getParameter("nom");
            int capaciteTotal = Integer.parseInt(request.getParameter("capaciteTotal"));
            
            Train train = new Train(numero, nom, capaciteTotal);
            trainDAO.create(train);
            
            response.sendRedirect(request.getContextPath() + "/admin/trains");
        } else if (action.equals("modifier")) {
            // Modifier un train existant
            Long id = Long.parseLong(request.getParameter("id"));
            String numero = request.getParameter("numero");
            String nom = request.getParameter("nom");
            int capaciteTotal = Integer.parseInt(request.getParameter("capaciteTotal"));
            
            Train train = trainDAO.findById(id);
            train.setNumero(numero);
            train.setNom(nom);
            train.setCapaciteTotal(capaciteTotal);
            
            trainDAO.update(train);
            
            response.sendRedirect(request.getContextPath() + "/admin/trains");
        }
    }
}
