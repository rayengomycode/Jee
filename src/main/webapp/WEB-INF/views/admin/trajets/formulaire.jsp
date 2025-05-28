<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Administration - ${empty trajet ? 'Ajouter' : 'Modifier'} un trajet</title>

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

        .gare-item {
            border: 1px solid #e5e7eb;
            border-radius: 12px;
            margin-bottom: 1rem;
            transition: all 0.3s ease;
        }

        .gare-item:hover {
            border-color: #6366f1;
            box-shadow: 0 4px 15px rgba(99, 102, 241, 0.1);
        }

        .form-control {
            border-radius: 8px;
            border: 1px solid #d1d5db;
            transition: all 0.3s ease;
        }

        .form-control:focus {
            border-color: #6366f1;
            box-shadow: 0 0 0 3px rgba(99, 102, 241, 0.1);
        }

        .btn {
            border-radius: 8px;
            font-weight: 500;
            transition: all 0.3s ease;
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

        .btn-info {
            background: linear-gradient(135deg, #06b6d4, #0891b2);
            border: none;
        }

        .btn-danger {
            background: linear-gradient(135deg, #ef4444, #dc2626);
            border: none;
        }

        .btn-secondary {
            background: linear-gradient(135deg, #6b7280, #4b5563);
            border: none;
        }

        .alert-info {
            background: linear-gradient(135deg, rgba(6, 182, 212, 0.1), rgba(8, 145, 178, 0.1));
            border: 1px solid rgba(6, 182, 212, 0.2);
            border-radius: 12px;
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
                        <h2><i class="fas fa-route"></i> ${empty trajet ? 'Ajouter' : 'Modifier'} un trajet</h2>
                        <a href="${pageContext.request.contextPath}/admin/trajets" class="btn btn-secondary">
                            <i class="fas fa-arrow-left"></i> Retour à la liste
                        </a>
                    </div>

                    <form action="${pageContext.request.contextPath}/admin/trajets" method="post">
                        <input type="hidden" name="action" value="${empty trajet ? 'ajouter' : 'modifier'}">
                        <c:if test="${not empty trajet}">
                            <input type="hidden" name="id" value="${trajet.id}">
                        </c:if>

                        <!-- Informations générales -->
                        <div class="form-section">
                            <h4><i class="fas fa-info-circle"></i> Informations générales</h4>

                            <div class="form-group">
                                <label for="code">Code du trajet</label>
                                <input type="text" class="form-control" id="code" name="code" value="${trajet.code}" required>
                            </div>

                            <div class="form-group form-check">
                                <input type="checkbox" class="form-check-input" id="direct" name="direct" ${trajet.direct ? 'checked' : ''}>
                                <label class="form-check-label" for="direct">Trajet direct (sans arrêt aux gares intermédiaires)</label>
                            </div>
                        </div>

                        <!-- Gares du trajet -->
                        <div class="form-section">
                            <h4><i class="fas fa-map-marker-alt"></i> Gares du trajet</h4>
                            <div class="alert alert-info">
                                <i class="fas fa-info-circle"></i> Ajoutez les gares dans l'ordre du trajet. La première gare est le départ, la dernière est l'arrivée.
                            </div>

                                <c:if test="${empty trajet.gareTrajets}">
                                    <div class="gare-item card mb-3">
                                        <div class="card-body">
                                            <div class="form-row">
                                                <div class="form-group col-md-4">
                                                    <label>Gare</label>
                                                    <select class="form-control" name="gareId" required>
                                                        <option value="">Sélectionner une gare</option>
                                                        <c:forEach items="${gares}" var="gare">
                                                            <option value="${gare.id}">${gare.nom} (${gare.ville})</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <div class="form-group col-md-2">
                                                    <label>Ordre</label>
                                                    <input type="number" class="form-control" name="ordre" value="1" min="1" required>
                                                </div>
                                                <div class="form-group col-md-3">
                                                    <label>Temps d'arrêt (min)</label>
                                                    <input type="number" class="form-control" name="tempsArret" value="0" min="0" required>
                                                </div>
                                                <div class="form-group col-md-3">
                                                    <label>Temps parcours (min)</label>
                                                    <input type="number" class="form-control" name="tempsParcours" value="0" min="0" required>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>

                                <c:forEach items="${trajet.gareTrajets}" var="gareTrajet" varStatus="status">
                                    <div class="gare-item card mb-3">
                                        <div class="card-body">
                                            <div class="form-row">
                                                <div class="form-group col-md-4">
                                                    <label>Gare</label>
                                                    <select class="form-control" name="gareId" required>
                                                        <c:forEach items="${gares}" var="gare">
                                                            <option value="${gare.id}" ${gare.id eq gareTrajet.gare.id ? 'selected' : ''}>${gare.nom} (${gare.ville})</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <div class="form-group col-md-2">
                                                    <label>Ordre</label>
                                                    <input type="number" class="form-control" name="ordre" value="${gareTrajet.ordre}" min="1" required>
                                                </div>
                                                <div class="form-group col-md-3">
                                                    <label>Temps d'arrêt (min)</label>
                                                    <input type="number" class="form-control" name="tempsArret" value="${gareTrajet.tempsArret.toMinutes()}" min="0" required>
                                                </div>
                                                <div class="form-group col-md-3">
                                                    <label>Temps parcours (min)</label>
                                                    <input type="number" class="form-control" name="tempsParcours" value="${gareTrajet.tempsParcours.toMinutes()}" min="0" required>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>

                            <div class="text-center mb-4">
                                <button type="button" id="add-gare" class="btn btn-info">
                                    <i class="fas fa-plus-circle"></i> Ajouter une gare
                                </button>
                            </div>
                        </div>

                        <!-- Actions -->
                        <div class="form-section">
                            <div class="text-right">
                                <a href="${pageContext.request.contextPath}/admin/trajets" class="btn btn-secondary mr-2">
                                    <i class="fas fa-times"></i> Annuler
                                </a>
                                <button type="submit" class="btn btn-primary">
                                    <i class="fas fa-save"></i> ${empty trajet ? 'Ajouter' : 'Modifier'}
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

    <script>
        $(document).ready(function() {
            // Ajouter une nouvelle gare
            $('#add-gare').click(function() {
                var gareCount = $('.gare-item').length;
                var gareTemplate = `
                    <div class="gare-item card mb-3">
                        <div class="card-body">
                            <div class="form-row">
                                <div class="form-group col-md-4">
                                    <label>Gare</label>
                                    <select class="form-control" name="gareId" required>
                                        <option value="">Sélectionner une gare</option>
                                        <c:forEach items="${gares}" var="gare">
                                            <option value="${gare.id}">${gare.nom} (${gare.ville})</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group col-md-2">
                                    <label>Ordre</label>
                                    <input type="number" class="form-control" name="ordre" value="${gareCount + 1}" min="1" required>
                                </div>
                                <div class="form-group col-md-3">
                                    <label>Temps d'arrêt (min)</label>
                                    <input type="number" class="form-control" name="tempsArret" value="0" min="0" required>
                                </div>
                                <div class="form-group col-md-3">
                                    <label>Temps parcours (min)</label>
                                    <input type="number" class="form-control" name="tempsParcours" value="0" min="0" required>
                                </div>
                            </div>
                            <button type="button" class="btn btn-sm btn-danger remove-gare">
                                <i class="fas fa-trash"></i> Supprimer
                            </button>
                        </div>
                    </div>
                `;
                $('#gares-container').append(gareTemplate);
            });

            // Supprimer une gare
            $(document).on('click', '.remove-gare', function() {
                $(this).closest('.gare-item').remove();
            });
        });
    </script>
</body>
</html>
