<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Connexion - TrainTicket</title>
    <meta name="description" content="Connectez-vous à votre compte TrainTicket pour gérer vos réservations de train.">

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
                                <i class="fas fa-train"></i>
                            </div>
                            <h1 class="auth-title">Bon retour !</h1>
                            <p class="auth-subtitle">Connectez-vous à votre compte TrainTicket</p>
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

                        <!-- Login Form -->
                        <form action="${pageContext.request.contextPath}/auth" method="post" class="auth-form" data-validate>
                            <input type="hidden" name="action" value="login">

                            <div class="form-group">
                                <label for="username" class="form-label">
                                    <i class="fas fa-user"></i>
                                    Nom d'utilisateur
                                </label>
                                <div class="input-group">
                                    <div class="input-icon">
                                        <i class="fas fa-user"></i>
                                    </div>
                                    <input type="text"
                                           class="form-control form-control-auth"
                                           id="username"
                                           name="username"
                                           placeholder="Entrez votre nom d'utilisateur"
                                           required
                                           autocomplete="username">
                                </div>
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
                                           placeholder="Entrez votre mot de passe"
                                           required
                                           autocomplete="current-password">
                                    <button type="button" class="password-toggle" onclick="togglePassword('password')">
                                        <i class="fas fa-eye" id="password-eye"></i>
                                    </button>
                                </div>
                            </div>

                            <div class="form-options">
                                <div class="form-check">
                                    <input type="checkbox" class="form-check-input" id="remember" name="remember">
                                    <label for="remember" class="form-check-label">
                                        Se souvenir de moi
                                    </label>
                                </div>
                                <a href="#" class="forgot-password">Mot de passe oublié ?</a>
                            </div>

                            <button type="submit" class="btn btn-primary btn-auth">
                                <i class="fas fa-sign-in-alt"></i>
                                <span>Se connecter</span>
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
                                <span>Continuer avec Google</span>
                            </button>
                            <button type="button" class="btn btn-social btn-facebook">
                                <i class="fab fa-facebook-f"></i>
                                <span>Continuer avec Facebook</span>
                            </button>
                        </div>

                        <!-- Footer -->
                        <div class="auth-footer">
                            <p>Vous n'avez pas de compte ?</p>
                            <a href="${pageContext.request.contextPath}/auth?page=register" class="auth-link">
                                Créer un compte
                                <i class="fas fa-arrow-right"></i>
                            </a>
                        </div>
                    </div>
                </div>

                <!-- Side Content -->
                <div class="col-lg-6 d-none d-lg-block">
                    <div class="auth-side-content slide-in-right">
                        <div class="side-illustration">
                            <i class="fas fa-train"></i>
                        </div>
                        <h2>Voyagez en toute sérénité</h2>
                        <p>Accédez à votre espace personnel pour gérer vos réservations, consulter vos billets et découvrir nos offres exclusives.</p>

                        <div class="features-list">
                            <div class="feature-item">
                                <i class="fas fa-check-circle"></i>
                                <span>Gestion simplifiée de vos billets</span>
                            </div>
                            <div class="feature-item">
                                <i class="fas fa-check-circle"></i>
                                <span>Notifications en temps réel</span>
                            </div>
                            <div class="feature-item">
                                <i class="fas fa-check-circle"></i>
                                <span>Offres personnalisées</span>
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
        });
    </script>
</body>
</html>
