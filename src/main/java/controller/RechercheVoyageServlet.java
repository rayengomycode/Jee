package controller;

import dao.GareDAO;
import dao.VoyageDAO;
import model.Gare;
import model.Voyage;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RechercheVoyageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private VoyageDAO voyageDAO;
    private GareDAO gareDAO;

    public RechercheVoyageServlet() {
        super();
        voyageDAO = new VoyageDAO();
        gareDAO = new GareDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Récupérer la liste des gares pour les listes déroulantes
        List<Gare> gares = gareDAO.findAll();
        request.setAttribute("gares", gares);

        // Pré-remplir les champs si des paramètres sont fournis
        String villeDepart = request.getParameter("villeDepart");
        String villeArrivee = request.getParameter("villeArrivee");
        String date = request.getParameter("date");

        if (villeDepart != null) request.setAttribute("villeDepart", villeDepart);
        if (villeArrivee != null) request.setAttribute("villeArrivee", villeArrivee);
        if (date != null) request.setAttribute("date", date);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/recherche.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String dateStr = request.getParameter("date");
        String villeDepart = request.getParameter("villeDepart");
        String villeArrivee = request.getParameter("villeArrivee");
        String direct = request.getParameter("direct");

        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ISO_DATE);

        List<Voyage> voyages;
        if (direct != null && direct.equals("on")) {
            voyages = voyageDAO.findDirectByDateAndVilles(date, villeDepart, villeArrivee);
        } else {
            voyages = voyageDAO.findByDateAndVilles(date, villeDepart, villeArrivee);
        }

        request.setAttribute("voyages", voyages);
        request.setAttribute("date", dateStr);
        request.setAttribute("villeDepart", villeDepart);
        request.setAttribute("villeArrivee", villeArrivee);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/resultats-recherche.jsp");
        dispatcher.forward(request, response);
    }
}
