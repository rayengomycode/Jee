package model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "voyages")
public class Voyage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "trajet_id", nullable = false)
    private Trajet trajet;
    
    @ManyToOne
    @JoinColumn(name = "train_id", nullable = false)
    private Train train;
    
    @Column(nullable = false)
    private LocalDate date;
    
    @Column(nullable = false)
    private LocalTime heureDepart;
    
    @Column(nullable = false)
    private LocalTime heureArrivee;
    
    @Column(nullable = false)
    private double prix;
    
    @Column(nullable = false)
    private int placesDisponibles;
    
    @OneToMany(mappedBy = "voyage")
    private Set<Billet> billets = new HashSet<>();
    
    // Constructeurs
    public Voyage() {
    }
    
    public Voyage(Trajet trajet, Train train, LocalDate date, LocalTime heureDepart, 
                 LocalTime heureArrivee, double prix, int placesDisponibles) {
        this.trajet = trajet;
        this.train = train;
        this.date = date;
        this.heureDepart = heureDepart;
        this.heureArrivee = heureArrivee;
        this.prix = prix;
        this.placesDisponibles = placesDisponibles;
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Trajet getTrajet() {
        return trajet;
    }

    public void setTrajet(Trajet trajet) {
        this.trajet = trajet;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getHeureDepart() {
        return heureDepart;
    }

    public void setHeureDepart(LocalTime heureDepart) {
        this.heureDepart = heureDepart;
    }

    public LocalTime getHeureArrivee() {
        return heureArrivee;
    }

    public void setHeureArrivee(LocalTime heureArrivee) {
        this.heureArrivee = heureArrivee;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getPlacesDisponibles() {
        return placesDisponibles;
    }

    public void setPlacesDisponibles(int placesDisponibles) {
        this.placesDisponibles = placesDisponibles;
    }

    public Set<Billet> getBillets() {
        return billets;
    }

    public void setBillets(Set<Billet> billets) {
        this.billets = billets;
    }
}
