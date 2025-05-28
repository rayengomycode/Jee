package model;

import jakarta.persistence.*;
import java.time.Duration;

@Entity
@Table(name = "gare_trajet")
public class GareTrajet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "gare_id", nullable = false)
    private Gare gare;
    
    @ManyToOne
    @JoinColumn(name = "trajet_id", nullable = false)
    private Trajet trajet;
    
    @Column(nullable = false)
    private int ordre;
    
    @Column
    private Duration tempsArret;
    
    @Column
    private Duration tempsParcours;  // Temps pour arriver à cette gare depuis la précédente
    
    // Constructeurs
    public GareTrajet() {
    }
    
    public GareTrajet(Gare gare, Trajet trajet, int ordre, Duration tempsArret, Duration tempsParcours) {
        this.gare = gare;
        this.trajet = trajet;
        this.ordre = ordre;
        this.tempsArret = tempsArret;
        this.tempsParcours = tempsParcours;
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Gare getGare() {
        return gare;
    }

    public void setGare(Gare gare) {
        this.gare = gare;
    }

    public Trajet getTrajet() {
        return trajet;
    }

    public void setTrajet(Trajet trajet) {
        this.trajet = trajet;
    }

    public int getOrdre() {
        return ordre;
    }

    public void setOrdre(int ordre) {
        this.ordre = ordre;
    }

    public Duration getTempsArret() {
        return tempsArret;
    }

    public void setTempsArret(Duration tempsArret) {
        this.tempsArret = tempsArret;
    }

    public Duration getTempsParcours() {
        return tempsParcours;
    }

    public void setTempsParcours(Duration tempsParcours) {
        this.tempsParcours = tempsParcours;
    }
}
