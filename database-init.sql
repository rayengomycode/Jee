-- Script d'initialisation de la base de données pour le système de réservation de billets de train
-- Exécuter ce script après le premier démarrage de l'application

-- Base de données
CREATE DATABASE IF NOT EXISTS traindb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE traindb;

-- Note: Les tables seront créées automatiquement par Hibernate
-- Ce script contient uniquement des données d'exemple supplémentaires

-- Insertion de données d'exemple supplémentaires après l'initialisation automatique

-- Vérifier et insérer des gares supplémentaires si nécessaire
INSERT IGNORE INTO gares (nom, ville, adresse) VALUES
('Gare de Kairouan', 'Kairouan', 'Avenue de la République, Kairouan'),
('Gare de Tozeur', 'Tozeur', 'Avenue Abou El Kacem Chebbi, Tozeur'),
('Gare de Gafsa', 'Gafsa', 'Avenue Habib Bourguiba, Gafsa'),
('Gare de Kasserine', 'Kasserine', 'Avenue de l\'Indépendance, Kasserine'),
('Gare de Jendouba', 'Jendouba', 'Avenue Bourguiba, Jendouba'),
('Gare de Béja', 'Béja', 'Avenue Habib Bourguiba, Béja'),
('Gare de Kef', 'Kef', 'Avenue de la Liberté, Kef'),
('Gare de Siliana', 'Siliana', 'Avenue 20 Mars, Siliana');

-- Insertion de trains supplémentaires
INSERT IGNORE INTO trains (numero, nom, capacite_total) VALUES
('TN006', 'Express Sahel', 180),
('TN007', 'Train du Centre', 140),
('TN008', 'Rapide Ouest', 160),
('TN009', 'Express Intérieur', 120),
('TN010', 'Train Touristique', 100);

-- Insertion de trajets supplémentaires avec leurs gares
-- Ces insertions seront gérées par l'application via DataInitializer

-- Quelques conseils pour l'utilisation :
-- 1. Démarrer l'application une première fois pour créer les tables
-- 2. L'application initialisera automatiquement les données de base
-- 3. Utiliser ce script pour ajouter des données supplémentaires si nécessaire

-- Comptes utilisateurs par défaut créés automatiquement :
-- Admin: username=admin, password=admin123
-- Les utilisateurs peuvent s'inscrire via l'interface web

-- Pour tester l'application :
-- 1. Se connecter en tant qu'admin pour gérer les données
-- 2. Créer des voyages via l'interface d'administration
-- 3. Tester la recherche et la réservation en tant qu'utilisateur normal
