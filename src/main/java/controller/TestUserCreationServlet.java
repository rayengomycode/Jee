package controller;

import dao.*;
import model.*;
import util.PasswordUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet pour tester spécifiquement la création d'utilisateur
 */
public class TestUserCreationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Test Création Utilisateur</title>");
        out.println("<style>body{font-family:Arial,sans-serif;margin:20px;} .success{color:green;} .error{color:red;} .info{color:blue;}</style>");
        out.println("</head><body>");
        out.println("<h1>Test de Création d'Utilisateur</h1>");
        
        UserDAO userDAO = new UserDAO();
        RoleDAO roleDAO = new RoleDAO();
        
        try {
            // Étape 1 : Vérifier le rôle USER
            out.println("<h2>1. Vérification du rôle USER</h2>");
            Role userRole = roleDAO.findByNom("USER");
            
            if (userRole == null) {
                out.println("<p class='error'>✗ Rôle USER non trouvé ! Création...</p>");
                userRole = new Role("USER");
                boolean roleCreated = roleDAO.create(userRole);
                
                if (roleCreated) {
                    out.println("<p class='success'>✓ Rôle USER créé avec succès</p>");
                    // Récupérer le rôle créé avec son ID
                    userRole = roleDAO.findByNom("USER");
                } else {
                    out.println("<p class='error'>✗ Échec de la création du rôle USER</p>");
                    return;
                }
            } else {
                out.println("<p class='success'>✓ Rôle USER trouvé (ID: " + userRole.getId() + ")</p>");
            }
            
            // Étape 2 : Créer un utilisateur de test
            out.println("<h2>2. Création d'un utilisateur de test</h2>");
            
            String testUsername = "testuser_" + System.currentTimeMillis();
            String testEmail = "test_" + System.currentTimeMillis() + "@example.com";
            
            out.println("<p class='info'>Username: " + testUsername + "</p>");
            out.println("<p class='info'>Email: " + testEmail + "</p>");
            
            // Vérifier que l'utilisateur n'existe pas déjà
            User existingUser = userDAO.findByUsername(testUsername);
            if (existingUser != null) {
                out.println("<p class='error'>✗ Utilisateur existe déjà !</p>");
                return;
            }
            
            // Créer l'utilisateur
            User newUser = new User(testUsername, PasswordUtil.hashPassword("password123"), 
                                  "Test", "User", testEmail);
            newUser.setRole(userRole);
            newUser.setActif(true);
            
            out.println("<p class='info'>Données utilisateur préparées :</p>");
            out.println("<ul>");
            out.println("<li>Username: " + newUser.getUsername() + "</li>");
            out.println("<li>Password: [HASHED]</li>");
            out.println("<li>Nom: " + newUser.getNom() + "</li>");
            out.println("<li>Prénom: " + newUser.getPrenom() + "</li>");
            out.println("<li>Email: " + newUser.getEmail() + "</li>");
            out.println("<li>Role ID: " + (newUser.getRole() != null ? newUser.getRole().getId() : "NULL") + "</li>");
            out.println("<li>Actif: " + newUser.isActif() + "</li>");
            out.println("</ul>");
            
            // Tentative de création
            out.println("<h3>Tentative de création...</h3>");
            boolean success = userDAO.create(newUser);
            
            if (success) {
                out.println("<p class='success'>✓ Utilisateur créé avec succès !</p>");
                
                // Vérifier que l'utilisateur a bien été créé
                User createdUser = userDAO.findByUsername(testUsername);
                if (createdUser != null) {
                    out.println("<p class='success'>✓ Utilisateur retrouvé en base (ID: " + createdUser.getId() + ")</p>");
                    
                    // Nettoyer : supprimer l'utilisateur de test
                    boolean deleted = userDAO.deleteById(createdUser.getId());
                    if (deleted) {
                        out.println("<p class='info'>✓ Utilisateur de test supprimé</p>");
                    }
                } else {
                    out.println("<p class='error'>✗ Utilisateur non retrouvé en base après création</p>");
                }
                
            } else {
                out.println("<p class='error'>✗ Échec de la création de l'utilisateur</p>");
            }
            
            // Étape 3 : Test avec données invalides
            out.println("<h2>3. Test avec données invalides</h2>");
            
            // Test avec username null
            try {
                User invalidUser = new User(null, "password", "Test", "User", "test@test.com");
                invalidUser.setRole(userRole);
                boolean invalidSuccess = userDAO.create(invalidUser);
                
                if (!invalidSuccess) {
                    out.println("<p class='success'>✓ Création correctement refusée pour username null</p>");
                } else {
                    out.println("<p class='error'>✗ Création acceptée avec username null (problème !)</p>");
                }
            } catch (Exception e) {
                out.println("<p class='success'>✓ Exception levée pour username null : " + e.getMessage() + "</p>");
            }
            
            // Étape 4 : Statistiques
            out.println("<h2>4. Statistiques actuelles</h2>");
            try {
                java.util.List<User> allUsers = userDAO.findAll();
                out.println("<p class='info'>Nombre total d'utilisateurs : " + allUsers.size() + "</p>");
                
                for (User user : allUsers) {
                    String roleName = user.getRole() != null ? user.getRole().getNom() : "NULL";
                    out.println("<p class='info'>  - " + user.getUsername() + " (" + roleName + ")</p>");
                }
            } catch (Exception e) {
                out.println("<p class='error'>Erreur lors de la récupération des utilisateurs : " + e.getMessage() + "</p>");
            }
            
        } catch (Exception e) {
            out.println("<p class='error'>Erreur générale : " + e.getMessage() + "</p>");
            e.printStackTrace();
        }
        
        out.println("<h2>5. Actions recommandées</h2>");
        out.println("<p>Si des erreurs persistent :</p>");
        out.println("<ul>");
        out.println("<li>Consultez les logs Tomcat pour les détails</li>");
        out.println("<li>Vérifiez la configuration de la base de données</li>");
        out.println("<li>Redémarrez l'application</li>");
        out.println("</ul>");
        
        out.println("<p><a href='" + request.getContextPath() + "/diagnostic'>Diagnostic complet</a></p>");
        out.println("<p><a href='" + request.getContextPath() + "/auth?page=register'>Tester l'inscription</a></p>");
        
        out.println("</body></html>");
    }
}
