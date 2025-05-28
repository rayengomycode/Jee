<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TrainTicket - Réservation de billets de train en ligne</title>
    <meta name="description" content="Réservez vos billets de train en ligne facilement et rapidement. Voyagez à travers la Tunisie avec TrainTicket.">

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
<body>
    <jsp:include page="/WEB-INF/views/header.jsp" />

    <!-- Hero Section -->
    <section class="hero-section">
        <div class="container">
            <div class="hero-content fade-in-up">
                <h1 class="hero-title">Voyagez en toute simplicité</h1>
                <p class="hero-subtitle">
                    Réservez vos billets de train en ligne et découvrez la Tunisie
                    avec confort et sécurité
                </p>
                <div class="hero-actions">
                    <a href="${pageContext.request.contextPath}/recherche" class="btn btn-primary btn-lg">
                        <i class="fas fa-search"></i>
                        <span>Rechercher un voyage</span>
                    </a>
                    <a href="#features" class="btn btn-outline-primary btn-lg">
                        <i class="fas fa-info-circle"></i>
                        <span>En savoir plus</span>
                    </a>
                </div>
            </div>
        </div>
    </section>

    <!-- Search Form Section -->
    <section class="search-section">
        <div class="container">
            <div class="search-form fade-in-up">
                <h2 class="text-center mb-6">Recherche rapide</h2>
                <form action="${pageContext.request.contextPath}/recherche" method="post" data-validate>
                    <div class="search-form .form-row">
                        <div class="search-form .form-col">
                            <div class="form-group">
                                <label for="villeDepart" class="form-label">
                                    <i class="fas fa-map-marker-alt"></i>
                                    Ville de départ
                                </label>
                                <input type="text"
                                       class="form-control"
                                       id="villeDepart"
                                       name="villeDepart"
                                       placeholder="Ex: Tunis"
                                       data-autocomplete="city"
                                       required>
                            </div>
                        </div>

                        <div class="search-form .form-col">
                            <div class="form-group">
                                <button type="button" id="swapCities" class="swap-button" data-tooltip="Échanger les villes">
                                    <i class="fas fa-exchange-alt"></i>
                                </button>
                            </div>
                        </div>

                        <div class="search-form .form-col">
                            <div class="form-group">
                                <label for="villeArrivee" class="form-label">
                                    <i class="fas fa-map-marker-alt"></i>
                                    Ville d'arrivée
                                </label>
                                <input type="text"
                                       class="form-control"
                                       id="villeArrivee"
                                       name="villeArrivee"
                                       placeholder="Ex: Sfax"
                                       data-autocomplete="city"
                                       required>
                            </div>
                        </div>

                        <div class="search-form .form-col">
                            <div class="form-group">
                                <label for="date" class="form-label">
                                    <i class="fas fa-calendar-alt"></i>
                                    Date du voyage
                                </label>
                                <input type="date"
                                       class="form-control"
                                       id="date"
                                       name="date"
                                       required>
                            </div>
                        </div>
                    </div>

                    <div class="text-center">
                        <button type="submit" class="btn btn-primary btn-lg">
                            <i class="fas fa-search"></i>
                            <span>Rechercher des trains</span>
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </section>

    <!-- Features Section - Enhanced -->
    <section id="features" class="features-section-enhanced">
        <div class="features-background">
            <div class="floating-elements">
                <div class="floating-element element-1"></div>
                <div class="floating-element element-2"></div>
                <div class="floating-element element-3"></div>
                <div class="floating-element element-4"></div>
            </div>
        </div>

        <div class="container">
            <div class="features-header text-center mb-16">
                <div class="section-badge fade-in-up">
                    <i class="fas fa-star"></i>
                    <span>Pourquoi nous choisir</span>
                </div>
                <h2 class="section-title-enhanced fade-in-up">
                    Pourquoi choisir <span class="gradient-text">TrainTicket</span> ?
                </h2>
                <p class="section-subtitle-enhanced fade-in-up">
                    Une expérience de voyage <strong>simplifiée et moderne</strong> qui révolutionne
                    votre façon de voyager en train à travers la Tunisie
                </p>
                <div class="title-decoration fade-in-up">
                    <div class="decoration-line"></div>
                    <div class="decoration-dot"></div>
                    <div class="decoration-line"></div>
                </div>
            </div>

            <!-- Main Features Grid -->
            <div class="features-grid-enhanced">
                <!-- Feature 1 - Recherche Intelligente -->
                <div class="feature-card-enhanced fade-in-up" data-delay="0.1s">
                    <div class="feature-card-inner">
                        <div class="feature-icon-enhanced">
                            <div class="icon-background"></div>
                            <i class="fas fa-brain"></i>
                            <div class="icon-pulse"></div>
                        </div>
                        <div class="feature-content-enhanced">
                            <h3 class="feature-title-enhanced">Recherche Intelligente</h3>
                            <p class="feature-description-enhanced">
                                Notre <strong>IA avancée</strong> analyse en temps réel tous les trajets disponibles
                                pour vous proposer les meilleures options selon vos préférences.
                            </p>
                            <div class="feature-benefits">
                                <div class="benefit-item">
                                    <i class="fas fa-check"></i>
                                    <span>Filtres personnalisés</span>
                                </div>
                                <div class="benefit-item">
                                    <i class="fas fa-check"></i>
                                    <span>Suggestions intelligentes</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Feature 2 - Sécurité Maximale -->
                <div class="feature-card-enhanced fade-in-up" data-delay="0.2s">
                    <div class="feature-card-inner">
                        <div class="feature-icon-enhanced">
                            <div class="icon-background"></div>
                            <i class="fas fa-shield-alt"></i>
                            <div class="icon-pulse"></div>
                        </div>
                        <div class="feature-content-enhanced">
                            <h3 class="feature-title-enhanced">Sécurité Maximale</h3>
                            <p class="feature-description-enhanced">
                                Vos données et paiements sont protégés par un <strong>cryptage SSL 256-bit</strong>
                                et les dernières technologies de sécurité bancaire.
                            </p>
                            <div class="feature-benefits">
                                <div class="benefit-item">
                                    <i class="fas fa-check"></i>
                                    <span>Cryptage SSL 256-bit</span>
                                </div>
                                <div class="benefit-item">
                                    <i class="fas fa-check"></i>
                                    <span>Paiements sécurisés</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Feature 3 - Expérience Mobile -->
                <div class="feature-card-enhanced fade-in-up" data-delay="0.3s">
                    <div class="feature-card-inner">
                        <div class="feature-icon-enhanced">
                            <div class="icon-background"></div>
                            <i class="fas fa-mobile-alt"></i>
                            <div class="icon-pulse"></div>
                        </div>
                        <div class="feature-content-enhanced">
                            <h3 class="feature-title-enhanced">100% Mobile</h3>
                            <p class="feature-description-enhanced">
                                Interface <strong>responsive premium</strong> optimisée pour tous les appareils.
                                Vos billets toujours accessibles, même hors ligne.
                            </p>
                            <div class="feature-benefits">
                                <div class="benefit-item">
                                    <i class="fas fa-check"></i>
                                    <span>E-tickets instantanés</span>
                                </div>
                                <div class="benefit-item">
                                    <i class="fas fa-check"></i>
                                    <span>Mode hors ligne</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Feature 4 - Support Premium -->
                <div class="feature-card-enhanced fade-in-up" data-delay="0.4s">
                    <div class="feature-card-inner">
                        <div class="feature-icon-enhanced">
                            <div class="icon-background"></div>
                            <i class="fas fa-headset"></i>
                            <div class="icon-pulse"></div>
                        </div>
                        <div class="feature-content-enhanced">
                            <h3 class="feature-title-enhanced">Support Premium 24/7</h3>
                            <p class="feature-description-enhanced">
                                Équipe d'experts disponible <strong>24h/24 et 7j/7</strong> par chat,
                                téléphone ou email pour vous accompagner.
                            </p>
                            <div class="feature-benefits">
                                <div class="benefit-item">
                                    <i class="fas fa-check"></i>
                                    <span>Chat en temps réel</span>
                                </div>
                                <div class="benefit-item">
                                    <i class="fas fa-check"></i>
                                    <span>Support multilingue</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Feature 5 - Prix Garantis -->
                <div class="feature-card-enhanced fade-in-up" data-delay="0.5s">
                    <div class="feature-card-inner">
                        <div class="feature-icon-enhanced">
                            <div class="icon-background"></div>
                            <i class="fas fa-tags"></i>
                            <div class="icon-pulse"></div>
                        </div>
                        <div class="feature-content-enhanced">
                            <h3 class="feature-title-enhanced">Meilleurs Prix Garantis</h3>
                            <p class="feature-description-enhanced">
                                Algorithme de <strong>comparaison en temps réel</strong> qui vous garantit
                                les tarifs les plus avantageux du marché.
                            </p>
                            <div class="feature-benefits">
                                <div class="benefit-item">
                                    <i class="fas fa-check"></i>
                                    <span>Comparaison automatique</span>
                                </div>
                                <div class="benefit-item">
                                    <i class="fas fa-check"></i>
                                    <span>Alertes prix</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Feature 6 - Innovation -->
                <div class="feature-card-enhanced fade-in-up" data-delay="0.6s">
                    <div class="feature-card-inner">
                        <div class="feature-icon-enhanced">
                            <div class="icon-background"></div>
                            <i class="fas fa-rocket"></i>
                            <div class="icon-pulse"></div>
                        </div>
                        <div class="feature-content-enhanced">
                            <h3 class="feature-title-enhanced">Innovation Continue</h3>
                            <p class="feature-description-enhanced">
                                Plateforme en <strong>évolution constante</strong> avec de nouvelles
                                fonctionnalités ajoutées régulièrement pour améliorer votre expérience.
                            </p>
                            <div class="feature-benefits">
                                <div class="benefit-item">
                                    <i class="fas fa-check"></i>
                                    <span>Mises à jour régulières</span>
                                </div>
                                <div class="benefit-item">
                                    <i class="fas fa-check"></i>
                                    <span>Nouvelles fonctionnalités</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Trust Indicators -->
            <div class="trust-section fade-in-up">
                <div class="trust-header">
                    <h3>Ils nous font confiance</h3>
                    <p>Plus de 50,000 voyageurs satisfaits</p>
                </div>
                <div class="trust-indicators">
                    <div class="trust-item">
                        <div class="trust-icon">
                            <i class="fas fa-star"></i>
                        </div>
                        <div class="trust-content">
                            <div class="trust-number">4.9/5</div>
                            <div class="trust-label">Note moyenne</div>
                        </div>
                    </div>
                    <div class="trust-item">
                        <div class="trust-icon">
                            <i class="fas fa-users"></i>
                        </div>
                        <div class="trust-content">
                            <div class="trust-number">50K+</div>
                            <div class="trust-label">Utilisateurs actifs</div>
                        </div>
                    </div>
                    <div class="trust-item">
                        <div class="trust-icon">
                            <i class="fas fa-award"></i>
                        </div>
                        <div class="trust-content">
                            <div class="trust-number">99.9%</div>
                            <div class="trust-label">Disponibilité</div>
                        </div>
                    </div>
                    <div class="trust-item">
                        <div class="trust-icon">
                            <i class="fas fa-clock"></i>
                        </div>
                        <div class="trust-content">
                            <div class="trust-number">< 2min</div>
                            <div class="trust-label">Temps de réservation</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Statistics Section -->
    <section class="stats-section">
        <div class="container">
            <div class="row text-center">
                <div class="col-md-3">
                    <div class="stat-item fade-in-up">
                        <div class="stat-number">50+</div>
                        <div class="stat-label">Destinations</div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="stat-item fade-in-up">
                        <div class="stat-number">10K+</div>
                        <div class="stat-label">Clients satisfaits</div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="stat-item fade-in-up">
                        <div class="stat-number">99.9%</div>
                        <div class="stat-label">Disponibilité</div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="stat-item fade-in-up">
                        <div class="stat-number">24/7</div>
                        <div class="stat-label">Support</div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- CTA Section -->
    <section class="cta-section">
        <div class="container">
            <div class="cta-content text-center fade-in-up">
                <h2>Prêt à commencer votre voyage ?</h2>
                <p>Rejoignez des milliers de voyageurs qui font confiance à TrainTicket</p>
                <div class="cta-actions">
                    <c:choose>
                        <c:when test="${empty sessionScope.user}">
                            <a href="${pageContext.request.contextPath}/auth?page=register" class="btn btn-primary btn-lg">
                                <i class="fas fa-user-plus"></i>
                                <span>Créer un compte</span>
                            </a>
                            <a href="${pageContext.request.contextPath}/recherche" class="btn btn-outline-primary btn-lg">
                                <i class="fas fa-search"></i>
                                <span>Rechercher maintenant</span>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/recherche" class="btn btn-primary btn-lg">
                                <i class="fas fa-search"></i>
                                <span>Rechercher un voyage</span>
                            </a>
                            <a href="${pageContext.request.contextPath}/mes-billets" class="btn btn-outline-primary btn-lg">
                                <i class="fas fa-ticket-alt"></i>
                                <span>Mes billets</span>
                            </a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </section>

    <jsp:include page="/WEB-INF/views/footer.jsp" />

    <!-- Scripts -->
    <script src="${pageContext.request.contextPath}/assets/js/app.js"></script>

    <script>
        // Set minimum date to today
        document.addEventListener('DOMContentLoaded', function() {
            const dateInput = document.getElementById('date');
            if (dateInput) {
                const today = new Date().toISOString().split('T')[0];
                dateInput.min = today;
                dateInput.value = today;
            }
        });
    </script>
</body>
</html>