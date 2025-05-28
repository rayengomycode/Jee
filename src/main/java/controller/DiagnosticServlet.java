package controller;

import dao.*;
import model.*;
import util.HibernateUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Servlet de diagnostic pour vérifier l'état de la base de données
 */
public class DiagnosticServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Diagnostic Base de Données</title>");
        out.println("<style>body{font-family:Arial,sans-serif;margin:20px;} .success{color:green;} .error{color:red;} .info{color:blue;}</style>");
        out.println("</head><body>");
        out.println("<h1>Diagnostic de la Base de Données</h1>");
        
        try {
            // Test de connexion Hibernate
            out.println("<h2>1. Connexion Hibernate</h2>");
            try {
                HibernateUtil.getSessionFactory();
                out.println("<p class='success'>✓ Connexion Hibernate OK</p>");
            } catch (Exception e) {
                out.println("<p class='error'>✗ Erreur Hibernate: " + e.getMessage() + "</p>");
                return;
            }
            
            // Test des rôles
            out.println("<h2>2. Vérification des Rôles</h2>");
            RoleDAO roleDAO = new RoleDAO();
            try {
                List<Role> roles = roleDAO.findAll();
                out.println("<p class='success'>✓ " + roles.size() + " rôles trouvés</p>");
                
                for (Role role : roles) {
                    out.println("<p class='info'>  - " + role.getNom() + " (ID: " + role.getId() + ")</p>");
                }
                
                // Vérifier spécifiquement le rôle USER
                Role userRole = roleDAO.findByNom("USER");
                if (userRole != null) {
                    out.println("<p class='success'>✓ Rôle USER trouvé (ID: " + userRole.getId() + ")</p>");
                } else {
                    out.println("<p class='error'>✗ Rôle USER non trouvé !</p>");
                    out.println("<p class='info'>Création du rôle USER...</p>");
                    Role newUserRole = new Role("USER");
                    roleDAO.create(newUserRole);
                    out.println("<p class='success'>✓ Rôle USER créé</p>");
                }
                
                Role adminRole = roleDAO.findByNom("ADMIN");
                if (adminRole != null) {
                    out.println("<p class='success'>✓ Rôle ADMIN trouvé (ID: " + adminRole.getId() + ")</p>");
                } else {
                    out.println("<p class='error'>✗ Rôle ADMIN non trouvé !</p>");
                }
                
            } catch (Exception e) {
                out.println("<p class='error'>✗ Erreur lors de la vérification des rôles: " + e.getMessage() + "</p>");
                e.printStackTrace();
            }
            
            // Test des utilisateurs
            out.println("<h2>3. Vérification des Utilisateurs</h2>");
            UserDAO userDAO = new UserDAO();
            try {
                List<User> users = userDAO.findAll();
                out.println("<p class='success'>✓ " + users.size() + " utilisateurs trouvés</p>");
                
                for (User user : users) {
                    String roleNom = user.getRole() != null ? user.getRole().getNom() : "NULL";
                    out.println("<p class='info'>  - " + user.getUsername() + " (" + roleNom + ") - " + 
                               (user.isActif() ? "Actif" : "Bloqué") + "</p>");
                }
                
            } catch (Exception e) {
                out.println("<p class='error'>✗ Erreur lors de la vérification des utilisateurs: " + e.getMessage() + "</p>");
                e.printStackTrace();
            }
            
            // Test des classes
            out.println("<h2>4. Vérification des Classes</h2>");
            ClasseDAO classeDAO = new ClasseDAO();
            try {
                List<Classe> classes = classeDAO.findAll();
                out.println("<p class='success'>✓ " + classes.size() + " classes trouvées</p>");
                
                for (Classe classe : classes) {
                    out.println("<p class='info'>  - " + classe.getNom() + " (coef: " + classe.getCoefficientPrix() + ")</p>");
                }
                
            } catch (Exception e) {
                out.println("<p class='error'>✗ Erreur lors de la vérification des classes: " + e.getMessage() + "</p>");
                e.printStackTrace();
            }
            
            // Test de création d'utilisateur
            out.println("<h2>5. Test de Création d'Utilisateur</h2>");
            try {
                Role userRole = roleDAO.findByNom("USER");
                if (userRole != null) {
                    // Vérifier si l'utilisateur test existe déjà
                    User testUser = userDAO.findByUsername("test_diagnostic");
                    if (testUser == null) {
                        User newUser = new User("test_diagnostic", "password123", "Test", "Diagnostic", "test@diagnostic.com");
                        newUser.setRole(userRole);
                        newUser.setActif(true);
                        
                        boolean success = userDAO.create(newUser);
                        if (success) {
                            out.println("<p class='success'>✓ Création d'utilisateur test réussie</p>");
                            // Supprimer l'utilisateur test
                            userDAO.deleteById(newUser.getId());
                            out.println("<p class='info'>  - Utilisateur test supprimé</p>");
                        } else {
                            out.println("<p class='error'>✗ Échec de la création d'utilisateur test</p>");
                        }
                    } else {
                        out.println("<p class='info'>Utilisateur test existe déjà</p>");
                    }
                } else {
                    out.println("<p class='error'>✗ Impossible de tester : rôle USER non trouvé</p>");
                }
                
            } catch (Exception e) {
                out.println("<p class='error'>✗ Erreur lors du test de création: " + e.getMessage() + "</p>");
                e.printStackTrace();
            }
            
            out.println("<h2>6. Recommandations</h2>");
            out.println("<p class='info'>Si vous voyez des erreurs ci-dessus :</p>");
            out.println("<ul>");
            out.println("<li>Redémarrez l'application pour réinitialiser les données</li>");
            out.println("<li>Vérifiez la configuration Hibernate</li>");
            out.println("<li>Consultez les logs Tomcat pour plus de détails</li>");
            out.println("</ul>");
            
            out.println("<p><a href='" + request.getContextPath() + "/auth?page=register'>Tester la création de compte</a></p>");
            out.println("<p><a href='" + request.getContextPath() + "/test'>Page de test principale</a></p>");
            
        } catch (Exception e) {
            out.println("<p class='error'>Erreur générale: " + e.getMessage() + "</p>");
            e.printStackTrace();
        }
        
        out.println("</body></html>");
    }
}
