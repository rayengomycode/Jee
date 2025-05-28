package model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "gares")
public class Gare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nom;
    
    @Column(nullable = false)
    private String ville;
    
    @Column
    private String adresse;
    
    @OneToMany(mappedBy = "gare")
    private Set<GareTrajet> gareTrajets = new HashSet<>();
    
    // Constructeurs
    public Gare() {
    }
    
    public Gare(String nom, String ville, String adresse) {
        this.nom = nom;
        this.ville = ville;
        this.adresse = adresse;
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

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Set<GareTrajet> getGareTrajets() {
        return gareTrajets;
    }

    public void setGareTrajets(Set<GareTrajet> gareTrajets) {
        this.gareTrajets = gareTrajets;
    }
}
