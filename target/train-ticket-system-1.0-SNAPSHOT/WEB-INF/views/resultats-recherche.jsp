<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Résultats de recherche - TrainTicket</title>
    <meta name="description" content="Résultats de votre recherche de trains. Sélectionnez votre voyage et réservez vos billets.">

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
                        <li class="breadcrumb-item">
                            <a href="${pageContext.request.contextPath}/recherche">
                                <i class="fas fa-search"></i> Recherche
                            </a>
                        </li>
                        <li class="breadcrumb-item active">
                            <i class="fas fa-list"></i> Résultats
                        </li>
                    </ol>
                </nav>
                <h1 class="page-title">Résultats de recherche</h1>
                <p class="page-subtitle">
                    Sélectionnez votre voyage parmi les options disponibles
                </p>
            </div>
        </div>
    </section>

    <!-- Main Content -->
    <main class="main-content">
        <div class="container">
            <!-- Search Summary -->
            <div class="search-summary fade-in-up">
                <div class="summary-card">
                    <div class="summary-header">
                        <div class="summary-icon">
                            <i class="fas fa-search"></i>
                        </div>
                        <h3>Votre recherche</h3>
                    </div>
                    <div class="summary-details">
                        <div class="summary-item">
                            <div class="summary-label">
                                <i class="fas fa-calendar"></i>
                                Date
                            </div>
                            <div class="summary-value">${date}</div>
                        </div>
                        <div class="summary-separator">
                            <i class="fas fa-arrow-right"></i>
                        </div>
                        <div class="summary-item">
                            <div class="summary-label">
                                <i class="fas fa-map-marker-alt"></i>
                                Départ
                            </div>
                            <div class="summary-value">${villeDepart}</div>
                        </div>
                        <div class="summary-separator">
                            <i class="fas fa-arrow-right"></i>
                        </div>
                        <div class="summary-item">
                            <div class="summary-label">
                                <i class="fas fa-map-marker-alt"></i>
                                Arrivée
                            </div>
                            <div class="summary-value">${villeArrivee}</div>
                        </div>
                    </div>
                    <div class="summary-actions">
                        <a href="${pageContext.request.contextPath}/recherche" class="btn btn-outline-primary btn-sm">
                            <i class="fas fa-edit"></i>
                            <span>Modifier la recherche</span>
                        </a>
                    </div>
                </div>
            </div>

            <!-- Empty State -->
            <c:if test="${empty voyages}">
                <div class="empty-results fade-in-up">
                    <div class="empty-icon">
                        <i class="fas fa-search"></i>
                    </div>
                    <h3>Aucun voyage trouvé</h3>
                    <p>Désolé, nous n'avons trouvé aucun voyage correspondant à vos critères de recherche.</p>
                    <div class="empty-suggestions">
                        <h4>Suggestions :</h4>
                        <ul>
                            <li>Vérifiez les villes de départ et d'arrivée</li>
                            <li>Essayez une autre date</li>
                            <li>Recherchez des voyages avec correspondance</li>
                        </ul>
                    </div>
                    <a href="${pageContext.request.contextPath}/recherche" class="btn btn-primary">
                        <i class="fas fa-search"></i>
                        <span>Nouvelle recherche</span>
                    </a>
                </div>
            </c:if>

            <!-- Results Grid -->
            <c:if test="${not empty voyages}">
                <div class="results-header fade-in-up">
                    <div class="results-count">
                        <i class="fas fa-train"></i>
                        <span>${voyages.size()} voyage(s) trouvé(s)</span>
                    </div>
                    <div class="results-sort">
                        <label for="sortBy">Trier par :</label>
                        <select id="sortBy" class="form-control form-control-sm">
                            <option value="heure">Heure de départ</option>
                            <option value="prix">Prix</option>
                            <option value="duree">Durée</option>
                        </select>
                    </div>
                </div>

                <div class="voyages-grid">
                    <c:forEach items="${voyages}" var="voyage" varStatus="status">
                        <div class="voyage-card fade-in-up" style="animation-delay: ${status.index * 0.1}s">
                            <!-- Card Header -->
                            <div class="voyage-header">
                                <div class="train-info">
                                    <div class="train-icon">
                                        <i class="fas fa-train"></i>
                                    </div>
                                    <div class="train-details">
                                        <div class="train-number">${voyage.train.numero}</div>
                                        <div class="train-name">${voyage.train.nom}</div>
                                    </div>
                                </div>
                                <div class="availability-badge">
                                    <c:choose>
                                        <c:when test="${voyage.placesDisponibles > 10}">
                                            <span class="badge badge-success">
                                                <i class="fas fa-check-circle"></i>
                                                Disponible
                                            </span>
                                        </c:when>
                                        <c:when test="${voyage.placesDisponibles > 0}">
                                            <span class="badge badge-warning">
                                                <i class="fas fa-exclamation-triangle"></i>
                                                Peu de places
                                            </span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge-danger">
                                                <i class="fas fa-times-circle"></i>
                                                Complet
                                            </span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>

                            <!-- Journey Timeline -->
                            <div class="journey-timeline">
                                <div class="timeline-point departure">
                                    <div class="timeline-time">
                                        <c:choose>
                                            <c:when test="${voyage.heureDepart != null}">
                                                ${voyage.heureDepart}
                                            </c:when>
                                            <c:otherwise>
                                                --:--
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div class="timeline-station">${villeDepart}</div>
                                </div>

                                <div class="timeline-line">
                                    <div class="timeline-duration">
                                        <c:set var="dureeMinutes" value="${(voyage.heureArrivee.hour * 60 + voyage.heureArrivee.minute) - (voyage.heureDepart.hour * 60 + voyage.heureDepart.minute)}" />
                                        <c:if test="${dureeMinutes < 0}">
                                            <c:set var="dureeMinutes" value="${dureeMinutes + 1440}" />
                                        </c:if>
                                        <i class="fas fa-clock"></i>
                                        <span>${dureeMinutes / 60}h${dureeMinutes % 60}</span>
                                    </div>
                                </div>

                                <div class="timeline-point arrival">
                                    <div class="timeline-time">
                                        <c:choose>
                                            <c:when test="${voyage.heureArrivee != null}">
                                                ${voyage.heureArrivee}
                                            </c:when>
                                            <c:otherwise>
                                                --:--
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div class="timeline-station">${villeArrivee}</div>
                                </div>
                            </div>

                            <!-- Card Footer -->
                            <div class="voyage-footer">
                                <div class="voyage-info">
                                    <div class="info-item">
                                        <i class="fas fa-users"></i>
                                        <span>${voyage.placesDisponibles} places</span>
                                    </div>
                                    <div class="price-info">
                                        <span class="price-label">À partir de</span>
                                        <span class="price-value">${voyage.prix} TND</span>
                                    </div>
                                </div>
                                <div class="voyage-actions">
                                    <c:choose>
                                        <c:when test="${voyage.placesDisponibles > 0}">
                                            <a href="${pageContext.request.contextPath}/selection?id=${voyage.id}"
                                               class="btn btn-primary">
                                                <i class="fas fa-ticket-alt"></i>
                                                <span>Sélectionner</span>
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <button class="btn btn-secondary" disabled>
                                                <i class="fas fa-ban"></i>
                                                <span>Complet</span>
                                            </button>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:if>

            <!-- Bottom Actions -->
            <div class="bottom-actions fade-in-up">
                <a href="${pageContext.request.contextPath}/recherche" class="btn btn-outline-primary">
                    <i class="fas fa-search"></i>
                    <span>Nouvelle recherche</span>
                </a>
                <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">
                    <i class="fas fa-home"></i>
                    <span>Retour à l'accueil</span>
                </a>
            </div>
        </div>
    </main>

    <jsp:include page="footer.jsp" />

    <!-- Scripts -->
    <script src="${pageContext.request.contextPath}/assets/js/app.js"></script>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // Animate voyage cards on scroll
            const observerOptions = {
                threshold: 0.1,
                rootMargin: '0px 0px -50px 0px'
            };

            const observer = new IntersectionObserver(function(entries) {
                entries.forEach(entry => {
                    if (entry.isIntersecting) {
                        entry.target.classList.add('animate-in');
                    }
                });
            }, observerOptions);

            // Observe all voyage cards
            document.querySelectorAll('.voyage-card').forEach(card => {
                observer.observe(card);
            });

            // Sort functionality
            const sortSelect = document.getElementById('sortBy');
            if (sortSelect) {
                sortSelect.addEventListener('change', function() {
                    const sortBy = this.value;
                    const voyagesGrid = document.querySelector('.voyages-grid');
                    const voyageCards = Array.from(voyagesGrid.children);

                    voyageCards.sort((a, b) => {
                        if (sortBy === 'heure') {
                            const timeA = a.querySelector('.departure .timeline-time').textContent;
                            const timeB = b.querySelector('.departure .timeline-time').textContent;
                            return timeA.localeCompare(timeB);
                        } else if (sortBy === 'prix') {
                            const priceA = parseFloat(a.querySelector('.price-value').textContent);
                            const priceB = parseFloat(b.querySelector('.price-value').textContent);
                            return priceA - priceB;
                        } else if (sortBy === 'duree') {
                            const durationA = a.querySelector('.timeline-duration span').textContent;
                            const durationB = b.querySelector('.timeline-duration span').textContent;
                            return durationA.localeCompare(durationB);
                        }
                        return 0;
                    });

                    // Re-append sorted cards
                    voyageCards.forEach(card => voyagesGrid.appendChild(card));
                });
            }
        });
    </script>
</body>
</html>
