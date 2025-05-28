package service;

import config.EmailConfig;
import model.Billet;
import model.User;
import model.Voyage;
import model.Gare;
import java.time.format.DateTimeFormatter;

/**
 * Service pour la génération de templates d'emails
 */
public class EmailTemplateService {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy à HH:mm");
    
    /**
     * Template de confirmation de réservation
     */
    public static String getBookingConfirmationTemplate(Billet billet) {
        StringBuilder html = new StringBuilder();
        
        html.append("<!DOCTYPE html>");
        html.append("<html><head><meta charset='UTF-8'>");
        html.append("<style>");
        html.append("body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }");
        html.append(".container { max-width: 600px; margin: 0 auto; padding: 20px; }");
        html.append(".header { background: #007bff; color: white; padding: 20px; text-align: center; }");
        html.append(".content { padding: 20px; background: #f8f9fa; }");
        html.append(".ticket-info { background: white; padding: 15px; margin: 10px 0; border-left: 4px solid #007bff; }");
        html.append(".footer { text-align: center; padding: 20px; color: #666; font-size: 12px; }");
        html.append(".btn { background: #007bff; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px; }");
        html.append("</style></head><body>");
        
        html.append("<div class='container'>");
        
        // En-tête
        html.append("<div class='header'>");
        html.append("<h1>Confirmation de Réservation</h1>");
        html.append("<p>Train Ticket System</p>");
        html.append("</div>");
        
        // Contenu principal
        html.append("<div class='content'>");
        html.append("<h2>Bonjour ").append(billet.getUser().getPrenom()).append(" ").append(billet.getUser().getNom()).append(",</h2>");
        html.append("<p>Votre réservation a été confirmée avec succès !</p>");
        
        // Informations du billet
        html.append("<div class='ticket-info'>");
        html.append("<h3>Détails de votre voyage</h3>");
        html.append("<p><strong>Numéro de billet :</strong> ").append(billet.getId()).append("</p>");
        
        if (billet.getVoyage() != null) {
            Voyage voyage = billet.getVoyage();
            if (voyage.getTrain() != null) {
                html.append("<p><strong>Train :</strong> ").append(voyage.getTrain().getNumero())
                    .append(" - ").append(voyage.getTrain().getNom()).append("</p>");
            }
            
            if (voyage.getTrajet() != null) {
                Gare depart = voyage.getTrajet().getGareDepart();
                Gare arrivee = voyage.getTrajet().getGareArrivee();
                if (depart != null) {
                    html.append("<p><strong>Départ :</strong> ").append(depart.getNom())
                        .append(" (").append(depart.getVille()).append(")</p>");
                }
                if (arrivee != null) {
                    html.append("<p><strong>Arrivée :</strong> ").append(arrivee.getNom())
                        .append(" (").append(arrivee.getVille()).append(")</p>");
                }
            }
            
            if (voyage.getDate() != null) {
                html.append("<p><strong>Date :</strong> ").append(voyage.getDate().format(DATE_FORMATTER)).append("</p>");
            }
            if (voyage.getHeureDepart() != null) {
                html.append("<p><strong>Heure de départ :</strong> ").append(voyage.getHeureDepart().format(TIME_FORMATTER)).append("</p>");
            }
            if (voyage.getHeureArrivee() != null) {
                html.append("<p><strong>Heure d'arrivée :</strong> ").append(voyage.getHeureArrivee().format(TIME_FORMATTER)).append("</p>");
            }
        }
        
        if (billet.getClasse() != null) {
            html.append("<p><strong>Classe :</strong> ").append(billet.getClasse().getNom()).append("</p>");
        }
        html.append("<p><strong>Siège :</strong> ").append(billet.getNumeroSiege() != null ? billet.getNumeroSiege() : "Non assigné").append("</p>");
        html.append("<p><strong>Prix :</strong> ").append(String.format("%.2f TND", billet.getPrix())).append("</p>");
        html.append("</div>");
        
        // Actions
        html.append("<p style='text-align: center; margin: 20px 0;'>");
        html.append("<a href='").append(EmailConfig.AppUrls.getBilletUrl(billet.getId())).append("' class='btn'>Voir mon billet</a> ");
        html.append("<a href='").append(EmailConfig.AppUrls.getPdfUrl(billet.getId())).append("' class='btn'>Télécharger PDF</a>");
        html.append("</p>");
        
        html.append("<p><strong>Important :</strong> Présentez ce billet lors du contrôle. Nous vous recommandons d'arriver en gare 15 minutes avant le départ.</p>");
        html.append("</div>");
        
        // Pied de page
        html.append("<div class='footer'>");
        html.append("<p>Cet email a été envoyé automatiquement, merci de ne pas y répondre.</p>");
        html.append("<p>Train Ticket System - Votre solution de transport ferroviaire</p>");
        html.append("<p><a href='").append(EmailConfig.AppUrls.CONTACT_URL).append("'>Nous contacter</a></p>");
        html.append("</div>");
        
        html.append("</div>");
        html.append("</body></html>");
        
        return html.toString();
    }
    
    /**
     * Template d'alerte de retard
     */
    public static String getDelayAlertTemplate(Voyage voyage, int delayMinutes, User user) {
        StringBuilder html = new StringBuilder();
        
        html.append("<!DOCTYPE html>");
        html.append("<html><head><meta charset='UTF-8'>");
        html.append("<style>");
        html.append("body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }");
        html.append(".container { max-width: 600px; margin: 0 auto; padding: 20px; }");
        html.append(".header { background: #ffc107; color: #212529; padding: 20px; text-align: center; }");
        html.append(".content { padding: 20px; background: #fff3cd; border: 1px solid #ffeaa7; }");
        html.append(".alert { background: #f8d7da; padding: 15px; margin: 10px 0; border-left: 4px solid #dc3545; }");
        html.append(".footer { text-align: center; padding: 20px; color: #666; font-size: 12px; }");
        html.append("</style></head><body>");
        
        html.append("<div class='container'>");
        
        // En-tête
        html.append("<div class='header'>");
        html.append("<h1>⚠️ Alerte Retard</h1>");
        html.append("<p>Train Ticket System</p>");
        html.append("</div>");
        
        // Contenu
        html.append("<div class='content'>");
        html.append("<h2>Bonjour ").append(user.getPrenom()).append(",</h2>");
        
        html.append("<div class='alert'>");
        html.append("<h3>Votre train a du retard</h3>");
        html.append("<p><strong>Retard estimé :</strong> ").append(delayMinutes).append(" minutes</p>");
        html.append("</div>");
        
        // Informations du voyage
        if (voyage.getTrain() != null) {
            html.append("<p><strong>Train :</strong> ").append(voyage.getTrain().getNumero())
                .append(" - ").append(voyage.getTrain().getNom()).append("</p>");
        }
        
        if (voyage.getDate() != null) {
            html.append("<p><strong>Date :</strong> ").append(voyage.getDate().format(DATE_FORMATTER)).append("</p>");
        }
        
        if (voyage.getHeureDepart() != null) {
            html.append("<p><strong>Heure de départ prévue :</strong> ").append(voyage.getHeureDepart().format(TIME_FORMATTER)).append("</p>");
            html.append("<p><strong>Nouvelle heure estimée :</strong> ").append(voyage.getHeureDepart().plusMinutes(delayMinutes).format(TIME_FORMATTER)).append("</p>");
        }
        
        html.append("<p>Nous nous excusons pour ce désagrément et vous remercions de votre compréhension.</p>");
        html.append("<p><a href='").append(EmailConfig.AppUrls.BILLETS_URL).append("'>Voir mes billets</a></p>");
        html.append("</div>");
        
        // Pied de page
        html.append("<div class='footer'>");
        html.append("<p>Train Ticket System - Information voyageurs</p>");
        html.append("</div>");
        
        html.append("</div>");
        html.append("</body></html>");
        
        return html.toString();
    }
    
    /**
     * Template d'annulation de voyage
     */
    public static String getCancellationTemplate(Voyage voyage, User user, String reason) {
        StringBuilder html = new StringBuilder();
        
        html.append("<!DOCTYPE html>");
        html.append("<html><head><meta charset='UTF-8'>");
        html.append("<style>");
        html.append("body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }");
        html.append(".container { max-width: 600px; margin: 0 auto; padding: 20px; }");
        html.append(".header { background: #dc3545; color: white; padding: 20px; text-align: center; }");
        html.append(".content { padding: 20px; background: #f8d7da; border: 1px solid #f5c6cb; }");
        html.append(".info { background: white; padding: 15px; margin: 10px 0; border-left: 4px solid #dc3545; }");
        html.append(".footer { text-align: center; padding: 20px; color: #666; font-size: 12px; }");
        html.append("</style></head><body>");
        
        html.append("<div class='container'>");
        
        // En-tête
        html.append("<div class='header'>");
        html.append("<h1>🚫 Annulation de Voyage</h1>");
        html.append("<p>Train Ticket System</p>");
        html.append("</div>");
        
        // Contenu
        html.append("<div class='content'>");
        html.append("<h2>Bonjour ").append(user.getPrenom()).append(",</h2>");
        html.append("<p>Nous regrettons de vous informer que votre voyage a été annulé.</p>");
        
        html.append("<div class='info'>");
        if (voyage.getTrain() != null) {
            html.append("<p><strong>Train :</strong> ").append(voyage.getTrain().getNumero())
                .append(" - ").append(voyage.getTrain().getNom()).append("</p>");
        }
        if (voyage.getDate() != null) {
            html.append("<p><strong>Date :</strong> ").append(voyage.getDate().format(DATE_FORMATTER)).append("</p>");
        }
        if (reason != null && !reason.isEmpty()) {
            html.append("<p><strong>Raison :</strong> ").append(reason).append("</p>");
        }
        html.append("</div>");
        
        html.append("<p><strong>Remboursement :</strong> Vos billets seront automatiquement remboursés sous 3-5 jours ouvrables.</p>");
        html.append("<p>Pour toute question, n'hésitez pas à nous contacter.</p>");
        html.append("<p><a href='").append(EmailConfig.AppUrls.CONTACT_URL).append("'>Nous contacter</a></p>");
        html.append("</div>");
        
        // Pied de page
        html.append("<div class='footer'>");
        html.append("<p>Train Ticket System - Service client</p>");
        html.append("</div>");
        
        html.append("</div>");
        html.append("</body></html>");
        
        return html.toString();
    }
    
    /**
     * Template de rappel de voyage
     */
    public static String getTravelReminderTemplate(Billet billet) {
        StringBuilder html = new StringBuilder();
        
        html.append("<!DOCTYPE html>");
        html.append("<html><head><meta charset='UTF-8'>");
        html.append("<style>");
        html.append("body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }");
        html.append(".container { max-width: 600px; margin: 0 auto; padding: 20px; }");
        html.append(".header { background: #28a745; color: white; padding: 20px; text-align: center; }");
        html.append(".content { padding: 20px; background: #d4edda; border: 1px solid #c3e6cb; }");
        html.append(".reminder { background: white; padding: 15px; margin: 10px 0; border-left: 4px solid #28a745; }");
        html.append(".footer { text-align: center; padding: 20px; color: #666; font-size: 12px; }");
        html.append("</style></head><body>");
        
        html.append("<div class='container'>");
        
        // En-tête
        html.append("<div class='header'>");
        html.append("<h1>🚂 Rappel de Voyage</h1>");
        html.append("<p>Train Ticket System</p>");
        html.append("</div>");
        
        // Contenu
        html.append("<div class='content'>");
        html.append("<h2>Bonjour ").append(billet.getUser().getPrenom()).append(",</h2>");
        html.append("<p>Votre voyage est prévu demain ! N'oubliez pas votre billet.</p>");
        
        html.append("<div class='reminder'>");
        html.append("<h3>Rappel de votre voyage</h3>");
        if (billet.getVoyage() != null) {
            Voyage voyage = billet.getVoyage();
            if (voyage.getDate() != null) {
                html.append("<p><strong>Date :</strong> ").append(voyage.getDate().format(DATE_FORMATTER)).append("</p>");
            }
            if (voyage.getHeureDepart() != null) {
                html.append("<p><strong>Départ :</strong> ").append(voyage.getHeureDepart().format(TIME_FORMATTER)).append("</p>");
            }
        }
        html.append("<p><strong>Siège :</strong> ").append(billet.getNumeroSiege() != null ? billet.getNumeroSiege() : "Non assigné").append("</p>");
        html.append("</div>");
        
        html.append("<p><strong>Conseils :</strong></p>");
        html.append("<ul>");
        html.append("<li>Arrivez en gare 15 minutes avant le départ</li>");
        html.append("<li>Ayez votre billet prêt pour le contrôle</li>");
        html.append("<li>Vérifiez les éventuels changements de quai</li>");
        html.append("</ul>");
        
        html.append("<p><a href='").append(EmailConfig.AppUrls.getPdfUrl(billet.getId())).append("'>Télécharger mon billet PDF</a></p>");
        html.append("</div>");
        
        // Pied de page
        html.append("<div class='footer'>");
        html.append("<p>Bon voyage avec Train Ticket System !</p>");
        html.append("</div>");
        
        html.append("</div>");
        html.append("</body></html>");
        
        return html.toString();
    }
}
