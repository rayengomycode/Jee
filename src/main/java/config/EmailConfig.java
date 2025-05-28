package config;

import java.util.Properties;

/**
 * Configuration pour l'envoi d'emails
 */
public class EmailConfig {

    // Configuration SMTP par défaut (Gmail)
    public static final String SMTP_HOST = "smtp.gmail.com";
    public static final String SMTP_PORT = "587";
    public static final boolean SMTP_AUTH = true;
    public static final boolean SMTP_STARTTLS = true;

    // Adresse email de l'application (à configurer)
    public static final String FROM_EMAIL = "nnutrimuscle@gmail.com";
    public static final String FROM_PASSWORD = "xmls vtue bfai cqqc";
    public static final String FROM_NAME = "Train Ticket System";

    // Configuration pour tests (utilise un serveur SMTP de test)
    public static final boolean TEST_MODE = false;
    public static final String TEST_SMTP_HOST = "smtp.mailtrap.io"; // Service de test
    public static final String TEST_SMTP_PORT = "2525";
    public static final String TEST_USERNAME = "votre_username_test";
    public static final String TEST_PASSWORD = "votre_password_test";

    /**
     * Retourne les propriétés SMTP configurées
     */
    public static Properties getSmtpProperties() {
        Properties props = new Properties();

        if (TEST_MODE) {
            // Configuration pour tests
            props.put("mail.smtp.host", TEST_SMTP_HOST);
            props.put("mail.smtp.port", TEST_SMTP_PORT);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
        } else {
            // Configuration production (Gmail)
            props.put("mail.smtp.host", SMTP_HOST);
            props.put("mail.smtp.port", SMTP_PORT);
            props.put("mail.smtp.auth", String.valueOf(SMTP_AUTH));
            props.put("mail.smtp.starttls.enable", String.valueOf(SMTP_STARTTLS));
        }

        // Propriétés communes
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.ssl.trust", "*");

        return props;
    }

    /**
     * Retourne le username SMTP
     */
    public static String getSmtpUsername() {
        return TEST_MODE ? TEST_USERNAME : FROM_EMAIL;
    }

    /**
     * Retourne le password SMTP
     */
    public static String getSmtpPassword() {
        return TEST_MODE ? TEST_PASSWORD : FROM_PASSWORD;
    }

    /**
     * Retourne l'adresse email d'envoi
     */
    public static String getFromEmail() {
        return TEST_MODE ? TEST_USERNAME : FROM_EMAIL;
    }

    /**
     * Retourne le nom d'envoi
     */
    public static String getFromName() {
        return FROM_NAME;
    }

    /**
     * Vérifie si la configuration email est valide
     */
    public static boolean isConfigured() {
        if (TEST_MODE) {
            return !TEST_USERNAME.equals("votre_username_test") &&
                   !TEST_PASSWORD.equals("votre_password_test");
        } else {
            return !FROM_EMAIL.equals("trainticket.system@gmail.com") &&
                   !FROM_PASSWORD.equals("votre_mot_de_passe_app");
        }
    }

    /**
     * Configuration pour différents environnements
     */
    public static class Environment {
        public static final String DEVELOPMENT = "dev";
        public static final String TESTING = "test";
        public static final String PRODUCTION = "prod";

        private static String currentEnvironment = DEVELOPMENT;

        public static void setEnvironment(String env) {
            currentEnvironment = env;
        }

        public static String getCurrentEnvironment() {
            return currentEnvironment;
        }

        public static boolean isDevelopment() {
            return DEVELOPMENT.equals(currentEnvironment);
        }

        public static boolean isTesting() {
            return TESTING.equals(currentEnvironment);
        }

        public static boolean isProduction() {
            return PRODUCTION.equals(currentEnvironment);
        }
    }

    /**
     * URLs de l'application pour les liens dans les emails
     */
    public static class AppUrls {
        public static final String BASE_URL = "http://localhost:9099/train-ticket-system";
        public static final String LOGIN_URL = BASE_URL + "/auth?page=login";
        public static final String BILLETS_URL = BASE_URL + "/mes-billets";
        public static final String CONTACT_URL = BASE_URL + "/contact";

        public static String getBilletUrl(Long billetId) {
            return BASE_URL + "/confirmation?billetId=" + billetId;
        }

        public static String getPdfUrl(Long billetId) {
            return BASE_URL + "/download-pdf?billetId=" + billetId;
        }
    }
}
