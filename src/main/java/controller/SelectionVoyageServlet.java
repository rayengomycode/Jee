package controller;

import dao.ClasseDAO;
import dao.VoyageDAO;
import model.Classe;
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

public class SelectionVoyageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private VoyageDAO voyageDAO;
    private ClasseDAO classeDAO;

    public SelectionVoyageServlet() {
        super();
        voyageDAO = new VoyageDAO();
        classeDAO = new ClasseDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String voyageId = request.getParameter("id");

        if (voyageId != null && !voyageId.isEmpty()) {
            Voyage voyage = voyageDAO.findById(Long.parseLong(voyageId));
            List<Classe> classes = classeDAO.findAll();

            request.setAttribute("voyage", voyage);
            request.setAttribute("classes", classes);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/selection-voyage.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/recherche");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        String voyageId = request.getParameter("voyageId");
        String classeId = request.getParameter("classeId");
        String preferences = request.getParameter("preferences");

        // Stocker les sélections dans la session
        session.setAttribute("voyageId", voyageId);
        session.setAttribute("classeId", classeId);
        session.setAttribute("preferences", preferences);

        // Vérifier si l'utilisateur veut réserver un autre voyage
        String autreVoyage = request.getParameter("autreVoyage");

        if (autreVoyage != null && autreVoyage.equals("oui")) {
            // Rediriger vers la page de recherche pour un autre voyage
            Voyage voyage = voyageDAO.findById(Long.parseLong(voyageId));
            String villeArrivee = voyage.getTrajet().getGareArrivee().getVille();

            response.sendRedirect(request.getContextPath() + "/recherche?villeDepart=" + villeArrivee);
        } else {
            // Rediriger vers la page de paiement
            response.sendRedirect(request.getContextPath() + "/paiement");
        }
    }
}
