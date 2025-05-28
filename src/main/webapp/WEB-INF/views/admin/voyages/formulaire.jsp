<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Administration - ${empty voyage ? 'Ajouter' : 'Modifier'} un voyage</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css">

    <!-- Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">

    <!-- Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <!-- Custom Styles -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/navbar.css">

    <jsp:include page="../admin-template.jsp" />

    <style>
        .form-section {
            background: white;
            border-radius: 15px;
            padding: 2rem;
            margin-bottom: 2rem;
            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.08);
            border: 1px solid rgba(226, 232, 240, 0.8);
        }

        .form-section h4 {
            color: #1f2937;
            font-weight: 600;
            margin-bottom: 1.5rem;
            display: flex;
            align-items: center;
            gap: 0.75rem;
        }

        .form-section h4 i {
            color: #6366f1;
        }

        .form-control {
            border-radius: 8px;
            border: 1px solid #d1d5db;
            transition: all 0.3s ease;
            padding: 0.75rem 1rem;
        }

        .form-control:focus {
            border-color: #6366f1;
            box-shadow: 0 0 0 3px rgba(99, 102, 241, 0.1);
        }

        .form-group label {
            font-weight: 500;
            color: #374151;
            margin-bottom: 0.5rem;
        }

        .btn {
            border-radius: 8px;
            font-weight: 500;
            transition: all 0.3s ease;
            padding: 0.75rem 1.5rem;
        }

        .btn-primary {
            background: linear-gradient(135deg, #6366f1, #8b5cf6);
            border: none;
            box-shadow: 0 4px 15px rgba(99, 102, 241, 0.3);
        }

        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 25px rgba(99, 102, 241, 0.4);
        }

        .btn-secondary {
            background: linear-gradient(135deg, #6b7280, #4b5563);
            border: none;
        }
    </style>
</head>
<body>
    <jsp:include page="../header.jsp" />

    <div class="container-fluid">
        <div class="admin-content">
            <div class="row">
                <div class="col-md-3">
                    <jsp:include page="../sidebar.jsp" />
                </div>

                <div class="col-md-9">
                    <div class="admin-header">
                        <h2><i class="fas fa-calendar-alt"></i> ${empty voyage ? 'Ajouter' : 'Modifier'} un voyage</h2>
                        <a href="${pageContext.request.contextPath}/admin/voyages" class="btn btn-secondary">
                            <i class="fas fa-arrow-left"></i> Retour à la liste
                        </a>
                    </div>

                    <form action="${pageContext.request.contextPath}/admin/voyages" method="post">
                        <input type="hidden" name="action" value="${empty voyage ? 'ajouter' : 'modifier'}">
                        <c:if test="${not empty voyage}">
                            <input type="hidden" name="id" value="${voyage.id}">
                        </c:if>

                        <!-- Sélection trajet et train -->
                        <div class="form-section">
                            <h4><i class="fas fa-route"></i> Trajet et Train</h4>

                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label for="trajetId">Trajet</label>
                                    <select class="form-control" id="trajetId" name="trajetId" required>
                                        <option value="">Sélectionner un trajet</option>
                                        <c:forEach items="${trajets}" var="trajet">
                                            <option value="${trajet.id}" ${voyage.trajet.id eq trajet.id ? 'selected' : ''}>
                                                ${trajet.code} - ${trajet.gareDepart.ville} → ${trajet.gareArrivee.ville}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="form-group col-md-6">
                                    <label for="trainId">Train</label>
                                    <select class="form-control" id="trainId" name="trainId" required>
                                        <option value="">Sélectionner un train</option>
                                        <c:forEach items="${trains}" var="train">
                                            <option value="${train.id}" ${voyage.train.id eq train.id ? 'selected' : ''}>
                                                ${train.numero} - ${train.nom} (Capacité: ${train.capaciteTotal})
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>

                        <!-- Horaires -->
                        <div class="form-section">
                            <h4><i class="fas fa-clock"></i> Horaires</h4>

                            <div class="form-row">
                                <div class="form-group col-md-4">
                                    <label for="date">Date</label>
                                    <input type="date" class="form-control" id="date" name="date" value="${voyage.date}" required>
                                </div>

                                <div class="form-group col-md-4">
                                    <label for="heureDepart">Heure de départ</label>
                                    <input type="time" class="form-control" id="heureDepart" name="heureDepart" value="${voyage.heureDepart}" required>
                                </div>

                                <div class="form-group col-md-4">
                                    <label for="heureArrivee">Heure d'arrivée</label>
                                    <input type="time" class="form-control" id="heureArrivee" name="heureArrivee" value="${voyage.heureArrivee}" required>
                                </div>
                            </div>
                        </div>

                        <!-- Tarification et places -->
                        <div class="form-section">
                            <h4><i class="fas fa-euro-sign"></i> Tarification et Places</h4>

                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label for="prix">Prix (TND)</label>
                                    <input type="number" class="form-control" id="prix" name="prix" value="${voyage.prix}" min="0" step="0.01" required placeholder="Ex: 25.50">
                                    <small class="form-text text-muted">Prix du billet en dinars tunisiens</small>
                                </div>

                                <div class="form-group col-md-6">
                                    <label for="placesDisponibles">Places disponibles</label>
                                    <input type="number" class="form-control" id="placesDisponibles" name="placesDisponibles" value="${voyage.placesDisponibles}" min="0" required placeholder="Ex: 300">
                                    <small class="form-text text-muted">Nombre de places disponibles pour ce voyage</small>
                                </div>
                            </div>
                        </div>

                        <!-- Actions -->
                        <div class="form-section">
                            <div class="text-right">
                                <a href="${pageContext.request.contextPath}/admin/voyages" class="btn btn-secondary mr-2">
                                    <i class="fas fa-times"></i> Annuler
                                </a>
                                <button type="submit" class="btn btn-primary">
                                    <i class="fas fa-save"></i> ${empty voyage ? 'Ajouter' : 'Modifier'}
                                </button>
                            </div>
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </div>

    <!-- Scripts -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/app.js"></script>
</body>
</html>
