package model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "trains")
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String numero;
    
    @Column(nullable = false)
    private String nom;
    
    @Column(nullable = false)
    private int capaciteTotal;
    
    @OneToMany(mappedBy = "train")
    private Set<Voyage> voyages = new HashSet<>();
    
    // Constructeurs
    public Train() {
    }
    
    public Train(String numero, String nom, int capaciteTotal) {
        this.numero = numero;
        this.nom = nom;
        this.capaciteTotal = capaciteTotal;
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getCapaciteTotal() {
        return capaciteTotal;
    }

    public void setCapaciteTotal(int capaciteTotal) {
        this.capaciteTotal = capaciteTotal;
    }

    public Set<Voyage> getVoyages() {
        return voyages;
    }

    public void setVoyages(Set<Voyage> voyages) {
        this.voyages = voyages;
    }
}
