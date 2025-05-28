<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Recherche de Trains - TrainTicket</title>
    <meta name="description" content="Recherchez et réservez vos billets de train en Tunisie. Interface moderne et intuitive.">

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
    <jsp:include page="header.jsp" />

    <!-- Page Header -->
    <section class="page-header">
        <div class="container">
            <div class="page-header-content">
                <nav class="breadcrumb-nav">
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item">
                            <a href="${pageContext.request.contextPath}/">
                                <i class="fas fa-home"></i> Accueil
                            </a>
                        </li>
                        <li class="breadcrumb-item active">
                            <i class="fas fa-search"></i> Recherche
                        </li>
                    </ol>
                </nav>
                <h1 class="page-title">Recherche de Trains</h1>
                <p class="page-subtitle">
                    Trouvez le voyage parfait parmi nos destinations en Tunisie
                </p>
            </div>
        </div>
    </section>

    <!-- Main Content -->
    <main class="main-content">
        <div class="container">
            <!-- Search Form -->
            <div class="row justify-content-center">
                <div class="col-lg-8">
                    <div class="search-card fade-in-up">
                        <div class="search-card-header">
                            <div class="search-icon">
                                <i class="fas fa-search"></i>
                            </div>
                            <h2>Planifiez votre voyage</h2>
                            <p>Sélectionnez vos préférences de voyage</p>
                        </div>

                        <form action="${pageContext.request.contextPath}/recherche" method="post" class="search-form-advanced" data-validate>
                            <!-- Date Selection -->
                            <div class="form-section">
                                <div class="form-section-header">
                                    <i class="fas fa-calendar-alt"></i>
                                    <span>Quand souhaitez-vous voyager ?</span>
                                </div>
                                <div class="form-group">
                                    <label for="date" class="form-label">Date du voyage</label>
                                    <div class="input-group">
                                        <div class="input-icon">
                                            <i class="fas fa-calendar-alt"></i>
                                        </div>
                                        <input type="date"
                                               class="form-control"
                                               id="date"
                                               name="date"
                                               value="${date}"
                                               required
                                               min="${pageContext.request.getAttribute('today')}">
                                    </div>
                                </div>
                            </div>

                            <!-- Destination Selection -->
                            <div class="form-section">
                                <div class="form-section-header">
                                    <i class="fas fa-map-marker-alt"></i>
                                    <span>Où souhaitez-vous aller ?</span>
                                </div>
                                <div class="destinations-container">
                                    <div class="row">
                                        <div class="col-md-5">
                                            <div class="form-group">
                                                <label for="villeDepart" class="form-label">
                                                    <i class="fas fa-play-circle"></i>
                                                    Ville de départ
                                                </label>
                                                <div class="select-wrapper">
                                                    <select class="form-control select-enhanced" id="villeDepart" name="villeDepart" required>
                                                        <option value="">Choisir la ville de départ</option>
                                                        <c:forEach var="gare" items="${gares}">
                                                            <option value="${gare.ville}"
                                                                    ${gare.ville == villeDepart ? 'selected' : ''}>
                                                                ${gare.ville}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                    <div class="select-icon">
                                                        <i class="fas fa-chevron-down"></i>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="col-md-2 d-flex align-items-center justify-content-center">
                                            <button type="button" class="swap-cities-btn" id="swapCities" data-tooltip="Échanger les villes">
                                                <i class="fas fa-exchange-alt"></i>
                                            </button>
                                        </div>

                                        <div class="col-md-5">
                                            <div class="form-group">
                                                <label for="villeArrivee" class="form-label">
                                                    <i class="fas fa-stop-circle"></i>
                                                    Ville d'arrivée
                                                </label>
                                                <div class="select-wrapper">
                                                    <select class="form-control select-enhanced" id="villeArrivee" name="villeArrivee" required>
                                                        <option value="">Choisir la ville d'arrivée</option>
                                                        <c:forEach var="gare" items="${gares}">
                                                            <option value="${gare.ville}"
                                                                    ${gare.ville == villeArrivee ? 'selected' : ''}>
                                                                ${gare.ville}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                    <div class="select-icon">
                                                        <i class="fas fa-chevron-down"></i>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Options -->
                            <div class="form-section">
                                <div class="form-section-header">
                                    <i class="fas fa-cog"></i>
                                    <span>Options de voyage</span>
                                </div>
                                <div class="form-options">
                                    <div class="option-card">
                                        <input type="checkbox" class="option-checkbox" id="direct" name="direct">
                                        <label for="direct" class="option-label">
                                            <div class="option-icon">
                                                <i class="fas fa-route"></i>
                                            </div>
                                            <div class="option-content">
                                                <span class="option-title">Voyages directs uniquement</span>
                                                <span class="option-description">Sans correspondance</span>
                                            </div>
                                            <div class="option-check">
                                                <i class="fas fa-check"></i>
                                            </div>
                                        </label>
                                    </div>
                                </div>
                            </div>

                            <!-- Submit Button -->
                            <div class="form-actions">
                                <button type="submit" class="btn btn-primary btn-lg btn-search">
                                    <i class="fas fa-search"></i>
                                    <span>Rechercher des trains</span>
                                    <div class="btn-shine"></div>
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <!-- Quick Tips -->
            <div class="row mt-8">
                <div class="col-12">
                    <div class="tips-section fade-in-up">
                        <h3 class="tips-title">
                            <i class="fas fa-lightbulb"></i>
                            Conseils pour votre recherche
                        </h3>
                        <div class="tips-grid">
                            <div class="tip-card">
                                <div class="tip-icon">
                                    <i class="fas fa-clock"></i>
                                </div>
                                <div class="tip-content">
                                    <h4>Réservez à l'avance</h4>
                                    <p>Meilleurs prix et plus de choix de places</p>
                                </div>
                            </div>
                            <div class="tip-card">
                                <div class="tip-icon">
                                    <i class="fas fa-calendar-check"></i>
                                </div>
                                <div class="tip-content">
                                    <h4>Dates flexibles</h4>
                                    <p>Voyagez en semaine pour économiser</p>
                                </div>
                            </div>
                            <div class="tip-card">
                                <div class="tip-icon">
                                    <i class="fas fa-route"></i>
                                </div>
                                <div class="tip-content">
                                    <h4>Voyages directs</h4>
                                    <p>Plus rapides et plus confortables</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>

    <jsp:include page="footer.jsp" />

    <!-- Scripts -->
    <script src="${pageContext.request.contextPath}/assets/js/app.js"></script>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // Set minimum date to today
            const dateInput = document.getElementById('date');
            if (dateInput && !dateInput.value) {
                const today = new Date().toISOString().split('T')[0];
                dateInput.min = today;
                dateInput.value = today;
            }

            // Swap cities functionality
            const swapBtn = document.getElementById('swapCities');
            const departSelect = document.getElementById('villeDepart');
            const arriveeSelect = document.getElementById('villeArrivee');

            if (swapBtn && departSelect && arriveeSelect) {
                swapBtn.addEventListener('click', function() {
                    const temp = departSelect.value;
                    departSelect.value = arriveeSelect.value;
                    arriveeSelect.value = temp;

                    // Animation
                    this.style.transform = 'rotate(180deg)';
                    setTimeout(() => {
                        this.style.transform = 'rotate(0deg)';
                    }, 300);
                });
            }
        });
    </script>
</body>
</html>
