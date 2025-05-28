<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sélection du voyage - TrainTicket</title>
    <meta name="description" content="Sélectionnez votre classe et vos préférences pour votre voyage en train.">

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
                    <span class="breadcrumb-item active">
                        Sélection
                    </span>
                </div>
                <h1 class="page-title">
                    <i class="fas fa-ticket-alt"></i>
                    Sélection du voyage
                </h1>
                <p class="page-subtitle">
                    Personnalisez votre voyage en choisissant votre classe et vos préférences
                </p>
            </div>
        </div>
    </section>

    <!-- Main Content -->
    <section class="main-content">
        <div class="container">
            <!-- Voyage Summary -->
            <div class="voyage-overview">
                <div class="voyage-header">
                    <div class="train-badge">
                        <i class="fas fa-train"></i>
                        <span>Train ${voyage.train.numero}</span>
                    </div>
                    <h2 class="voyage-title">${voyage.train.nom}</h2>
                    <div class="voyage-date-badge">
                        <i class="fas fa-calendar"></i>
                        <span>${voyage.date}</span>
                    </div>
                </div>

                <div class="journey-timeline">
                    <div class="timeline-point start">
                        <div class="point-marker">
                            <i class="fas fa-play-circle"></i>
                        </div>
                        <div class="point-details">
                            <h3>${voyage.trajet.gareDepart.ville}</h3>
                            <p class="station">${voyage.trajet.gareDepart.nom}</p>
                            <div class="time">
                                <i class="fas fa-clock"></i>
                                <c:choose>
                                    <c:when test="${voyage.heureDepart != null}">
                                        ${voyage.heureDepart}
                                    </c:when>
                                    <c:otherwise>
                                        Non définie
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>

                    <div class="timeline-connector">
                        <div class="connector-line"></div>
                        <div class="connector-info">
                            <i class="fas fa-arrow-right"></i>
                            <span>Trajet direct</span>
                        </div>
                    </div>

                    <div class="timeline-point end">
                        <div class="point-marker">
                            <i class="fas fa-map-marker-alt"></i>
                        </div>
                        <div class="point-details">
                            <h3>${voyage.trajet.gareArrivee.ville}</h3>
                            <p class="station">${voyage.trajet.gareArrivee.nom}</p>
                            <div class="time">
                                <i class="fas fa-clock"></i>
                                <c:choose>
                                    <c:when test="${voyage.heureArrivee != null}">
                                        ${voyage.heureArrivee}
                                    </c:when>
                                    <c:otherwise>
                                        Non définie
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="price-overview">
                    <span class="price-label">À partir de</span>
                    <span class="price-amount">${voyage.prix} TND</span>
                </div>
            </div>

            <div class="row">
                <!-- Selection Form -->
                <div class="col-lg-8">

                    <!-- Step 1: Class Selection -->
                    <div class="selection-step">
                        <div class="step-header">
                            <div class="step-number">1</div>
                            <div class="step-info">
                                <h3>Choisissez votre classe</h3>
                                <p>Sélectionnez le niveau de confort souhaité</p>
                            </div>
                        </div>

                        <form action="${pageContext.request.contextPath}/selection" method="post" class="selection-form">
                            <input type="hidden" name="voyageId" value="${voyage.id}">

                            <div class="class-selection">
                                <c:forEach items="${classes}" var="classe" varStatus="status">
                                    <div class="class-item">
                                        <input type="radio"
                                               id="classe_${classe.id}"
                                               name="classeId"
                                               value="${classe.id}"
                                               data-coef="${classe.coefficientPrix}"
                                               data-name="${classe.nom}"
                                               ${status.first ? 'checked' : ''}
                                               required>
                                        <label for="classe_${classe.id}" class="class-label">
                                            <div class="class-info">
                                                <div class="class-name">${classe.nom}</div>
                                                <div class="class-price">
                                                    <span class="base-price">${voyage.prix}</span>
                                                    <span class="multiplier">× ${classe.coefficientPrix}</span>
                                                    <span class="final-price">${voyage.prix * classe.coefficientPrix} TND</span>
                                                </div>
                                            </div>
                                            <div class="class-features">
                                                <c:choose>
                                                    <c:when test="${classe.nom eq 'Première'}">
                                                        <span class="feature">Sièges premium</span>
                                                        <span class="feature">WiFi gratuit</span>
                                                        <span class="feature">Collations</span>
                                                    </c:when>
                                                    <c:when test="${classe.nom eq 'Business'}">
                                                        <span class="feature">Espace travail</span>
                                                        <span class="feature">Prises électriques</span>
                                                        <span class="feature">Zone silencieuse</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="feature">Sièges confortables</span>
                                                        <span class="feature">Climatisation</span>
                                                        <span class="feature">Voyage sécurisé</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                        </label>
                                    </div>
                                </c:forEach>
                            </div>
                    </div>

                    <!-- Step 2: Preferences -->
                    <div class="selection-step">
                        <div class="step-header">
                            <div class="step-number">2</div>
                            <div class="step-info">
                                <h3>Préférences de voyage</h3>
                                <p>Options supplémentaires pour votre confort (optionnel)</p>
                            </div>
                        </div>

                        <div class="preferences-selection">
                            <div class="preference-group">
                                <label class="preference-option">
                                    <input type="checkbox" name="preferences" value="fenetre">
                                    <span class="checkmark"></span>
                                    <div class="preference-content">
                                        <i class="fas fa-mountain"></i>
                                        <span>Place côté fenêtre</span>
                                    </div>
                                </label>

                                <label class="preference-option">
                                    <input type="checkbox" name="preferences" value="famille">
                                    <span class="checkmark"></span>
                                    <div class="preference-content">
                                        <i class="fas fa-baby"></i>
                                        <span>Espace famille</span>
                                    </div>
                                </label>

                                <label class="preference-option">
                                    <input type="checkbox" name="preferences" value="nonfumeur">
                                    <span class="checkmark"></span>
                                    <div class="preference-content">
                                        <i class="fas fa-ban-smoking"></i>
                                        <span>Wagon non-fumeur</span>
                                    </div>
                                </label>
                            </div>
                        </div>
                    </div>

                    <!-- Step 3: Return Trip -->
                    <div class="selection-step">
                        <div class="step-header">
                            <div class="step-number">3</div>
                            <div class="step-info">
                                <h3>Voyage de retour</h3>
                                <p>Souhaitez-vous réserver un voyage de retour depuis ${voyage.trajet.gareArrivee.ville} ?</p>
                            </div>
                        </div>

                        <div class="return-selection">
                            <label class="return-option">
                                <input type="radio" name="autreVoyage" value="non" checked>
                                <span class="radio-mark"></span>
                                <div class="return-content">
                                    <i class="fas fa-check-circle"></i>
                                    <div>
                                        <strong>Aller simple</strong>
                                        <p>Continuer avec ce voyage uniquement</p>
                                    </div>
                                </div>
                            </label>

                            <label class="return-option">
                                <input type="radio" name="autreVoyage" value="oui">
                                <span class="radio-mark"></span>
                                <div class="return-content">
                                    <i class="fas fa-exchange-alt"></i>
                                    <div>
                                        <strong>Aller-retour</strong>
                                        <p>Ajouter un voyage de retour</p>
                                    </div>
                                </div>
                            </label>
                        </div>
                    </div>

                            <!-- Form Actions -->
                            <div class="form-actions">
                                <a href="${pageContext.request.contextPath}/recherche" class="btn btn-outline">
                                    <i class="fas fa-arrow-left"></i>
                                    <span>Retour à la recherche</span>
                                </a>
                                <button type="submit" class="btn btn-primary btn-large">
                                    <i class="fas fa-credit-card"></i>
                                    <span>Continuer vers le paiement</span>
                                </button>
                            </div>
                        </form>
                    </div>
                </div>

                <!-- Sidebar -->
                <div class="col-lg-4">
                    <!-- Price Summary -->
                    <div class="summary-card">
                        <div class="summary-header">
                            <h3>Votre sélection</h3>
                        </div>
                        <div class="summary-content">
                            <div class="selected-class">
                                <div class="class-display">
                                    <span class="class-name-display" id="selectedClassName">Économique</span>
                                    <span class="class-price-display" id="selectedClassPrice">${voyage.prix} TND</span>
                                </div>
                            </div>

                            <div class="selected-preferences">
                                <h4>Préférences sélectionnées</h4>
                                <div class="preferences-list" id="selectedPreferences">
                                    <span class="no-preferences">Aucune préférence sélectionnée</span>
                                </div>
                            </div>

                            <div class="selected-return">
                                <h4>Type de voyage</h4>
                                <div class="return-type" id="selectedReturnType">
                                    <i class="fas fa-arrow-right"></i>
                                    <span>Aller simple</span>
                                </div>
                            </div>

                            <div class="total-price">
                                <div class="price-row">
                                    <span>Total à payer</span>
                                    <span class="total-amount" id="totalPrice">${voyage.prix} TND</span>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Quick Info -->
                    <div class="info-card">
                        <div class="info-header">
                            <h4>Informations importantes</h4>
                        </div>
                        <div class="info-content">
                            <div class="info-item">
                                <i class="fas fa-clock text-primary"></i>
                                <span>Arrivez 15 min avant le départ</span>
                            </div>
                            <div class="info-item">
                                <i class="fas fa-id-card text-primary"></i>
                                <span>Pièce d'identité obligatoire</span>
                            </div>
                            <div class="info-item">
                                <i class="fas fa-mobile-alt text-primary"></i>
                                <span>Billet envoyé par email</span>
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
        document.addEventListener('DOMContentLoaded', function() {
            const prixBase = ${voyage.prix};

            // Elements
            const classeInputs = document.querySelectorAll('input[name="classeId"]');
            const preferencesInputs = document.querySelectorAll('input[name="preferences"]');
            const returnInputs = document.querySelectorAll('input[name="autreVoyage"]');

            // Display elements
            const selectedClassName = document.getElementById('selectedClassName');
            const selectedClassPrice = document.getElementById('selectedClassPrice');
            const selectedPreferences = document.getElementById('selectedPreferences');
            const selectedReturnType = document.getElementById('selectedReturnType');
            const totalPrice = document.getElementById('totalPrice');

            // Update class selection
            function updateClassSelection() {
                const selectedClasse = document.querySelector('input[name="classeId"]:checked');
                if (selectedClasse) {
                    const coef = parseFloat(selectedClasse.dataset.coef);
                    const name = selectedClasse.dataset.name;
                    const price = (prixBase * coef).toFixed(2);

                    selectedClassName.textContent = name;
                    selectedClassPrice.textContent = price + ' TND';
                    totalPrice.textContent = price + ' TND';
                }
            }

            // Update preferences selection
            function updatePreferencesSelection() {
                const checkedPreferences = document.querySelectorAll('input[name="preferences"]:checked');

                if (checkedPreferences.length === 0) {
                    selectedPreferences.innerHTML = '<span class="no-preferences">Aucune préférence sélectionnée</span>';
                } else {
                    const preferencesList = Array.from(checkedPreferences).map(input => {
                        const label = input.parentElement.querySelector('span:last-child').textContent;
                        return '<span class="preference-tag">' + label + '</span>';
                    }).join('');
                    selectedPreferences.innerHTML = preferencesList;
                }
            }

            // Update return trip selection
            function updateReturnSelection() {
                const selectedReturn = document.querySelector('input[name="autreVoyage"]:checked');
                if (selectedReturn) {
                    if (selectedReturn.value === 'oui') {
                        selectedReturnType.innerHTML = '<i class="fas fa-exchange-alt"></i><span>Aller-retour</span>';
                    } else {
                        selectedReturnType.innerHTML = '<i class="fas fa-arrow-right"></i><span>Aller simple</span>';
                    }
                }
            }

            // Event listeners
            classeInputs.forEach(input => {
                input.addEventListener('change', updateClassSelection);
            });

            preferencesInputs.forEach(input => {
                input.addEventListener('change', updatePreferencesSelection);
            });

            returnInputs.forEach(input => {
                input.addEventListener('change', updateReturnSelection);
            });

            // Initial updates
            updateClassSelection();
            updatePreferencesSelection();
            updateReturnSelection();
        });
    </script>
</body>
</html>
