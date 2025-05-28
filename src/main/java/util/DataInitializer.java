package util;

import dao.*;
import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Classe pour initialiser les données de base de l'application
 */
public class DataInitializer {

    private SessionFactory sessionFactory;
    private RoleDAO roleDAO;
    private UserDAO userDAO;
    private ClasseDAO classeDAO;
    private GareDAO gareDAO;
    private TrajetDAO trajetDAO;
    private TrainDAO trainDAO;
    private VoyageDAO voyageDAO;

    public DataInitializer() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
        this.roleDAO = new RoleDAO();
        this.userDAO = new UserDAO();
        this.classeDAO = new ClasseDAO();
        this.gareDAO = new GareDAO();
        this.trajetDAO = new TrajetDAO();
        this.trainDAO = new TrainDAO();
        this.voyageDAO = new VoyageDAO();
    }

    public void initializeData() {
        initializeRoles();
        initializeClasses();
        initializeGares();
        initializeTrains();
        initializeTrajets();
        initializeAdminUser();
        initializeVoyages();
    }

    private void initializeRoles() {
        if (roleDAO.findByNom("ADMIN") == null) {
            Role adminRole = new Role("ADMIN");
            roleDAO.create(adminRole);
        }

        if (roleDAO.findByNom("USER") == null) {
            Role userRole = new Role("USER");
            roleDAO.create(userRole);
        }
    }

    private void initializeClasses() {
        if (classeDAO.findByNom("Économique") == null) {
            Classe economique = new Classe("Économique", 1.0);
            classeDAO.create(economique);
        }

        if (classeDAO.findByNom("Première") == null) {
            Classe premiere = new Classe("Première", 1.5);
            classeDAO.create(premiere);
        }

        if (classeDAO.findByNom("Business") == null) {
            Classe business = new Classe("Business", 2.0);
            classeDAO.create(business);
        }
    }

    private void initializeGares() {
        String[][] garesData = {
            {"Gare Centrale", "Tunis", "Avenue Habib Bourguiba, Tunis"},
            {"Gare de Sousse", "Sousse", "Avenue Léopold Sédar Senghor, Sousse"},
            {"Gare de Sfax", "Sfax", "Avenue Hedi Chaker, Sfax"},
            {"Gare de Monastir", "Monastir", "Route de l'Aéroport, Monastir"},
            {"Gare de Mahdia", "Mahdia", "Avenue 7 Novembre, Mahdia"},
            {"Gare de Gabès", "Gabès", "Avenue Farhat Hached, Gabès"},
            {"Gare de Bizerte", "Bizerte", "Avenue Habib Bourguiba, Bizerte"},
            {"Gare de Nabeul", "Nabeul", "Avenue Taieb Mhiri, Nabeul"}
        };

        for (String[] gareData : garesData) {
            if (gareDAO.findByNomAndVille(gareData[0], gareData[1]) == null) {
                Gare gare = new Gare(gareData[0], gareData[1], gareData[2]);
                gareDAO.create(gare);
            }
        }
    }

    private void initializeTrains() {
        String[][] trainsData = {
            {"TN001", "Express Tunis-Sousse", "200"},
            {"TN002", "Rapide Côtier", "180"},
            {"TN003", "Train du Sud", "150"},
            {"TN004", "Express Nord", "160"},
            {"TN005", "Train Régional", "120"}
        };

        for (String[] trainData : trainsData) {
            if (trainDAO.findByNumero(trainData[0]) == null) {
                Train train = new Train(trainData[0], trainData[1], Integer.parseInt(trainData[2]));
                trainDAO.create(train);
            }
        }
    }

    private void initializeTrajets() {
        // Trajet Tunis-Sousse (direct)
        createTrajetIfNotExists("TUN-SOU-001", true, new String[][]{
            {"Gare Centrale", "Tunis", "1"},
            {"Gare de Sousse", "Sousse", "2"}
        });

        // Trajet Tunis-Sfax (avec arrêts)
        createTrajetIfNotExists("TUN-SFX-001", false, new String[][]{
            {"Gare Centrale", "Tunis", "1"},
            {"Gare de Sousse", "Sousse", "2"},
            {"Gare de Monastir", "Monastir", "3"},
            {"Gare de Sfax", "Sfax", "4"}
        });

        // Trajet Sousse-Mahdia
        createTrajetIfNotExists("SOU-MAH-001", true, new String[][]{
            {"Gare de Sousse", "Sousse", "1"},
            {"Gare de Mahdia", "Mahdia", "2"}
        });
    }

    private void createTrajetIfNotExists(String code, boolean direct, String[][] garesData) {
        if (trajetDAO.findByCode(code) == null) {
            Session session = sessionFactory.openSession();
            Transaction tx = null;

            try {
                tx = session.beginTransaction();

                Trajet trajet = new Trajet(code, direct);
                session.persist(trajet);

                for (String[] gareData : garesData) {
                    Gare gare = gareDAO.findByNomAndVille(gareData[0], gareData[1]);
                    if (gare != null) {
                        GareTrajet gareTrajet = new GareTrajet(
                            gare,
                            trajet,
                            Integer.parseInt(gareData[2]),
                            Duration.ofMinutes(5), // Temps d'arrêt par défaut
                            Duration.ofMinutes(30) // Temps de parcours par défaut
                        );
                        session.persist(gareTrajet);
                    }
                }

                tx.commit();
            } catch (Exception e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
        }
    }

    private void initializeAdminUser() {
        User existingAdmin = userDAO.findByUsername("admin");
        Role adminRole = roleDAO.findByNom("ADMIN");

        if (existingAdmin == null && adminRole != null) {
            // Créer un nouvel utilisateur admin
            User admin = new User("admin", PasswordUtil.hashPassword("admin123"),
                                "Administrateur", "Système", "admin@trainticket.com");
            admin.setRole(adminRole);
            admin.setActif(true);
            userDAO.create(admin);
            System.out.println("Utilisateur admin créé avec mot de passe haché");
        } else if (existingAdmin != null) {
            // Vérifier si le mot de passe est correctement haché
            try {
                PasswordUtil.checkPassword("admin123", existingAdmin.getPassword());
                System.out.println("Utilisateur admin existant avec mot de passe correct");
            } catch (Exception e) {
                // Le mot de passe n'est pas correctement haché, le corriger
                existingAdmin.setPassword(PasswordUtil.hashPassword("admin123"));
                existingAdmin.setActif(true);
                userDAO.update(existingAdmin);
                System.out.println("Mot de passe admin corrigé avec hash BCrypt");
            }
        }
    }

    private void initializeVoyages() {
        // Ajouter quelques voyages d'exemple pour les prochains jours
        List<Trajet> trajets = trajetDAO.findAll();
        List<Train> trains = trainDAO.findAll();

        if (!trajets.isEmpty() && !trains.isEmpty()) {
            LocalDate today = LocalDate.now();

            for (int i = 0; i < 7; i++) { // Voyages pour les 7 prochains jours
                LocalDate date = today.plusDays(i);

                for (Trajet trajet : trajets) {
                    for (Train train : trains) {
                        // Créer 2-3 voyages par jour par trajet
                        createVoyageIfNotExists(trajet, train, date, LocalTime.of(8, 0), 50.0);
                        createVoyageIfNotExists(trajet, train, date, LocalTime.of(14, 0), 55.0);
                        createVoyageIfNotExists(trajet, train, date, LocalTime.of(20, 0), 60.0);
                    }
                }
            }
        }
    }

    private void createVoyageIfNotExists(Trajet trajet, Train train, LocalDate date, LocalTime heureDepart, double prix) {
        // Vérifier si le voyage existe déjà
        List<Voyage> existingVoyages = voyageDAO.findAll();
        boolean exists = existingVoyages.stream()
            .anyMatch(v -> v.getTrajet().getId().equals(trajet.getId())
                        && v.getTrain().getId().equals(train.getId())
                        && v.getDate().equals(date)
                        && v.getHeureDepart().equals(heureDepart));

        if (!exists) {
            LocalTime heureArrivee = heureDepart.plusHours(2); // Durée par défaut de 2h
            Voyage voyage = new Voyage(trajet, train, date, heureDepart, heureArrivee, prix, train.getCapaciteTotal());
            voyageDAO.create(voyage);
        }
    }
}
