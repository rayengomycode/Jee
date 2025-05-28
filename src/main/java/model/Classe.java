package model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "classes")
public class Classe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String nom;
    
    @Column(nullable = false)
    private double coefficientPrix;
    
    @OneToMany(mappedBy = "classe")
    private Set<Billet> billets = new HashSet<>();
    
    // Constructeurs
    public Classe() {
    }
    
    public Classe(String nom, double coefficientPrix) {
        this.nom = nom;
        this.coefficientPrix = coefficientPrix;
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getCoefficientPrix() {
        return coefficientPrix;
    }

    public void setCoefficientPrix(double coefficientPrix) {
        this.coefficientPrix = coefficientPrix;
    }

    public Set<Billet> getBillets() {
        return billets;
    }

    public void setBillets(Set<Billet> billets) {
        this.billets = billets;
    }
}
