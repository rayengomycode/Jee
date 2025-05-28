<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Administration - Gestion des trajets</title>

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
                        <h2><i class="fas fa-route"></i> Gestion des trajets</h2>
                        <a href="${pageContext.request.contextPath}/admin/trajets?action=ajouter" class="btn btn-primary">
                            <i class="fas fa-plus"></i> Ajouter un trajet
                        </a>
                    </div>

                    <div class="card">
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-striped table-hover">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Code</th>
                                            <th>Départ</th>
                                            <th>Arrivée</th>
                                            <th>Direct</th>
                                            <th>Gares</th>
                                            <th>Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${trajets}" var="trajet">
                                            <tr>
                                                <td>${trajet.id}</td>
                                                <td>${trajet.code}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${trajet.gareDepart != null}">
                                                            ${trajet.gareDepart.nom} (${trajet.gareDepart.ville})
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="text-muted">Non définie</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${trajet.gareArrivee != null}">
                                                            ${trajet.gareArrivee.nom} (${trajet.gareArrivee.ville})
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="text-muted">Non définie</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <c:if test="${trajet.direct}">
                                                        <span class="badge badge-success">Oui</span>
                                                    </c:if>
                                                    <c:if test="${!trajet.direct}">
                                                        <span class="badge badge-secondary">Non</span>
                                                    </c:if>
                                                </td>
                                                <td>${trajet.gareTrajets.size()}</td>
                                                <td>
                                                    <a href="${pageContext.request.contextPath}/admin/trajets?action=modifier&id=${trajet.id}" class="btn btn-sm btn-warning">
                                                        <i class="fas fa-edit"></i>
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/admin/trajets?action=supprimer&id=${trajet.id}" class="btn btn-sm btn-danger" onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce trajet?')">
                                                        <i class="fas fa-trash"></i>
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

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
