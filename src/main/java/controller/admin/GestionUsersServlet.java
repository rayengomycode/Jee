package controller.admin;

import dao.RoleDAO;
import dao.UserDAO;
import model.Role;
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

@WebServlet("/admin/users")
public class GestionUsersServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;
    private RoleDAO roleDAO;

    public GestionUsersServlet() {
        super();
        userDAO = new UserDAO();
        roleDAO = new RoleDAO();
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
            // Afficher la liste des utilisateurs
            List<User> users = userDAO.findAll();
            request.setAttribute("users", users);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/admin/users/liste.jsp");
            dispatcher.forward(request, response);
        } else if (action.equals("modifier")) {
            // Afficher le formulaire de modification
            String id = request.getParameter("id");
            User userToEdit = userDAO.findById(Long.parseLong(id));
            List<Role> roles = roleDAO.findAll();

            request.setAttribute("userToEdit", userToEdit);
            request.setAttribute("roles", roles);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/admin/users/formulaire.jsp");
            dispatcher.forward(request, response);
        } else if (action.equals("bloquer")) {
            // Bloquer un utilisateur
            String id = request.getParameter("id");
            userDAO.updateStatut(Long.parseLong(id), false);
            response.sendRedirect(request.getContextPath() + "/admin/users");
        } else if (action.equals("debloquer")) {
            // Débloquer un utilisateur
            String id = request.getParameter("id");
            userDAO.updateStatut(Long.parseLong(id), true);
            response.sendRedirect(request.getContextPath() + "/admin/users");
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

        if (action.equals("modifier")) {
            // Modifier un utilisateur existant
            Long id = Long.parseLong(request.getParameter("id"));
            String username = request.getParameter("username");
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String email = request.getParameter("email");
            Long roleId = Long.parseLong(request.getParameter("roleId"));

            User userToUpdate = userDAO.findById(id);
            Role role = roleDAO.findById(roleId);

            userToUpdate.setUsername(username);
            userToUpdate.setNom(nom);
            userToUpdate.setPrenom(prenom);
            userToUpdate.setEmail(email);
            userToUpdate.setRole(role);

            userDAO.update(userToUpdate);

            response.sendRedirect(request.getContextPath() + "/admin/users");
        }
    }
}
