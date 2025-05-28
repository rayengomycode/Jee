package controller;

import dao.RoleDAO;
import dao.UserDAO;
import model.Role;
import model.User;
import util.PasswordUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet pour réinitialiser le mot de passe admin en cas de problème
 * À utiliser uniquement en cas d'urgence
 */
public class ResetAdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Reset Admin Password</title>");
        out.println("<style>body{font-family:Arial,sans-serif;margin:20px;} .success{color:green;} .error{color:red;}</style>");
        out.println("</head><body>");
        out.println("<h1>Réinitialisation du mot de passe admin</h1>");
        
        try {
            UserDAO userDAO = new UserDAO();
            RoleDAO roleDAO = new RoleDAO();
            
            // Chercher l'utilisateur admin
            User admin = userDAO.findByUsername("admin");
            
            if (admin == null) {
                // Créer l'utilisateur admin s'il n'existe pas
                Role adminRole = roleDAO.findByNom("ADMIN");
                if (adminRole == null) {
                    adminRole = new Role("ADMIN");
                    roleDAO.create(adminRole);
                }
                
                admin = new User("admin", PasswordUtil.hashPassword("admin123"), 
                               "Administrateur", "Système", "admin@trainticket.com");
                admin.setRole(adminRole);
                admin.setActif(true);
                userDAO.create(admin);
                
                out.println("<p class='success'>✓ Utilisateur admin créé avec succès</p>");
            } else {
                // Mettre à jour le mot de passe avec le hash correct
                admin.setPassword(PasswordUtil.hashPassword("admin123"));
                admin.setActif(true);
                userDAO.update(admin);
                
                out.println("<p class='success'>✓ Mot de passe admin réinitialisé avec succès</p>");
            }
            
            out.println("<p><strong>Identifiants admin :</strong></p>");
            out.println("<p>Username: <code>admin</code></p>");
            out.println("<p>Password: <code>admin123</code></p>");
            
            out.println("<p><a href='" + request.getContextPath() + "/auth'>Aller à la page de connexion</a></p>");
            out.println("<p><a href='" + request.getContextPath() + "/test'>Page de test</a></p>");
            
        } catch (Exception e) {
            out.println("<p class='error'>Erreur: " + e.getMessage() + "</p>");
            e.printStackTrace();
        }
        
        out.println("</body></html>");
    }
}
