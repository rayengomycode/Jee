package controller;

import config.EmailConfig;
import service.EmailService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet pour tester directement l'EmailService
 */
public class TestEmailDirectServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Test Email Direct</title>");
        out.println("<style>body{font-family:Arial,sans-serif;margin:20px;} .success{color:green;background:#d4edda;padding:10px;border-radius:5px;} .error{color:red;background:#f8d7da;padding:10px;border-radius:5px;} .info{color:blue;background:#e7f3ff;padding:10px;border-radius:5px;}</style>");
        out.println("</head><body>");
        out.println("<h1>🔧 Test Email Direct</h1>");
        
        // Test de configuration
        out.println("<div class='info'>");
        out.println("<h3>Configuration actuelle :</h3>");
        out.println("<p><strong>TEST_MODE :</strong> " + EmailConfig.TEST_MODE + "</p>");
        out.println("<p><strong>isConfigured() :</strong> " + EmailConfig.isConfigured() + "</p>");
        out.println("<p><strong>From Email :</strong> " + EmailConfig.getFromEmail() + "</p>");
        out.println("<p><strong>SMTP Username :</strong> " + EmailConfig.getSmtpUsername() + "</p>");
        out.println("</div>");
        
        // Test d'envoi simple
        out.println("<h3>Test d'envoi simple :</h3>");
        try {
            EmailService emailService = EmailService.getInstance();
            
            System.out.println("=== DEBUT TEST EMAIL DIRECT ===");
            
            boolean result = emailService.sendEmail(
                "test@example.com", 
                "Test Email Direct", 
                "Ceci est un test d'email direct depuis le servlet."
            );
            
            System.out.println("=== FIN TEST EMAIL DIRECT ===");
            
            if (result) {
                out.println("<div class='success'>✅ Email envoyé avec succès ! Vérifiez les logs Tomcat.</div>");
            } else {
                out.println("<div class='error'>❌ Échec de l'envoi de l'email.</div>");
            }
            
        } catch (Exception e) {
            out.println("<div class='error'>❌ Erreur : " + e.getMessage() + "</div>");
            e.printStackTrace();
        }
        
        // Test avec HTML
        out.println("<h3>Test d'envoi HTML :</h3>");
        try {
            EmailService emailService = EmailService.getInstance();
            
            String htmlContent = "<html><body>" +
                "<h1 style='color:blue;'>Test Email HTML</h1>" +
                "<p>Ceci est un <strong>email HTML</strong> de test.</p>" +
                "<p>Envoyé depuis : Train Ticket System</p>" +
                "</body></html>";
            
            System.out.println("=== DEBUT TEST EMAIL HTML ===");
            
            boolean result = emailService.sendHtmlEmail(
                "test@example.com", 
                "Test Email HTML", 
                htmlContent
            );
            
            System.out.println("=== FIN TEST EMAIL HTML ===");
            
            if (result) {
                out.println("<div class='success'>✅ Email HTML envoyé avec succès !</div>");
            } else {
                out.println("<div class='error'>❌ Échec de l'envoi de l'email HTML.</div>");
            }
            
        } catch (Exception e) {
            out.println("<div class='error'>❌ Erreur HTML : " + e.getMessage() + "</div>");
            e.printStackTrace();
        }
        
        // Instructions
        out.println("<h3>📋 Instructions :</h3>");
        out.println("<div class='info'>");
        out.println("<p>1. <strong>Vérifiez les logs Tomcat</strong> pour voir les emails simulés</p>");
        out.println("<p>2. Si aucun log n'apparaît, il y a un problème avec EmailService</p>");
        out.println("<p>3. Les emails sont simulés car la configuration n'est pas définie</p>");
        out.println("</div>");
        
        out.println("<p><a href='" + request.getContextPath() + "/test-notifications'>Retour aux tests notifications</a></p>");
        out.println("</body></html>");
    }
}
