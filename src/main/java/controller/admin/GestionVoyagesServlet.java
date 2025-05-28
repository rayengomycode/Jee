package controller.admin;

import dao.TrainDAO;
import dao.TrajetDAO;
import dao.VoyageDAO;
import model.Train;
import model.Trajet;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/admin/voyages")
public class GestionVoyagesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private VoyageDAO voyageDAO;
    private TrajetDAO trajetDAO;
    private TrainDAO trainDAO;
    
    public GestionVoyagesServlet() {
        super();
        voyageDAO = new VoyageDAO();
        trajetDAO = new TrajetDAO();
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
            // Afficher la liste des voyages
            List<Voyage> voyages = voyageDAO.findAll();
            request.setAttribute("voyages", voyages);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/admin/voyages/liste.jsp");
            dispatcher.forward(request, response);
        } else if (action.equals("ajouter")) {
            // Afficher le formulaire d'ajout
            List<Trajet> trajets = trajetDAO.findAll();
            List<Train> trains = trainDAO.findAll();
            
            request.setAttribute("trajets", trajets);
            request.setAttribute("trains", trains);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/admin/voyages/formulaire.jsp");
            dispatcher.forward(request, response);
        } else if (action.equals("modifier")) {
            // Afficher le formulaire de modification
            String id = request.getParameter("id");
            Voyage voyage = voyageDAO.findById(Long.parseLong(id));
            List<Trajet> trajets = trajetDAO.findAll();
            List<Train> trains = trainDAO.findAll();
            
            request.setAttribute("voyage", voyage);
            request.setAttribute("trajets", trajets);
            request.setAttribute("trains", trains);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/admin/voyages/formulaire.jsp");
            dispatcher.forward(request, response);
        } else if (action.equals("supprimer")) {
            // Supprimer un voyage
            String id = request.getParameter("id");
            voyageDAO.deleteById(Long.parseLong(id));
            response.sendRedirect(request.getContextPath() + "/admin/voyages");
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
            // Ajouter un nouveau voyage
            Long trajetId = Long.parseLong(request.getParameter("trajetId"));
            Long trainId = Long.parseLong(request.getParameter("trainId"));
            LocalDate date = LocalDate.parse(request.getParameter("date"));
            LocalTime heureDepart = LocalTime.parse(request.getParameter("heureDepart"));
            LocalTime heureArrivee = LocalTime.parse(request.getParameter("heureArrivee"));
            double prix = Double.parseDouble(request.getParameter("prix"));
            int placesDisponibles = Integer.parseInt(request.getParameter("placesDisponibles"));
            
            Trajet trajet = trajetDAO.findById(trajetId);
            Train train = trainDAO.findById(trainId);
            
            Voyage voyage = new Voyage(trajet, train, date, heureDepart, heureArrivee, prix, placesDisponibles);
            voyageDAO.create(voyage);
            
            response.sendRedirect(request.getContextPath() + "/admin/voyages");
        } else if (action.equals("modifier")) {
            // Modifier un voyage existant
            Long id = Long.parseLong(request.getParameter("id"));
            Long trajetId = Long.parseLong(request.getParameter("trajetId"));
            Long trainId = Long.parseLong(request.getParameter("trainId"));
            LocalDate date = LocalDate.parse(request.getParameter("date"));
            LocalTime heureDepart = LocalTime.parse(request.getParameter("heureDepart"));
            LocalTime heureArrivee = LocalTime.parse(request.getParameter("heureArrivee"));
            double prix = Double.parseDouble(request.getParameter("prix"));
            int placesDisponibles = Integer.parseInt(request.getParameter("placesDisponibles"));
            
            Trajet trajet = trajetDAO.findById(trajetId);
            Train train = trainDAO.findById(trainId);
            
            Voyage voyage = voyageDAO.findById(id);
            voyage.setTrajet(trajet);
            voyage.setTrain(train);
            voyage.setDate(date);
            voyage.setHeureDepart(heureDepart);
            voyage.setHeureArrivee(heureArrivee);
            voyage.setPrix(prix);
            voyage.setPlacesDisponibles(placesDisponibles);
            
            voyageDAO.update(voyage);
            
            response.sendRedirect(request.getContextPath() + "/admin/voyages");
        }
    }
}
