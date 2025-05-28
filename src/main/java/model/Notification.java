package model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(nullable = false)
    private String titre;
    
    @Column(columnDefinition = "TEXT")
    private String message;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeNotification type;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutNotification statut = StatutNotification.NON_LUE;
    
    @Column(name = "dateCreation", nullable = false)
    private LocalDateTime dateCreation;
    
    @Column(name = "dateLecture")
    private LocalDateTime dateLecture;
    
    @Column(name = "emailEnvoye", nullable = false)
    private boolean emailEnvoye = false;
    
    @Column(name = "smsEnvoye", nullable = false)
    private boolean smsEnvoye = false;
    
    @ManyToOne
    @JoinColumn(name = "voyage_id")
    private Voyage voyage;
    
    @ManyToOne
    @JoinColumn(name = "billet_id")
    private Billet billet;
    
    // Énumérations
    public enum TypeNotification {
        CONFIRMATION_RESERVATION,
        RAPPEL_VOYAGE,
        RETARD_VOYAGE,
        ANNULATION_VOYAGE,
        CHANGEMENT_QUAI,
        REMBOURSEMENT,
        INFORMATION_GENERALE
    }
    
    public enum StatutNotification {
        NON_LUE,
        LUE,
        ARCHIVEE
    }
    
    // Constructeurs
    public Notification() {
        this.dateCreation = LocalDateTime.now();
    }
    
    public Notification(User user, String titre, String message, TypeNotification type) {
        this();
        this.user = user;
        this.titre = titre;
        this.message = message;
        this.type = type;
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public String getTitre() {
        return titre;
    }
    
    public void setTitre(String titre) {
        this.titre = titre;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public TypeNotification getType() {
        return type;
    }
    
    public void setType(TypeNotification type) {
        this.type = type;
    }
    
    public StatutNotification getStatut() {
        return statut;
    }
    
    public void setStatut(StatutNotification statut) {
        this.statut = statut;
    }
    
    public LocalDateTime getDateCreation() {
        return dateCreation;
    }
    
    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }
    
    public LocalDateTime getDateLecture() {
        return dateLecture;
    }
    
    public void setDateLecture(LocalDateTime dateLecture) {
        this.dateLecture = dateLecture;
    }
    
    public boolean isEmailEnvoye() {
        return emailEnvoye;
    }
    
    public void setEmailEnvoye(boolean emailEnvoye) {
        this.emailEnvoye = emailEnvoye;
    }
    
    public boolean isSmsEnvoye() {
        return smsEnvoye;
    }
    
    public void setSmsEnvoye(boolean smsEnvoye) {
        this.smsEnvoye = smsEnvoye;
    }
    
    public Voyage getVoyage() {
        return voyage;
    }
    
    public void setVoyage(Voyage voyage) {
        this.voyage = voyage;
    }
    
    public Billet getBillet() {
        return billet;
    }
    
    public void setBillet(Billet billet) {
        this.billet = billet;
    }
    
    // Méthodes utilitaires
    public void marquerCommeLue() {
        this.statut = StatutNotification.LUE;
        this.dateLecture = LocalDateTime.now();
    }
    
    public void archiver() {
        this.statut = StatutNotification.ARCHIVEE;
    }
    
    public boolean isLue() {
        return this.statut == StatutNotification.LUE || this.statut == StatutNotification.ARCHIVEE;
    }
    
    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", type=" + type +
                ", statut=" + statut +
                ", dateCreation=" + dateCreation +
                '}';
    }
}
