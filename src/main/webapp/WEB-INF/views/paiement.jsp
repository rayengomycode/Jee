<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Paiement - TrainTicket</title>
    <meta name="description" content="Finalisez votre achat de billet de train en toute sécurité.">

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
                <div class="breadcrumb">
                    <a href="${pageContext.request.contextPath}/" class="breadcrumb-item">
                        <i class="fas fa-home"></i>
                        Accueil
                    </a>
                    <span class="breadcrumb-separator">
                        <i class="fas fa-chevron-right"></i>
                    </span>
                    <a href="${pageContext.request.contextPath}/recherche" class="breadcrumb-item">
                        Recherche
                    </a>
                    <span class="breadcrumb-separator">
                        <i class="fas fa-chevron-right"></i>
                    </span>
                    <a href="${pageContext.request.contextPath}/selection" class="breadcrumb-item">
                        Sélection
                    </a>
                    <span class="breadcrumb-separator">
                        <i class="fas fa-chevron-right"></i>
                    </span>
                    <span class="breadcrumb-item active">
                        Paiement
                    </span>
                </div>
                <h1 class="page-title">
                    <i class="fas fa-credit-card"></i>
                    Finaliser votre achat
                </h1>
                <p class="page-subtitle">
                    Vérifiez vos informations et procédez au paiement sécurisé
                </p>
            </div>
        </div>
    </section>

    <!-- Main Content -->
    <section class="main-content">
        <div class="container">
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

            <!-- Centered Payment Container -->
            <div class="payment-container">
                <div class="payment-content">
                    <!-- Order Summary -->
                    <div class="payment-step">
                        <div class="step-header">
                            <div class="step-number">1</div>
                            <div class="step-info">
                                <h3>Récapitulatif de votre commande</h3>
                                <p>Vérifiez les détails de votre voyage</p>
                            </div>
                        </div>

                        <div class="order-summary">
                            <div class="journey-card">
                                <div class="journey-header">
                                    <div class="train-info">
                                        <i class="fas fa-train"></i>
                                        <span>Train ${voyage.train.numero} - ${voyage.train.nom}</span>
                                    </div>
                                    <div class="journey-date">
                                        <i class="fas fa-calendar"></i>
                                        <span>${voyage.date}</span>
                                    </div>
                                </div>

                                <div class="journey-route">
                                    <div class="route-point">
                                        <div class="route-time">
                                            <fmt:parseDate value="${voyage.heureDepart}" pattern="HH:mm" var="departDate" />
                                            <fmt:formatDate value="${departDate}" pattern="HH:mm" />
                                        </div>
                                        <div class="route-station">
                                            <strong>${voyage.trajet.gareDepart.nom}</strong>
                                            <span>${voyage.trajet.gareDepart.ville}</span>
                                        </div>
                                    </div>

                                    <div class="route-arrow">
                                        <i class="fas fa-arrow-right"></i>
                                    </div>

                                    <div class="route-point">
                                        <div class="route-time">
                                            <fmt:parseDate value="${voyage.heureArrivee}" pattern="HH:mm" var="arriveeDate" />
                                            <fmt:formatDate value="${arriveeDate}" pattern="HH:mm" />
                                        </div>
                                        <div class="route-station">
                                            <strong>${voyage.trajet.gareArrivee.nom}</strong>
                                            <span>${voyage.trajet.gareArrivee.ville}</span>
                                        </div>
                                    </div>
                                </div>

                                <div class="journey-details">
                                    <div class="detail-item">
                                        <span class="detail-label">Classe</span>
                                        <span class="detail-value">${classe.nom}</span>
                                    </div>
                                    <div class="detail-item">
                                        <span class="detail-label">Prix</span>
                                        <span class="detail-value price">${prixTotal} TND</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Payment Method -->
                    <div class="payment-step">
                        <div class="step-header">
                            <div class="step-number">2</div>
                            <div class="step-info">
                                <h3>Méthode de paiement</h3>
                                <p>Choisissez votre mode de paiement préféré</p>
                            </div>
                        </div>

                        <form action="${pageContext.request.contextPath}/paiement" method="post" class="payment-form">
                            <div class="payment-methods">
                                <div class="payment-method">
                                    <input type="radio" id="carte_credit" name="methodePaiement" value="CARTE_CREDIT" checked>
                                    <label for="carte_credit" class="payment-method-label">
                                        <div class="payment-method-icon">
                                            <i class="fas fa-credit-card"></i>
                                        </div>
                                        <div class="payment-method-info">
                                            <strong>Carte de crédit</strong>
                                            <span>Visa, Mastercard, American Express</span>
                                        </div>
                                        <div class="payment-method-check">
                                            <i class="fas fa-check"></i>
                                        </div>
                                    </label>
                                </div>

                                <div class="payment-method">
                                    <input type="radio" id="virement" name="methodePaiement" value="VIREMENT">
                                    <label for="virement" class="payment-method-label">
                                        <div class="payment-method-icon">
                                            <i class="fas fa-university"></i>
                                        </div>
                                        <div class="payment-method-info">
                                            <strong>Virement bancaire</strong>
                                            <span>Paiement par virement sécurisé</span>
                                        </div>
                                        <div class="payment-method-check">
                                            <i class="fas fa-check"></i>
                                        </div>
                                    </label>
                                </div>

                                <div class="payment-method">
                                    <input type="radio" id="paypal" name="methodePaiement" value="PAYPAL">
                                    <label for="paypal" class="payment-method-label">
                                        <div class="payment-method-icon">
                                            <i class="fab fa-paypal"></i>
                                        </div>
                                        <div class="payment-method-info">
                                            <strong>PayPal</strong>
                                            <span>Paiement rapide et sécurisé</span>
                                        </div>
                                        <div class="payment-method-check">
                                            <i class="fas fa-check"></i>
                                        </div>
                                    </label>
                                </div>
                            </div>

                            <!-- Credit Card Form -->
                            <div id="carteCreditForm" class="card-form">
                                <div class="form-group">
                                    <label for="numeroCarte">
                                        <i class="fas fa-credit-card"></i>
                                        Numéro de carte
                                    </label>
                                    <input type="text" class="form-control" id="numeroCarte" placeholder="1234 5678 9012 3456" maxlength="19">
                                </div>

                                <div class="form-row">
                                    <div class="form-group col-md-6">
                                        <label for="dateExpiration">
                                            <i class="fas fa-calendar"></i>
                                            Date d'expiration
                                        </label>
                                        <input type="text" class="form-control" id="dateExpiration" placeholder="MM/AA" maxlength="5">
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label for="cvv">
                                            <i class="fas fa-lock"></i>
                                            CVV
                                        </label>
                                        <input type="text" class="form-control" id="cvv" placeholder="123" maxlength="4">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="nomCarte">
                                        <i class="fas fa-user"></i>
                                        Nom sur la carte
                                    </label>
                                    <input type="text" class="form-control" id="nomCarte" placeholder="Nom complet">
                                </div>
                            </div>

                            <!-- Security Notice -->
                            <div class="security-notice">
                                <div class="security-icon">
                                    <i class="fas fa-shield-alt"></i>
                                </div>
                                <div class="security-text">
                                    <strong>Paiement 100% sécurisé</strong>
                                    <p>Vos données sont protégées par un cryptage SSL 256 bits</p>
                                </div>
                            </div>

                            <!-- Submit Button -->
                            <div class="payment-actions">
                                <a href="${pageContext.request.contextPath}/selection" class="btn btn-outline">
                                    <i class="fas fa-arrow-left"></i>
                                    Retour
                                </a>
                                <button type="submit" class="btn btn-primary btn-large">
                                    <i class="fas fa-lock"></i>
                                    Payer ${prixTotal} TND
                                </button>
                            </div>
                        </form>
                    </div>

                    <!-- Price Summary -->
                    <div class="price-summary-card">
                        <div class="price-summary-header">
                            <h3>
                                <i class="fas fa-receipt"></i>
                                Récapitulatif du paiement
                            </h3>
                        </div>
                        <div class="price-summary-content">
                            <div class="price-breakdown">
                                <div class="price-item">
                                    <span class="price-label">Billet ${classe.nom}</span>
                                    <span class="price-amount">${prixTotal} TND</span>
                                </div>
                                <div class="price-divider"></div>
                                <div class="price-total">
                                    <span class="total-label">Total à payer</span>
                                    <span class="total-amount">${prixTotal} TND</span>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Security Info -->
                    <div class="info-card">
                        <div class="info-header">
                            <h4>
                                <i class="fas fa-shield-alt"></i>
                                Paiement sécurisé
                            </h4>
                        </div>
                        <div class="info-content">
                            <div class="info-item">
                                <i class="fas fa-check-circle text-success"></i>
                                <span>Transactions cryptées SSL</span>
                            </div>
                            <div class="info-item">
                                <i class="fas fa-check-circle text-success"></i>
                                <span>Données protégées</span>
                            </div>
                            <div class="info-item">
                                <i class="fas fa-check-circle text-success"></i>
                                <span>Confirmation par email</span>
                            </div>
                            <div class="info-item">
                                <i class="fas fa-check-circle text-success"></i>
                                <span>Support client 24/7</span>
                            </div>
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
            // Payment method selection
            $('input[name="methodePaiement"]').change(function() {
                if ($(this).val() === 'CARTE_CREDIT') {
                    $('#carteCreditForm').slideDown(300);
                } else {
                    $('#carteCreditForm').slideUp(300);
                }
            });

            // Card number formatting
            $('#numeroCarte').on('input', function() {
                let value = $(this).val().replace(/\s/g, '').replace(/[^0-9]/gi, '');
                let formattedValue = value.match(/.{1,4}/g)?.join(' ') || value;
                $(this).val(formattedValue);
            });

            // Expiry date formatting
            $('#dateExpiration').on('input', function() {
                let value = $(this).val().replace(/\D/g, '');
                if (value.length >= 2) {
                    value = value.substring(0, 2) + '/' + value.substring(2, 4);
                }
                $(this).val(value);
            });

            // CVV validation
            $('#cvv').on('input', function() {
                let value = $(this).val().replace(/[^0-9]/gi, '');
                $(this).val(value);
            });

            // Form validation
            $('.payment-form').on('submit', function(e) {
                if ($('input[name="methodePaiement"]:checked').val() === 'CARTE_CREDIT') {
                    let isValid = true;

                    if ($('#numeroCarte').val().replace(/\s/g, '').length < 16) {
                        isValid = false;
                        $('#numeroCarte').addClass('is-invalid');
                    } else {
                        $('#numeroCarte').removeClass('is-invalid');
                    }

                    if ($('#dateExpiration').val().length < 5) {
                        isValid = false;
                        $('#dateExpiration').addClass('is-invalid');
                    } else {
                        $('#dateExpiration').removeClass('is-invalid');
                    }

                    if ($('#cvv').val().length < 3) {
                        isValid = false;
                        $('#cvv').addClass('is-invalid');
                    } else {
                        $('#cvv').removeClass('is-invalid');
                    }

                    if ($('#nomCarte').val().trim().length < 2) {
                        isValid = false;
                        $('#nomCarte').addClass('is-invalid');
                    } else {
                        $('#nomCarte').removeClass('is-invalid');
                    }

                    if (!isValid) {
                        e.preventDefault();
                        alert('Veuillez remplir correctement tous les champs de la carte.');
                    }
                }
            });
        });
    </script>
</body>
</html>
