package controller;

import util.DataInitializer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

/**
 * Servlet d'initialisation qui charge les données de base au démarrage de l'application
 */
public class InitServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void init() throws ServletException {
        super.init();

        try {
            System.out.println("Initialisation des données de base...");
            DataInitializer initializer = new DataInitializer();
            initializer.initializeData();
            System.out.println("Données de base initialisées avec succès.");
        } catch (Exception e) {
            System.err.println("Erreur lors de l'initialisation des données : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
