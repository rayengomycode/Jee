package service;

import config.EmailConfig;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.activation.DataHandler;
import java.util.Properties;

/**
 * Service pour l'envoi d'emails
 */
public class EmailService {

    private static EmailService instance;
    private Session mailSession;

    private EmailService() {
        initializeMailSession();
    }

    public static EmailService getInstance() {
        if (instance == null) {
            synchronized (EmailService.class) {
                if (instance == null) {
                    instance = new EmailService();
                }
            }
        }
        return instance;
    }

    private void initializeMailSession() {
        Properties props = EmailConfig.getSmtpProperties();

        mailSession = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                    EmailConfig.getSmtpUsername(),
                    EmailConfig.getSmtpPassword()
                );
            }
        });

        // Activer le debug en mode développement
        if (EmailConfig.Environment.isDevelopment()) {
            mailSession.setDebug(true);
        }
    }

    /**
     * Envoie un email simple
     */
    public boolean sendEmail(String to, String subject, String content) {
        return sendEmail(to, subject, content, false);
    }

    /**
     * Envoie un email HTML
     */
    public boolean sendHtmlEmail(String to, String subject, String htmlContent) {
        return sendEmail(to, subject, htmlContent, true);
    }

    /**
     * Envoie un email avec ou sans HTML
     */
    private boolean sendEmail(String to, String subject, String content, boolean isHtml) {
        try {
            // Vérifier la configuration
            if (!EmailConfig.isConfigured()) {
                // Utiliser System.err pour forcer l'affichage
                System.err.println("=== EMAIL SIMULATION ===");
                System.err.println("Configuration email non définie. Simulation d'envoi:");
                System.err.println("TO: " + to);
                System.err.println("SUBJECT: " + subject);
                System.err.println("CONTENT: " + (content.length() > 200 ? content.substring(0, 200) + "..." : content));
                System.err.println("HTML: " + isHtml);
                System.err.println("=== FIN SIMULATION ===");

                // Aussi dans System.out au cas où
                System.out.println("EMAIL SIMULE ENVOYE A: " + to);
                return true; // Simulation réussie
            }

            Message message = new MimeMessage(mailSession);

            // Expéditeur
            message.setFrom(new InternetAddress(
                EmailConfig.getFromEmail(),
                EmailConfig.getFromName()
            ));

            // Destinataire
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            // Sujet
            message.setSubject(subject);

            // Contenu
            if (isHtml) {
                message.setContent(content, "text/html; charset=utf-8");
            } else {
                message.setText(content);
            }

            // Envoi
            Transport.send(message);

            System.out.println("Email envoyé avec succès à: " + to);
            return true;

        } catch (Exception e) {
            System.err.println("Erreur lors de l'envoi de l'email à " + to + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Envoie un email avec pièce jointe
     */
    public boolean sendEmailWithAttachment(String to, String subject, String content,
                                         byte[] attachment, String attachmentName, String mimeType) {
        try {
            if (!EmailConfig.isConfigured()) {
                System.out.println("=== EMAIL AVEC PIECE JOINTE SIMULATION ===");
                System.out.println("Configuration email non définie. Simulation d'envoi avec pièce jointe:");
                System.out.println("TO: " + to);
                System.out.println("SUBJECT: " + subject);
                System.out.println("CONTENT: " + (content.length() > 200 ? content.substring(0, 200) + "..." : content));
                System.out.println("ATTACHMENT: " + attachmentName + " (" + (attachment != null ? attachment.length : 0) + " bytes)");
                System.out.println("MIME TYPE: " + mimeType);
                System.out.println("=== FIN SIMULATION PIECE JOINTE ===");
                return true;
            }

            Message message = new MimeMessage(mailSession);

            // Expéditeur
            message.setFrom(new InternetAddress(
                EmailConfig.getFromEmail(),
                EmailConfig.getFromName()
            ));

            // Destinataire
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            // Sujet
            message.setSubject(subject);

            // Créer le multipart
            Multipart multipart = new MimeMultipart();

            // Partie texte
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(content, "text/html; charset=utf-8");
            multipart.addBodyPart(messageBodyPart);

            // Partie pièce jointe
            if (attachment != null && attachment.length > 0) {
                messageBodyPart = new MimeBodyPart();
                messageBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(attachment, mimeType)));
                messageBodyPart.setFileName(attachmentName);
                multipart.addBodyPart(messageBodyPart);
            }

            // Définir le contenu du message
            message.setContent(multipart);

            // Envoi
            Transport.send(message);

            System.out.println("Email avec pièce jointe envoyé avec succès à: " + to);
            return true;

        } catch (Exception e) {
            System.err.println("Erreur lors de l'envoi de l'email avec pièce jointe à " + to + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Teste la configuration email
     */
    public boolean testEmailConfiguration() {
        try {
            String testEmail = EmailConfig.getFromEmail();
            return sendEmail(testEmail, "Test Configuration",
                "Ceci est un email de test pour vérifier la configuration SMTP.");
        } catch (Exception e) {
            System.err.println("Erreur lors du test de configuration email: " + e.getMessage());
            return false;
        }
    }

    /**
     * Classe utilitaire pour les pièces jointes
     */
    private static class ByteArrayDataSource implements jakarta.activation.DataSource {
        private byte[] data;
        private String type;

        public ByteArrayDataSource(byte[] data, String type) {
            this.data = data;
            this.type = type;
        }

        @Override
        public java.io.InputStream getInputStream() {
            return new java.io.ByteArrayInputStream(data);
        }

        @Override
        public java.io.OutputStream getOutputStream() {
            throw new UnsupportedOperationException("Not supported");
        }

        @Override
        public String getContentType() {
            return type;
        }

        @Override
        public String getName() {
            return "attachment";
        }
    }
}
