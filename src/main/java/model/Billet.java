package model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "billets")
public class Billet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "voyage_id", nullable = false)
    private Voyage voyage;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "classe_id", nullable = false)
    private Classe classe;
    
    @Column(nullable = false)
    private String numeroSiege;
    
    @Column(nullable = false)
    private double prix;
    
    @Column(nullable = false)
    private LocalDateTime dateAchat;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EtatBillet etat;
    
    @Column
    private String preferences;
    
    @OneToOne(mappedBy = "billet", cascade = CascadeType.ALL)
    private Paiement paiement;
    
    // Enum pour l'état du billet
    public enum EtatBillet {
        ACHETE, UTILISE, ANNULE
    }
    
    // Constructeurs
    public Billet() {
    }
    
    public Billet(Voyage voyage, User user, Classe classe, String numeroSiege, 
                 double prix, LocalDateTime dateAchat, EtatBillet etat, String preferences) {
        this.voyage = voyage;
        this.user = user;
        this.classe = classe;
        this.numeroSiege = numeroSiege;
        this.prix = prix;
        this.dateAchat = dateAchat;
        this.etat = etat;
        this.preferences = preferences;
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Voyage getVoyage() {
        return voyage;
    }

    public void setVoyage(Voyage voyage) {
        this.voyage = voyage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public String getNumeroSiege() {
        return numeroSiege;
    }

    public void setNumeroSiege(String numeroSiege) {
        this.numeroSiege = numeroSiege;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public LocalDateTime getDateAchat() {
        return dateAchat;
    }

    public void setDateAchat(LocalDateTime dateAchat) {
        this.dateAchat = dateAchat;
    }

    public EtatBillet getEtat() {
        return etat;
    }

    public void setEtat(EtatBillet etat) {
        this.etat = etat;
    }

    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }

    public Paiement getPaiement() {
        return paiement;
    }

    public void setPaiement(Paiement paiement) {
        this.paiement = paiement;
    }
}
