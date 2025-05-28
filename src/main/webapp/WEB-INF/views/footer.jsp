<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<footer class="footer">
    <div class="container">
        <div class="footer-content">
            <div class="footer-section">
                <div class="footer-brand">
                    <i class="fas fa-train"></i>
                    <span>TrainTicket</span>
                </div>
                <p class="footer-description">
                    Votre plateforme de réservation de billets de train en ligne.
                    Voyagez facilement et en toute sécurité à travers la Tunisie.
                </p>
                <div class="footer-social">
                    <a href="#" class="social-link" data-tooltip="Facebook">
                        <i class="fab fa-facebook-f"></i>
                    </a>
                    <a href="#" class="social-link" data-tooltip="Twitter">
                        <i class="fab fa-twitter"></i>
                    </a>
                    <a href="#" class="social-link" data-tooltip="Instagram">
                        <i class="fab fa-instagram"></i>
                    </a>
                    <a href="#" class="social-link" data-tooltip="LinkedIn">
                        <i class="fab fa-linkedin-in"></i>
                    </a>
                </div>
            </div>

            <div class="footer-section">
                <h5>Services</h5>
                <ul class="footer-links">
                    <li><a href="${pageContext.request.contextPath}/recherche">Rechercher un voyage</a></li>
                    <li><a href="${pageContext.request.contextPath}/horaires">Horaires des trains</a></li>
                    <li><a href="${pageContext.request.contextPath}/tarifs">Tarifs et promotions</a></li>
                    <li><a href="${pageContext.request.contextPath}/gares">Nos gares</a></li>
                </ul>
            </div>

            <div class="footer-section">
                <h5>Aide & Support</h5>
                <ul class="footer-links">
                    <li><a href="${pageContext.request.contextPath}/faq">Questions fréquentes</a></li>
                    <li><a href="${pageContext.request.contextPath}/contact">Nous contacter</a></li>
                    <li><a href="${pageContext.request.contextPath}/conditions">Conditions d'utilisation</a></li>
                    <li><a href="${pageContext.request.contextPath}/confidentialite">Politique de confidentialité</a></li>
                </ul>
            </div>

            <div class="footer-section">
                <h5>Contact</h5>
                <div class="contact-info">
                    <div class="contact-item">
                        <i class="fas fa-map-marker-alt"></i>
                        <span>Avenue Habib Bourguiba, Tunis 1000</span>
                    </div>
                    <div class="contact-item">
                        <i class="fas fa-phone"></i>
                        <span>+216 71 123 456</span>
                    </div>
                    <div class="contact-item">
                        <i class="fas fa-envelope"></i>
                        <span>contact@trainticket.tn</span>
                    </div>
                    <div class="contact-item">
                        <i class="fas fa-clock"></i>
                        <span>24h/24 - 7j/7</span>
                    </div>
                </div>
            </div>
        </div>

        <div class="footer-bottom">
            <div class="footer-bottom-content">
                <p>&copy; 2024 TrainTicket. Tous droits réservés.</p>
                <div class="footer-bottom-links">
                    <a href="${pageContext.request.contextPath}/mentions-legales">Mentions légales</a>
                    <a href="${pageContext.request.contextPath}/cookies">Cookies</a>
                    <a href="${pageContext.request.contextPath}/accessibilite">Accessibilité</a>
                </div>
            </div>
        </div>
    </div>
</footer>
