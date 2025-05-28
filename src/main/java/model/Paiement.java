package model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "paiements")
public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "billet_id", nullable = false)
    private Billet billet;
    
    @Column(nullable = false)
    private double montant;
    
    @Column(nullable = false)
    private LocalDateTime datePaiement;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MethodePaiement methode;
    
    @Column(nullable = false)
    private String reference;
    
    @Column
    private String details;
    
    // Enum pour la méthode de paiement
    public enum MethodePaiement {
        CARTE_CREDIT, VIREMENT, PAYPAL
    }
    
    // Constructeurs
    public Paiement() {
    }
    
    public Paiement(Billet billet, double montant, LocalDateTime datePaiement, 
                   MethodePaiement methode, String reference, String details) {
        this.billet = billet;
        this.montant = montant;
        this.datePaiement = datePaiement;
        this.methode = methode;
        this.reference = reference;
        this.details = details;
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Billet getBillet() {
        return billet;
    }

    public void setBillet(Billet billet) {
        this.billet = billet;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public LocalDateTime getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDateTime datePaiement) {
        this.datePaiement = datePaiement;
    }

    public MethodePaiement getMethode() {
        return methode;
    }

    public void setMethode(MethodePaiement methode) {
        this.methode = methode;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
