package util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utilitaire pour le hachage et la vérification des mots de passe
 */
public class PasswordUtil {
    
    private static final int ROUNDS = 12;
    
    /**
     * Hache un mot de passe en utilisant BCrypt
     * @param plainPassword le mot de passe en clair
     * @return le mot de passe haché
     */
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(ROUNDS));
    }
    
    /**
     * Vérifie si un mot de passe en clair correspond au hash
     * @param plainPassword le mot de passe en clair
     * @param hashedPassword le mot de passe haché
     * @return true si les mots de passe correspondent
     */
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
