package controller;

import dao.RoleDAO;
import dao.UserDAO;
import model.Role;
import model.User;
import util.PasswordUtil;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class AuthServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;
    private RoleDAO roleDAO;

    public AuthServlet() {
        super();
        userDAO = new UserDAO();
        roleDAO = new RoleDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null && action.equals("logout")) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/auth?page=login");
            return;
        }

        String page = request.getParameter("page");
        if (page == null || page.isEmpty()) {
            page = "login";
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/" + page + ".jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action.equals("login")) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            User user = userDAO.findByUsername(username);

            boolean passwordValid = false;
            if (user != null && user.isActif()) {
                try {
                    // Essayer d'abord avec BCrypt
                    passwordValid = PasswordUtil.checkPassword(password, user.getPassword());
                } catch (IllegalArgumentException e) {
                    // Si BCrypt échoue, vérifier si c'est un mot de passe en clair (pour la migration)
                    if (password.equals(user.getPassword())) {
                        // Mot de passe en clair correct, le mettre à jour avec BCrypt
                        user.setPassword(PasswordUtil.hashPassword(password));
                        userDAO.update(user);
                        passwordValid = true;
                        System.out.println("Mot de passe migré vers BCrypt pour l'utilisateur: " + username);
                    }
                }
            }

            if (passwordValid) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                if (user.getRole().getNom().equals("ADMIN")) {
                    response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                } else {
                    // Rediriger vers la page précédente ou la page d'accueil
                    String referer = request.getHeader("Referer");
                    if (referer != null && !referer.contains("auth")) {
                        response.sendRedirect(referer);
                    } else {
                        response.sendRedirect(request.getContextPath() + "/");
                    }
                }
            } else {
                request.setAttribute("error", "Nom d'utilisateur ou mot de passe incorrect");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
                dispatcher.forward(request, response);
            }
        } else if (action.equals("register")) {
            try {
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                String nom = request.getParameter("nom");
                String prenom = request.getParameter("prenom");
                String email = request.getParameter("email");

                // Validation des champs
                if (username == null || username.trim().isEmpty() ||
                    password == null || password.trim().isEmpty() ||
                    nom == null || nom.trim().isEmpty() ||
                    prenom == null || prenom.trim().isEmpty() ||
                    email == null || email.trim().isEmpty()) {

                    request.setAttribute("error", "Tous les champs sont obligatoires");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/register.jsp");
                    dispatcher.forward(request, response);
                    return;
                }

                // Vérifier si l'utilisateur existe déjà
                User existingUser = userDAO.findByUsername(username.trim());
                if (existingUser != null) {
                    request.setAttribute("error", "Ce nom d'utilisateur existe déjà");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/register.jsp");
                    dispatcher.forward(request, response);
                    return;
                }

                // Vérifier si l'email existe déjà
                existingUser = userDAO.findByEmail(email.trim());
                if (existingUser != null) {
                    request.setAttribute("error", "Cet email est déjà utilisé");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/register.jsp");
                    dispatcher.forward(request, response);
                    return;
                }

                // Vérifier que le rôle USER existe
                Role userRole = roleDAO.findByNom("USER");
                if (userRole == null) {
                    request.setAttribute("error", "Erreur de configuration : rôle USER non trouvé");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/register.jsp");
                    dispatcher.forward(request, response);
                    return;
                }

                // Créer un nouvel utilisateur avec mot de passe haché
                User newUser = new User(username.trim(), PasswordUtil.hashPassword(password),
                                      nom.trim(), prenom.trim(), email.trim());
                newUser.setRole(userRole);
                newUser.setActif(true);

                // Debug : afficher les informations avant création
                System.out.println("=== DEBUG CREATION USER ===");
                System.out.println("Username: " + newUser.getUsername());
                System.out.println("Email: " + newUser.getEmail());
                System.out.println("Role ID: " + (userRole != null ? userRole.getId() : "NULL"));
                System.out.println("Role Name: " + (userRole != null ? userRole.getNom() : "NULL"));
                System.out.println("Actif: " + newUser.isActif());

                boolean success = userDAO.create(newUser);

                if (success) {
                    System.out.println("=== USER CREATED SUCCESSFULLY ===");
                    HttpSession session = request.getSession();
                    session.setAttribute("user", newUser);

                    // Rediriger vers la page précédente ou la page d'accueil
                    String referer = request.getHeader("Referer");
                    if (referer != null && !referer.contains("auth")) {
                        response.sendRedirect(referer);
                    } else {
                        response.sendRedirect(request.getContextPath() + "/");
                    }
                } else {
                    System.out.println("=== USER CREATION FAILED ===");
                    request.setAttribute("error", "Erreur lors de la création du compte en base de données. Vérifiez les logs pour plus de détails.");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/register.jsp");
                    dispatcher.forward(request, response);
                }
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "Erreur technique : " + e.getMessage());
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/register.jsp");
                dispatcher.forward(request, response);
            }
        }
    }
}
