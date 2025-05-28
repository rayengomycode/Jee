<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inscription - TrainTicket</title>
    <meta name="description" content="Créez votre compte TrainTicket pour réserver vos billets de train en ligne.">

    <!-- Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">

    <!-- Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <!-- Styles -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/navbar.css">
</head>
<body class="auth-page">
    <jsp:include page="header.jsp" />

    <!-- Auth Section -->
    <section class="auth-section">
        <div class="auth-background">
            <div class="auth-shapes">
                <div class="shape shape-1"></div>
                <div class="shape shape-2"></div>
                <div class="shape shape-3"></div>
            </div>
        </div>

        <div class="container">
            <div class="row justify-content-center align-items-center min-vh-80">
                <div class="col-lg-5 col-md-7">
                    <div class="auth-card fade-in-up">
                        <!-- Header -->
                        <div class="auth-header">
                            <div class="auth-logo">
                                <i class="fas fa-user-plus"></i>
                            </div>
                            <h1 class="auth-title">Rejoignez-nous !</h1>
                            <p class="auth-subtitle">Créez votre compte TrainTicket en quelques clics</p>
                        </div>

                        <!-- Alerts -->
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger alert-dismissible">
                                <i class="fas fa-exclamation-circle"></i>
                                <span>${error}</span>
                                <button type="button" class="alert-close" onclick="this.parentElement.remove()">
                                    <i class="fas fa-times"></i>
                                </button>
                            </div>
                        </c:if>

                        <!-- Register Form -->
                        <form action="${pageContext.request.contextPath}/auth" method="post" class="auth-form" data-validate>
                            <input type="hidden" name="action" value="register">

                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label for="prenom" class="form-label">
                                        <i class="fas fa-user"></i>
                                        Prénom
                                    </label>
                                    <div class="input-group">
                                        <div class="input-icon">
                                            <i class="fas fa-user"></i>
                                        </div>
                                        <input type="text"
                                               class="form-control form-control-auth"
                                               id="prenom"
                                               name="prenom"
                                               value="${param.prenom}"
                                               placeholder="Votre prénom"
                                               required
                                               autocomplete="given-name">
                                    </div>
                                </div>

                                <div class="form-group col-md-6">
                                    <label for="nom" class="form-label">
                                        <i class="fas fa-user"></i>
                                        Nom
                                    </label>
                                    <div class="input-group">
                                        <div class="input-icon">
                                            <i class="fas fa-user"></i>
                                        </div>
                                        <input type="text"
                                               class="form-control form-control-auth"
                                               id="nom"
                                               name="nom"
                                               value="${param.nom}"
                                               placeholder="Votre nom"
                                               required
                                               autocomplete="family-name">
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="username" class="form-label">
                                    <i class="fas fa-at"></i>
                                    Nom d'utilisateur
                                </label>
                                <div class="input-group">
                                    <div class="input-icon">
                                        <i class="fas fa-at"></i>
                                    </div>
                                    <input type="text"
                                           class="form-control form-control-auth"
                                           id="username"
                                           name="username"
                                           value="${param.username}"
                                           placeholder="Choisissez un nom d'utilisateur"
                                           required
                                           autocomplete="username">
                                </div>
                                <small class="form-text text-muted">
                                    <i class="fas fa-info-circle"></i>
                                    Choisissez un nom d'utilisateur unique
                                </small>
                            </div>

                            <div class="form-group">
                                <label for="email" class="form-label">
                                    <i class="fas fa-envelope"></i>
                                    Email
                                </label>
                                <div class="input-group">
                                    <div class="input-icon">
                                        <i class="fas fa-envelope"></i>
                                    </div>
                                    <input type="email"
                                           class="form-control form-control-auth"
                                           id="email"
                                           name="email"
                                           value="${param.email}"
                                           placeholder="votre.email@exemple.com"
                                           required
                                           autocomplete="email">
                                </div>
                                <small class="form-text text-muted">
                                    <i class="fas fa-shield-alt"></i>
                                    Utilisé pour la récupération de mot de passe
                                </small>
                            </div>

                            <div class="form-group">
                                <label for="password" class="form-label">
                                    <i class="fas fa-lock"></i>
                                    Mot de passe
                                </label>
                                <div class="input-group">
                                    <div class="input-icon">
                                        <i class="fas fa-lock"></i>
                                    </div>
                                    <input type="password"
                                           class="form-control form-control-auth"
                                           id="password"
                                           name="password"
                                           placeholder="Créez un mot de passe sécurisé"
                                           required
                                           autocomplete="new-password"
                                           minlength="6">
                                    <button type="button" class="password-toggle" onclick="togglePassword('password')">
                                        <i class="fas fa-eye" id="password-eye"></i>
                                    </button>
                                </div>
                                <small class="form-text text-muted">
                                    <i class="fas fa-key"></i>
                                    Minimum 6 caractères
                                </small>
                            </div>

                            <div class="form-options">
                                <div class="form-check">
                                    <input type="checkbox" class="form-check-input" id="terms" name="terms" required>
                                    <label for="terms" class="form-check-label">
                                        J'accepte les <a href="#" class="auth-link">conditions d'utilisation</a> et la <a href="#" class="auth-link">politique de confidentialité</a>
                                    </label>
                                </div>
                            </div>

                            <button type="submit" class="btn btn-primary btn-auth">
                                <i class="fas fa-user-plus"></i>
                                <span>Créer mon compte</span>
                                <div class="btn-shine"></div>
                            </button>
                        </form>

                        <!-- Divider -->
                        <div class="auth-divider">
                            <span>ou</span>
                        </div>

                        <!-- Social Login -->
                        <div class="social-login">
                            <button type="button" class="btn btn-social btn-google">
                                <i class="fab fa-google"></i>
                                <span>S'inscrire avec Google</span>
                            </button>
                            <button type="button" class="btn btn-social btn-facebook">
                                <i class="fab fa-facebook-f"></i>
                                <span>S'inscrire avec Facebook</span>
                            </button>
                        </div>

                        <!-- Footer -->
                        <div class="auth-footer">
                            <p>Vous avez déjà un compte ?</p>
                            <a href="${pageContext.request.contextPath}/auth?page=login" class="auth-link">
                                Se connecter
                                <i class="fas fa-arrow-right"></i>
                            </a>
                        </div>
                    </div>
                </div>

                <!-- Side Content -->
                <div class="col-lg-6 d-none d-lg-block">
                    <div class="auth-side-content slide-in-right">
                        <div class="side-illustration">
                            <i class="fas fa-ticket-alt"></i>
                        </div>
                        <h2>Commencez votre voyage</h2>
                        <p>Rejoignez des milliers de voyageurs qui font confiance à TrainTicket pour leurs déplacements en train.</p>

                        <div class="features-list">
                            <div class="feature-item">
                                <i class="fas fa-check-circle"></i>
                                <span>Réservation rapide et sécurisée</span>
                            </div>
                            <div class="feature-item">
                                <i class="fas fa-check-circle"></i>
                                <span>Billets électroniques instantanés</span>
                            </div>
                            <div class="feature-item">
                                <i class="fas fa-check-circle"></i>
                                <span>Support client 24h/24</span>
                            </div>
                            <div class="feature-item">
                                <i class="fas fa-check-circle"></i>
                                <span>Offres exclusives membres</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <jsp:include page="footer.jsp" />

    <!-- Scripts -->
    <script src="${pageContext.request.contextPath}/assets/js/app.js"></script>

    <script>
        // Toggle password visibility
        function togglePassword(inputId) {
            const input = document.getElementById(inputId);
            const eye = document.getElementById(inputId + '-eye');

            if (input.type === 'password') {
                input.type = 'text';
                eye.classList.remove('fa-eye');
                eye.classList.add('fa-eye-slash');
            } else {
                input.type = 'password';
                eye.classList.remove('fa-eye-slash');
                eye.classList.add('fa-eye');
            }
        }

        // Form validation
        document.addEventListener('DOMContentLoaded', function() {
            const form = document.querySelector('.auth-form');
            const inputs = form.querySelectorAll('input[required]');

            inputs.forEach(input => {
                input.addEventListener('blur', function() {
                    if (this.value.trim() === '') {
                        this.classList.add('error');
                    } else {
                        this.classList.remove('error');
                    }
                });
            });

            // Password strength indicator
            const passwordInput = document.getElementById('password');
            passwordInput.addEventListener('input', function() {
                const password = this.value;
                const strength = getPasswordStrength(password);

                // Remove existing strength classes
                this.classList.remove('weak', 'medium', 'strong');

                if (password.length > 0) {
                    this.classList.add(strength);
                }
            });
        });

        function getPasswordStrength(password) {
            let score = 0;

            if (password.length >= 6) score++;
            if (password.length >= 8) score++;
            if (/[a-z]/.test(password)) score++;
            if (/[A-Z]/.test(password)) score++;
            if (/[0-9]/.test(password)) score++;
            if (/[^A-Za-z0-9]/.test(password)) score++;

            if (score < 3) return 'weak';
            if (score < 5) return 'medium';
            return 'strong';
        }
    </script>
</body>
</html>
