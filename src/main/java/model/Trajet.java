package model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "trajets")
public class Trajet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private boolean direct;

    @OneToMany(mappedBy = "trajet", cascade = CascadeType.ALL)
    private Set<GareTrajet> gareTrajets = new HashSet<>();

    @OneToMany(mappedBy = "trajet")
    private Set<Voyage> voyages = new HashSet<>();

    // Constructeurs
    public Trajet() {
    }

    public Trajet(String code, boolean direct) {
        this.code = code;
        this.direct = direct;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isDirect() {
        return direct;
    }

    public void setDirect(boolean direct) {
        this.direct = direct;
    }

    public Set<GareTrajet> getGareTrajets() {
        return gareTrajets;
    }

    public void setGareTrajets(Set<GareTrajet> gareTrajets) {
        this.gareTrajets = gareTrajets;
    }

    public Set<Voyage> getVoyages() {
        return voyages;
    }

    public void setVoyages(Set<Voyage> voyages) {
        this.voyages = voyages;
    }

    // Méthodes utilitaires
    public Gare getGareDepart() {
        try {
            if (gareTrajets == null || gareTrajets.isEmpty()) {
                return null;
            }
            return gareTrajets.stream()
                    .filter(gt -> gt.getOrdre() == 1)
                    .findFirst()
                    .map(GareTrajet::getGare)
                    .orElse(null);
        } catch (org.hibernate.LazyInitializationException e) {
            // Si les gareTrajets ne sont pas chargés, retourner null
            return null;
        }
    }

    public Gare getGareArrivee() {
        try {
            if (gareTrajets == null || gareTrajets.isEmpty()) {
                return null;
            }
            int maxOrdre = gareTrajets.stream()
                    .mapToInt(GareTrajet::getOrdre)
                    .max()
                    .orElse(0);

            return gareTrajets.stream()
                    .filter(gt -> gt.getOrdre() == maxOrdre)
                    .findFirst()
                    .map(GareTrajet::getGare)
                    .orElse(null);
        } catch (org.hibernate.LazyInitializationException e) {
            // Si les gareTrajets ne sont pas chargés, retourner null
            return null;
        }
    }

    public java.util.List<Gare> getGaresIntermediaires() {
        try {
            if (gareTrajets == null || gareTrajets.isEmpty()) {
                return new java.util.ArrayList<>();
            }
            return gareTrajets.stream()
                    .filter(gt -> gt.getOrdre() > 1)
                    .sorted((gt1, gt2) -> Integer.compare(gt1.getOrdre(), gt2.getOrdre()))
                    .map(GareTrajet::getGare)
                    .collect(java.util.stream.Collectors.toList());
        } catch (org.hibernate.LazyInitializationException e) {
            return new java.util.ArrayList<>();
        }
    }

    public boolean contientGare(Gare gare) {
        try {
            if (gareTrajets == null || gareTrajets.isEmpty() || gare == null) {
                return false;
            }
            return gareTrajets.stream()
                    .anyMatch(gt -> gt.getGare().getId().equals(gare.getId()));
        } catch (org.hibernate.LazyInitializationException e) {
            return false;
        }
    }

    public java.util.List<GareTrajet> getGareTrajetsOrdonnes() {
        try {
            if (gareTrajets == null || gareTrajets.isEmpty()) {
                return new java.util.ArrayList<>();
            }
            return gareTrajets.stream()
                    .sorted((gt1, gt2) -> Integer.compare(gt1.getOrdre(), gt2.getOrdre()))
                    .collect(java.util.stream.Collectors.toList());
        } catch (org.hibernate.LazyInitializationException e) {
            return new java.util.ArrayList<>();
        }
    }
}
