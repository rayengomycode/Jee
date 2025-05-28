package controller.admin;

import dao.GareDAO;
import dao.TrajetDAO;
import model.Gare;
import model.GareTrajet;
import model.Trajet;
import model.User;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

@WebServlet("/admin/trajets")
public class GestionTrajetsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TrajetDAO trajetDAO;
    private GareDAO gareDAO;
    
    public GestionTrajetsServlet() {
        super();
        trajetDAO = new TrajetDAO();
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
            // Afficher la liste des trajets
            List<Trajet> trajets = trajetDAO.findAll();
            request.setAttribute("trajets", trajets);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/admin/trajets/liste.jsp");
            dispatcher.forward(request, response);
        } else if (action.equals("ajouter")) {
            // Afficher le formulaire d'ajout
            List<Gare> gares = gareDAO.findAll();
            request.setAttribute("gares", gares);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/admin/trajets/formulaire.jsp");
            dispatcher.forward(request, response);
        } else if (action.equals("modifier")) {
            // Afficher le formulaire de modification
            String id = request.getParameter("id");
            Trajet trajet = trajetDAO.findById(Long.parseLong(id));
            List<Gare> gares = gareDAO.findAll();
            
            request.setAttribute("trajet", trajet);
            request.setAttribute("gares", gares);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/admin/trajets/formulaire.jsp");
            dispatcher.forward(request, response);
        } else if (action.equals("supprimer")) {
            // Supprimer un trajet
            String id = request.getParameter("id");
            trajetDAO.deleteById(Long.parseLong(id));
            response.sendRedirect(request.getContextPath() + "/admin/trajets");
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
            // Ajouter un nouveau trajet
            String code = request.getParameter("code");
            boolean direct = request.getParameter("direct") != null;
            
            Trajet trajet = new Trajet(code, direct);
            trajetDAO.create(trajet);
            
            // Ajouter les gares au trajet
            String[] gareIds = request.getParameterValues("gareId");
            String[] ordres = request.getParameterValues("ordre");
            String[] tempsArrets = request.getParameterValues("tempsArret");
            String[] tempsParcours = request.getParameterValues("tempsParcours");
            
            if (gareIds != null && gareIds.length > 0) {
                for (int i = 0; i < gareIds.length; i++) {
                    Gare gare = gareDAO.findById(Long.parseLong(gareIds[i]));
                    int ordre = Integer.parseInt(ordres[i]);
                    Duration tempsArret = Duration.ofMinutes(Long.parseLong(tempsArrets[i]));
                    Duration tempsParcour = Duration.ofMinutes(Long.parseLong(tempsParcours[i]));
                    
                    GareTrajet gareTrajet = new GareTrajet(gare, trajet, ordre, tempsArret, tempsParcour);
                    trajet.getGareTrajets().add(gareTrajet);
                }
                
                trajetDAO.update(trajet);
            }
            
            response.sendRedirect(request.getContextPath() + "/admin/trajets");
        } else if (action.equals("modifier")) {
            // Modifier un trajet existant
            Long id = Long.parseLong(request.getParameter("id"));
            String code = request.getParameter("code");
            boolean direct = request.getParameter("direct") != null;
            
            Trajet trajet = trajetDAO.findById(id);
            trajet.setCode(code);
            trajet.setDirect(direct);
            
            // Supprimer les anciennes gares
            trajet.getGareTrajets().clear();
            
            // Ajouter les nouvelles gares
            String[] gareIds = request.getParameterValues("gareId");
            String[] ordres = request.getParameterValues("ordre");
            String[] tempsArrets = request.getParameterValues("tempsArret");
            String[] tempsParcours = request.getParameterValues("tempsParcours");
            
            if (gareIds != null && gareIds.length > 0) {
                for (int i = 0; i < gareIds.length; i++) {
                    Gare gare = gareDAO.findById(Long.parseLong(gareIds[i]));
                    int ordre = Integer.parseInt(ordres[i]);
                    Duration tempsArret = Duration.ofMinutes(Long.parseLong(tempsArrets[i]));
                    Duration tempsParcour = Duration.ofMinutes(Long.parseLong(tempsParcours[i]));
                    
                    GareTrajet gareTrajet = new GareTrajet(gare, trajet, ordre, tempsArret, tempsParcour);
                    trajet.getGareTrajets().add(gareTrajet);
                }
            }
            
            trajetDAO.update(trajet);
            
            response.sendRedirect(request.getContextPath() + "/admin/trajets");
        }
    }
}
