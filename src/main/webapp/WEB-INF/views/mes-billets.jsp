<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mes Billets - TrainTicket</title>
    <meta name="description" content="Gérez vos billets de train, téléchargez vos e-tickets et suivez vos voyages.">

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
                            <i class="fas fa-ticket-alt"></i> Mes Billets
                        </li>
                    </ol>
                </nav>
                <h1 class="page-title">Mes Billets</h1>
                <p class="page-subtitle">
                    Gérez vos réservations et téléchargez vos e-tickets
                </p>
            </div>
        </div>
    </section>

    <!-- Main Content -->
    <main class="main-content">
        <div class="container">
            <!-- Filter Tabs -->
            <div class="filter-tabs-simple fade-in-up">
                <div class="tabs-container-simple">
                    <a class="tab-item-simple ${empty filter ? 'active' : ''}" href="${pageContext.request.contextPath}/mes-billets">
                        <i class="fas fa-list"></i>
                        <span>Tous</span>
                    </a>
                    <a class="tab-item-simple ${filter eq 'achetes' ? 'active' : ''}" href="${pageContext.request.contextPath}/mes-billets?filter=achetes">
                        <i class="fas fa-shopping-cart"></i>
                        <span>Achetés</span>
                    </a>
                    <a class="tab-item-simple ${filter eq 'utilises' ? 'active' : ''}" href="${pageContext.request.contextPath}/mes-billets?filter=utilises">
                        <i class="fas fa-check-circle"></i>
                        <span>Utilisés</span>
                    </a>
                    <a class="tab-item-simple ${filter eq 'annules' ? 'active' : ''}" href="${pageContext.request.contextPath}/mes-billets?filter=annules">
                        <i class="fas fa-times-circle"></i>
                        <span>Annulés</span>
                    </a>
                </div>
            </div>

            <!-- Empty State -->
            <c:if test="${empty billets}">
                <div class="empty-state-simple fade-in-up">
                    <div class="empty-icon-simple">
                        <i class="fas fa-ticket-alt"></i>
                    </div>
                    <h3>Aucun billet trouvé</h3>
                    <p>Vous n'avez pas encore de billets dans cette catégorie.</p>
                    <a href="${pageContext.request.contextPath}/recherche" class="btn btn-primary">
                        <i class="fas fa-search"></i>
                        <span>Rechercher un voyage</span>
                    </a>
                </div>
            </c:if>

            <!-- Billets Grid -->
            <c:if test="${not empty billets}">
                <div class="billets-grid">
                    <c:forEach items="${billets}" var="billet" varStatus="status">
                        <div class="billet-card fade-in-up" style="animation-delay: ${status.index * 0.1}s">
                            <!-- Card Header -->
                            <div class="billet-header ${billet.etat eq 'ACHETE' ? 'header-success' : billet.etat eq 'UTILISE' ? 'header-secondary' : 'header-danger'}">
                                <div class="billet-title">
                                    <i class="fas fa-ticket-alt"></i>
                                    <span>Billet #${billet.id}</span>
                                </div>
                                <div class="billet-status">
                                    <c:choose>
                                        <c:when test="${billet.etat eq 'ACHETE'}">
                                            <i class="fas fa-check-circle"></i>
                                            <span>Acheté</span>
                                        </c:when>
                                        <c:when test="${billet.etat eq 'UTILISE'}">
                                            <i class="fas fa-flag-checkered"></i>
                                            <span>Utilisé</span>
                                        </c:when>
                                        <c:when test="${billet.etat eq 'ANNULE'}">
                                            <i class="fas fa-times-circle"></i>
                                            <span>Annulé</span>
                                        </c:when>
                                    </c:choose>
                                </div>
                            </div>

                            <!-- Card Body -->
                            <div class="billet-body">
                                <!-- Voyage Info -->
                                <div class="voyage-section">
                                    <h6 class="section-title">
                                        <i class="fas fa-route"></i>
                                        Voyage
                                    </h6>
                                    <div class="voyage-details">
                                        <div class="voyage-item">
                                            <strong>Date:</strong> ${billet.voyage.date}
                                        </div>
                                        <div class="voyage-item">
                                            <strong>Train:</strong> ${billet.voyage.train.numero} - ${billet.voyage.train.nom}
                                        </div>
                                        <div class="voyage-item">
                                            <strong>Départ:</strong> ${billet.voyage.trajet.gareDepart.nom} (${billet.voyage.trajet.gareDepart.ville}) à
                                            <fmt:parseDate value="${billet.voyage.heureDepart}" pattern="HH:mm" var="departDate" />
                                            <fmt:formatDate value="${departDate}" pattern="HH:mm" />
                                        </div>
                                        <div class="voyage-item">
                                            <strong>Arrivée:</strong> ${billet.voyage.trajet.gareArrivee.nom} (${billet.voyage.trajet.gareArrivee.ville}) à
                                            <fmt:parseDate value="${billet.voyage.heureArrivee}" pattern="HH:mm" var="arriveeDate" />
                                            <fmt:formatDate value="${arriveeDate}" pattern="HH:mm" />
                                        </div>
                                    </div>
                                </div>

                                <!-- Details Info -->
                                <div class="details-section">
                                    <h6 class="section-title">
                                        <i class="fas fa-info-circle"></i>
                                        Détails
                                    </h6>
                                    <div class="details-grid">
                                        <div class="detail-item-simple">
                                            <strong>Classe:</strong> ${billet.classe.nom}
                                        </div>
                                        <div class="detail-item-simple">
                                            <strong>Siège:</strong> ${billet.numeroSiege}
                                        </div>
                                        <div class="detail-item-simple">
                                            <strong>Prix:</strong> <span class="price">${billet.prix} TND</span>
                                        </div>
                                        <div class="detail-item-simple">
                                            <strong>Date d'achat:</strong> ${billet.dateAchat}
                                        </div>
                                        <c:if test="${not empty billet.preferences}">
                                            <div class="detail-item-simple full-width">
                                                <strong>Préférences:</strong> ${billet.preferences}
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                            <!-- Card Footer -->
                            <div class="billet-footer">
                                <c:if test="${billet.etat eq 'ACHETE'}">
                                    <a href="${pageContext.request.contextPath}/download-pdf?billetId=${billet.id}"
                                       class="btn btn-primary btn-sm">
                                        <i class="fas fa-download"></i>
                                        <span>Télécharger PDF</span>
                                    </a>
                                    <form method="post" action="${pageContext.request.contextPath}/mes-billets"
                                          style="display: inline;"
                                          onsubmit="return confirm('Êtes-vous sûr de vouloir annuler ce billet ?');">
                                        <input type="hidden" name="action" value="annuler">
                                        <input type="hidden" name="billetId" value="${billet.id}">
                                        <button type="submit" class="btn btn-outline-danger btn-sm">
                                            <i class="fas fa-times"></i>
                                            <span>Annuler</span>
                                        </button>
                                    </form>
                                </c:if>
                                <c:if test="${billet.etat eq 'UTILISE'}">
                                    <div class="status-message">
                                        <i class="fas fa-check-circle"></i>
                                        <span>Ce billet a été utilisé</span>
                                    </div>
                                    <a href="${pageContext.request.contextPath}/download-pdf?billetId=${billet.id}"
                                       class="btn btn-primary btn-sm">
                                        <i class="fas fa-download"></i>
                                        <span>Télécharger PDF</span>
                                    </a>
                                </c:if>
                                <c:if test="${billet.etat eq 'ANNULE'}">
                                    <div class="status-message">
                                        <i class="fas fa-ban"></i>
                                        <span>Ce billet a été annulé</span>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:if>
        </div>
    </main>

    <jsp:include page="footer.jsp" />

    <!-- Scripts -->
    <script src="${pageContext.request.contextPath}/assets/js/app.js"></script>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // Animate billet cards on scroll
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

            // Observe all billet cards
            document.querySelectorAll('.billet-card').forEach(card => {
                observer.observe(card);
            });
        });
    </script>
</body>
</html>
