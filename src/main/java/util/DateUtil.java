package util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Utilitaires pour le formatage des dates Java 8+ dans les JSP
 */
public class DateUtil {
    
    // Formatters prédéfinis
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    /**
     * Formate une LocalDate en string dd/MM/yyyy
     */
    public static String formatDate(LocalDate date) {
        if (date == null) {
            return "Non définie";
        }
        return date.format(DATE_FORMATTER);
    }
    
    /**
     * Formate une LocalTime en string HH:mm
     */
    public static String formatTime(LocalTime time) {
        if (time == null) {
            return "Non définie";
        }
        return time.format(TIME_FORMATTER);
    }
    
    /**
     * Formate une LocalDateTime en string dd/MM/yyyy HH:mm
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "Non définie";
        }
        return dateTime.format(DATETIME_FORMATTER);
    }
    
    /**
     * Formate une LocalDate avec un pattern personnalisé
     */
    public static String formatDate(LocalDate date, String pattern) {
        if (date == null) {
            return "Non définie";
        }
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }
    
    /**
     * Formate une LocalTime avec un pattern personnalisé
     */
    public static String formatTime(LocalTime time, String pattern) {
        if (time == null) {
            return "Non définie";
        }
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }
    
    /**
     * Formate une LocalDateTime avec un pattern personnalisé
     */
    public static String formatDateTime(LocalDateTime dateTime, String pattern) {
        if (dateTime == null) {
            return "Non définie";
        }
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }
    
    /**
     * Retourne la date actuelle formatée
     */
    public static String getCurrentDate() {
        return formatDate(LocalDate.now());
    }
    
    /**
     * Retourne l'heure actuelle formatée
     */
    public static String getCurrentTime() {
        return formatTime(LocalTime.now());
    }
    
    /**
     * Retourne la date et heure actuelles formatées
     */
    public static String getCurrentDateTime() {
        return formatDateTime(LocalDateTime.now());
    }
}
