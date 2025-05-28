package controller.admin;

import dao.GareDAO;
import model.Gare;
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

@WebServlet("/admin/gares")
public class GestionGaresServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private GareDAO gareDAO;
    
    public GestionGaresServlet() {
        super();
        gareDAO = new GareDAO();
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
            // Afficher la liste des gares
            List<Gare> gares = gareDAO.findAll();
            request.setAttribute("gares", gares);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/admin/gares/liste.jsp");
            dispatcher.forward(request, response);
        } else if (action.equals("ajouter")) {
            // Afficher le formulaire d'ajout
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/admin/gares/formulaire.jsp");
            dispatcher.forward(request, response);
        } else if (action.equals("modifier")) {
            // Afficher le formulaire de modification
            String id = request.getParameter("id");
            Gare gare = gareDAO.findById(Long.parseLong(id));
            request.setAttribute("gare", gare);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/admin/gares/formulaire.jsp");
            dispatcher.forward(request, response);
        } else if (action.equals("supprimer")) {
            // Supprimer une gare
            String id = request.getParameter("id");
            gareDAO.deleteById(Long.parseLong(id));
            response.sendRedirect(request.getContextPath() + "/admin/gares");
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
            // Ajouter une nouvelle gare
            String nom = request.getParameter("nom");
            String ville = request.getParameter("ville");
            String adresse = request.getParameter("adresse");
            
            Gare gare = new Gare(nom, ville, adresse);
            gareDAO.create(gare);
            
            response.sendRedirect(request.getContextPath() + "/admin/gares");
        } else if (action.equals("modifier")) {
            // Modifier une gare existante
            Long id = Long.parseLong(request.getParameter("id"));
            String nom = request.getParameter("nom");
            String ville = request.getParameter("ville");
            String adresse = request.getParameter("adresse");
            
            Gare gare = gareDAO.findById(id);
            gare.setNom(nom);
            gare.setVille(ville);
            gare.setAdresse(adresse);
            
            gareDAO.update(gare);
            
            response.sendRedirect(request.getContextPath() + "/admin/gares");
        }
    }
}
