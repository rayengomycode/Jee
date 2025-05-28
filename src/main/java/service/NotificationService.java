package service;

import dao.NotificationDAO;
import dao.BilletDAO;
import model.*;
import util.PDFGenerator;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service principal pour la gestion des notifications
 */
public class NotificationService {

    private static NotificationService instance;
    private NotificationDAO notificationDAO;
    private EmailService emailService;

    private NotificationService() {
        this.notificationDAO = new NotificationDAO();
        this.emailService = EmailService.getInstance();
    }

    public static NotificationService getInstance() {
        if (instance == null) {
            synchronized (NotificationService.class) {
                if (instance == null) {
                    instance = new NotificationService();
                }
            }
        }
        return instance;
    }

    /**
     * Envoie une notification de confirmation de réservation
     */
    public boolean sendBookingConfirmation(Billet billet) {
        try {
            // Créer la notification en base
            Notification notification = new Notification(
                billet.getUser(),
                "Confirmation de réservation",
                "Votre réservation pour le billet #" + billet.getId() + " a été confirmée.",
                Notification.TypeNotification.CONFIRMATION_RESERVATION
            );
            notification.setBillet(billet);
            notification.setVoyage(billet.getVoyage());

            notificationDAO.create(notification);

            // Envoyer l'email avec le PDF en pièce jointe
            String emailContent = EmailTemplateService.getBookingConfirmationTemplate(billet);
            boolean emailSent = false;

            try {
                // Générer le PDF du billet
                byte[] pdfBytes = PDFGenerator.generateBilletPDF(billet);
                String pdfFileName = "billet_" + billet.getId() + ".pdf";

                emailSent = emailService.sendEmailWithAttachment(
                    billet.getUser().getEmail(),
                    "Confirmation de réservation - Billet #" + billet.getId(),
                    emailContent,
                    pdfBytes,
                    pdfFileName,
                    "application/pdf"
                );
            } catch (Exception e) {
                // Si erreur avec PDF, envoyer email simple
                emailSent = emailService.sendHtmlEmail(
                    billet.getUser().getEmail(),
                    "Confirmation de réservation - Billet #" + billet.getId(),
                    emailContent
                );
            }

            // Mettre à jour le statut d'envoi
            if (emailSent) {
                notification.setEmailEnvoye(true);
                notificationDAO.update(notification);
            }

            return true;

        } catch (Exception e) {
            System.err.println("Erreur lors de l'envoi de la confirmation de réservation: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Envoie une alerte de retard
     */
    public boolean sendDelayAlert(Voyage voyage, int delayMinutes, String reason) {
        try {
            // Récupérer tous les billets pour ce voyage
            List<Billet> billets = getBilletsForVoyage(voyage);

            boolean allSent = true;
            for (Billet billet : billets) {
                if (billet.getEtat() == Billet.EtatBillet.ACHETE) {
                    // Créer la notification
                    Notification notification = new Notification(
                        billet.getUser(),
                        "Retard de voyage - " + delayMinutes + " minutes",
                        "Votre train " + voyage.getTrain().getNumero() + " a " + delayMinutes + " minutes de retard. " +
                        (reason != null ? "Raison: " + reason : ""),
                        Notification.TypeNotification.RETARD_VOYAGE
                    );
                    notification.setVoyage(voyage);
                    notification.setBillet(billet);

                    notificationDAO.create(notification);

                    // Envoyer email
                    String emailContent = EmailTemplateService.getDelayAlertTemplate(voyage, delayMinutes, billet.getUser());
                    boolean emailSent = emailService.sendHtmlEmail(
                        billet.getUser().getEmail(),
                        "Alerte retard - Train " + voyage.getTrain().getNumero(),
                        emailContent
                    );

                    // Envoyer SMS si retard important et numéro disponible
                    boolean smsSent = false;
                    if (delayMinutes >= 30 && billet.getUser().getTelephone() != null) {
                        String smsMessage = "RETARD: Votre train " + voyage.getTrain().getNumero() +
                                          " a " + delayMinutes + " min de retard. Nouvelle heure: " +
                                          voyage.getHeureDepart().plusMinutes(delayMinutes).format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"));
                        smsSent = sendSMS(billet.getUser().getTelephone(), smsMessage);
                    }

                    // Mettre à jour les statuts d'envoi
                    if (emailSent) notification.setEmailEnvoye(true);
                    if (smsSent) notification.setSmsEnvoye(true);
                    notificationDAO.update(notification);

                    if (!emailSent) allSent = false;
                }
            }

            return allSent;

        } catch (Exception e) {
            System.err.println("Erreur lors de l'envoi des alertes de retard: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Envoie une notification d'annulation
     */
    public boolean sendCancellationAlert(Voyage voyage, String reason) {
        try {
            List<Billet> billets = getBilletsForVoyage(voyage);

            boolean allSent = true;
            for (Billet billet : billets) {
                if (billet.getEtat() == Billet.EtatBillet.ACHETE) {
                    // Créer la notification
                    Notification notification = new Notification(
                        billet.getUser(),
                        "Annulation de voyage",
                        "Votre voyage du " + voyage.getDate() + " a été annulé. " +
                        (reason != null ? "Raison: " + reason : "") + " Remboursement automatique en cours.",
                        Notification.TypeNotification.ANNULATION_VOYAGE
                    );
                    notification.setVoyage(voyage);
                    notification.setBillet(billet);

                    notificationDAO.create(notification);

                    // Envoyer email
                    String emailContent = EmailTemplateService.getCancellationTemplate(voyage, billet.getUser(), reason);
                    boolean emailSent = emailService.sendHtmlEmail(
                        billet.getUser().getEmail(),
                        "Annulation de voyage - Train " + voyage.getTrain().getNumero(),
                        emailContent
                    );

                    // Envoyer SMS urgent
                    boolean smsSent = false;
                    if (billet.getUser().getTelephone() != null) {
                        String smsMessage = "ANNULATION: Votre train " + voyage.getTrain().getNumero() +
                                          " du " + voyage.getDate() + " est annulé. Remboursement automatique.";
                        smsSent = sendSMS(billet.getUser().getTelephone(), smsMessage);
                    }

                    // Mettre à jour les statuts
                    if (emailSent) notification.setEmailEnvoye(true);
                    if (smsSent) notification.setSmsEnvoye(true);
                    notificationDAO.update(notification);

                    if (!emailSent) allSent = false;
                }
            }

            return allSent;

        } catch (Exception e) {
            System.err.println("Erreur lors de l'envoi des notifications d'annulation: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Envoie un rappel de voyage (24h avant)
     */
    public boolean sendTravelReminder(Billet billet) {
        try {
            // Créer la notification
            Notification notification = new Notification(
                billet.getUser(),
                "Rappel de voyage",
                "Votre voyage est prévu demain. N'oubliez pas votre billet !",
                Notification.TypeNotification.RAPPEL_VOYAGE
            );
            notification.setBillet(billet);
            notification.setVoyage(billet.getVoyage());

            notificationDAO.create(notification);

            // Envoyer email
            String emailContent = EmailTemplateService.getTravelReminderTemplate(billet);
            boolean emailSent = emailService.sendHtmlEmail(
                billet.getUser().getEmail(),
                "Rappel de voyage - Demain",
                emailContent
            );

            // Mettre à jour le statut
            if (emailSent) {
                notification.setEmailEnvoye(true);
                notificationDAO.update(notification);
            }

            return emailSent;

        } catch (Exception e) {
            System.err.println("Erreur lors de l'envoi du rappel de voyage: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Crée une notification simple
     */
    public boolean createNotification(User user, String titre, String message,
                                    Notification.TypeNotification type) {
        try {
            Notification notification = new Notification(user, titre, message, type);
            return notificationDAO.create(notification);
        } catch (Exception e) {
            System.err.println("Erreur lors de la création de notification: " + e.getMessage());
            return false;
        }
    }

    /**
     * Récupère les notifications d'un utilisateur
     */
    public List<Notification> getUserNotifications(User user) {
        return notificationDAO.findByUser(user);
    }

    /**
     * Récupère les notifications non lues d'un utilisateur
     */
    public List<Notification> getUnreadNotifications(User user) {
        return notificationDAO.findUnreadByUser(user);
    }

    /**
     * Compte les notifications non lues
     */
    public long countUnreadNotifications(User user) {
        return notificationDAO.countUnreadByUser(user);
    }

    /**
     * Marque une notification comme lue
     */
    public boolean markAsRead(Long notificationId) {
        return notificationDAO.markAsRead(notificationId);
    }

    /**
     * Marque toutes les notifications comme lues
     */
    public boolean markAllAsRead(User user) {
        return notificationDAO.markAllAsReadForUser(user);
    }

    /**
     * Traite les notifications en attente d'envoi
     */
    public void processePendingNotifications() {
        try {
            // Traiter les emails en attente
            List<Notification> pendingEmails = notificationDAO.findPendingEmailNotifications();
            for (Notification notification : pendingEmails) {
                // Logique d'envoi d'email selon le type
                // (implémentation simplifiée)
                notification.setEmailEnvoye(true);
                notificationDAO.update(notification);
            }

            // Traiter les SMS en attente
            List<Notification> pendingSms = notificationDAO.findPendingSmsNotifications();
            for (Notification notification : pendingSms) {
                // Logique d'envoi SMS
                notification.setSmsEnvoye(true);
                notificationDAO.update(notification);
            }

        } catch (Exception e) {
            System.err.println("Erreur lors du traitement des notifications en attente: " + e.getMessage());
        }
    }

    // Méthodes utilitaires privées

    private List<Billet> getBilletsForVoyage(Voyage voyage) {
        // Utiliser BilletDAO pour récupérer les billets d'un voyage
        try {
            BilletDAO billetDAO = new BilletDAO();
            return billetDAO.findByVoyage(voyage);
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des billets pour le voyage " + voyage.getId() + ": " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }

    private boolean sendSMS(String phoneNumber, String message) {
        // Simulation d'envoi SMS
        // Dans une vraie implémentation, utiliser une API comme Twilio
        System.out.println("SMS envoyé à " + phoneNumber + ": " + message);
        return true;
    }
}
