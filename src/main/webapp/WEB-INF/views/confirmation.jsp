<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Confirmation d'achat - TrainTicket</title>
    <meta name="description" content="Votre billet de train a été confirmé avec succès.">

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

    <!-- Success Header -->
    <section class="success-header">
        <div class="container">
            <div class="success-content">
                <div class="success-animation">
                    <div class="success-circle">
                        <i class="fas fa-check"></i>
                    </div>
                </div>
                <h1 class="success-title">Paiement confirmé !</h1>
                <p class="success-subtitle">
                    Votre billet de train a été acheté avec succès
                </p>
                <div class="success-badge">
                    <i class="fas fa-ticket-alt"></i>
                    <span>Billet N° ${billet.id}</span>
                </div>
            </div>
        </div>
    </section>

    <!-- Main Content -->
    <section class="main-content">
        <div class="container">
            <!-- Centered Confirmation Container -->
            <div class="confirmation-container">
                <div class="confirmation-content">
                    <!-- Digital Ticket -->
                    <div class="digital-ticket">
                        <div class="ticket-header">
                            <div class="ticket-logo">
                                <i class="fas fa-train"></i>
                                <span>TrainTicket</span>
                            </div>
                            <div class="ticket-number">
                                <span>Billet N°</span>
                                <strong>${billet.id}</strong>
                            </div>
                        </div>

                        <div class="ticket-journey">
                            <div class="journey-route">
                                <div class="route-station departure">
                                    <div class="station-time">
                                        <c:choose>
                                            <c:when test="${billet.voyage.heureDepart != null}">
                                                ${billet.voyage.heureDepart}
                                            </c:when>
                                            <c:otherwise>--:--</c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div class="station-name">${billet.voyage.trajet.gareDepart.nom}</div>
                                    <div class="station-city">${billet.voyage.trajet.gareDepart.ville}</div>
                                </div>

                                <div class="route-connector">
                                    <div class="connector-line"></div>
                                    <div class="train-icon">
                                        <i class="fas fa-train"></i>
                                    </div>
                                    <div class="train-info">
                                        <span>${billet.voyage.train.numero}</span>
                                        <span>${billet.voyage.train.nom}</span>
                                    </div>
                                </div>

                                <div class="route-station arrival">
                                    <div class="station-time">
                                        <c:choose>
                                            <c:when test="${billet.voyage.heureArrivee != null}">
                                                ${billet.voyage.heureArrivee}
                                            </c:when>
                                            <c:otherwise>--:--</c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div class="station-name">${billet.voyage.trajet.gareArrivee.nom}</div>
                                    <div class="station-city">${billet.voyage.trajet.gareArrivee.ville}</div>
                                </div>
                            </div>

                            <div class="journey-date">
                                <i class="fas fa-calendar"></i>
                                <span>
                                    <c:choose>
                                        <c:when test="${billet.voyage.date != null}">
                                            <c:set var="day" value="${billet.voyage.date.dayOfMonth}" />
                                            <c:set var="month" value="${billet.voyage.date.monthValue}" />
                                            <c:set var="year" value="${billet.voyage.date.year}" />
                                            <c:if test="${day < 10}">0</c:if>${day}/<c:if test="${month < 10}">0</c:if>${month}/${year}
                                        </c:when>
                                        <c:otherwise>Date non définie</c:otherwise>
                                    </c:choose>
                                </span>
                            </div>
                        </div>

                        <div class="ticket-details">
                            <div class="detail-row">
                                <div class="detail-group">
                                    <span class="detail-label">Passager</span>
                                    <span class="detail-value">${billet.user.prenom} ${billet.user.nom}</span>
                                </div>
                                <div class="detail-group">
                                    <span class="detail-label">Classe</span>
                                    <span class="detail-value">${billet.classe.nom}</span>
                                </div>
                            </div>

                            <div class="detail-row">
                                <div class="detail-group">
                                    <span class="detail-label">Siège</span>
                                    <span class="detail-value">${billet.numeroSiege}</span>
                                </div>
                                <div class="detail-group">
                                    <span class="detail-label">Prix payé</span>
                                    <span class="detail-value price">${billet.prix} TND</span>
                                </div>
                            </div>

                            <c:if test="${not empty billet.preferences}">
                                <div class="detail-row">
                                    <div class="detail-group full-width">
                                        <span class="detail-label">Préférences</span>
                                        <span class="detail-value">${billet.preferences}</span>
                                    </div>
                                </div>
                            </c:if>
                        </div>

                        <div class="ticket-qr">
                            <div class="qr-placeholder">
                                <i class="fas fa-qrcode"></i>
                                <span>Code QR</span>
                            </div>
                            <div class="qr-text">
                                <p>Présentez ce code QR lors de votre voyage</p>
                            </div>
                        </div>
                    </div>

                    <!-- Action Buttons -->
                    <div class="action-buttons">
                        <a href="${pageContext.request.contextPath}/download-pdf?billetId=${billet.id}"
                           class="btn btn-primary btn-large">
                            <i class="fas fa-download"></i>
                            Télécharger le PDF
                        </a>
                        <a href="${pageContext.request.contextPath}/mes-billets"
                           class="btn btn-outline btn-large">
                            <i class="fas fa-list"></i>
                            Mes billets
                        </a>
                    </div>

                    <!-- Next Steps -->
                    <div class="info-card">
                        <div class="info-header">
                            <h4>
                                <i class="fas fa-route"></i>
                                Prochaines étapes
                            </h4>
                        </div>
                        <div class="info-content">
                            <div class="step-item">
                                <div class="step-number">1</div>
                                <div class="step-content">
                                    <strong>Téléchargez votre billet</strong>
                                    <p>Sauvegardez le PDF sur votre téléphone</p>
                                </div>
                            </div>
                            <div class="step-item">
                                <div class="step-number">2</div>
                                <div class="step-content">
                                    <strong>Arrivez en avance</strong>
                                    <p>Présentez-vous 15 min avant le départ</p>
                                </div>
                            </div>
                            <div class="step-item">
                                <div class="step-number">3</div>
                                <div class="step-content">
                                    <strong>Pièce d'identité</strong>
                                    <p>Munissez-vous d'une pièce d'identité valide</p>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Important Info -->
                    <div class="info-card">
                        <div class="info-header">
                            <h4>
                                <i class="fas fa-info-circle"></i>
                                Informations importantes
                            </h4>
                        </div>
                        <div class="info-content">
                            <div class="info-item">
                                <i class="fas fa-check-circle text-success"></i>
                                <span>Billet confirmé et valide</span>
                            </div>
                            <div class="info-item">
                                <i class="fas fa-envelope text-primary"></i>
                                <span>Confirmation envoyée par email</span>
                            </div>
                            <div class="info-item">
                                <i class="fas fa-mobile-alt text-primary"></i>
                                <span>Accessible dans votre espace</span>
                            </div>
                            <div class="info-item">
                                <i class="fas fa-headset text-primary"></i>
                                <span>Support client 24/7</span>
                            </div>
                        </div>
                    </div>

                    <!-- Contact Support -->
                    <div class="support-card">
                        <div class="support-icon">
                            <i class="fas fa-headset"></i>
                        </div>
                        <div class="support-content">
                            <h4>Besoin d'aide ?</h4>
                            <p>Notre équipe est là pour vous accompagner</p>
                            <a href="#" class="btn btn-outline btn-small">
                                <i class="fas fa-phone"></i>
                                Contacter le support
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <jsp:include page="footer.jsp" />

    <!-- Scripts -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(document).ready(function() {
            // Success animation
            setTimeout(function() {
                $('.success-circle').addClass('animate');
            }, 500);

            // Auto-scroll to ticket after animation
            setTimeout(function() {
                $('html, body').animate({
                    scrollTop: $('.digital-ticket').offset().top - 100
                }, 1000);
            }, 2000);

            // Print functionality
            $('.btn-print').on('click', function(e) {
                e.preventDefault();
                window.print();
            });
        });
    </script>

    <style>
        @media print {
            .navbar, .footer, .action-buttons, .col-lg-4 {
                display: none !important;
            }
            .digital-ticket {
                box-shadow: none !important;
                border: 2px solid #000 !important;
            }
        }
    </style>
</body>
</html>
