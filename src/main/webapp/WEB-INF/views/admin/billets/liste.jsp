<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Administration - Gestion des billets</title>

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
                        <h2><i class="fas fa-ticket-alt"></i> Gestion des billets</h2>
                    </div>

                    <div class="card">
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-striped table-hover">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Utilisateur</th>
                                            <th>Voyage</th>
                                            <th>Classe</th>
                                            <th>Prix</th>
                                            <th>Date d'achat</th>
                                            <th>État</th>
                                            <th>Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${billets}" var="billet">
                                            <tr>
                                                <td>${billet.id}</td>
                                                <td>${billet.user.prenom} ${billet.user.nom}</td>
                                                <td>
                                                    ${billet.voyage.trajet.gareDepart.ville} → ${billet.voyage.trajet.gareArrivee.ville}
                                                    <br>
                                                    <small class="text-muted">
                                                        <c:choose>
                                                            <c:when test="${billet.voyage.date != null}">
                                                                <c:set var="day" value="${billet.voyage.date.dayOfMonth}" />
                                                                <c:set var="month" value="${billet.voyage.date.monthValue}" />
                                                                <c:set var="year" value="${billet.voyage.date.year}" />
                                                                <c:if test="${day < 10}">0</c:if>${day}/<c:if test="${month < 10}">0</c:if>${month}/${year}
                                                            </c:when>
                                                            <c:otherwise>
                                                                Date non définie
                                                            </c:otherwise>
                                                        </c:choose>
                                                        à
                                                        <c:choose>
                                                            <c:when test="${billet.voyage.heureDepart != null}">
                                                                ${billet.voyage.heureDepart}
                                                            </c:when>
                                                            <c:otherwise>
                                                                Heure non définie
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </small>
                                                </td>
                                                <td>${billet.classe.nom}</td>
                                                <td>${billet.prix} TND</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${billet.dateAchat != null}">
                                                            <c:set var="day" value="${billet.dateAchat.dayOfMonth}" />
                                                            <c:set var="month" value="${billet.dateAchat.monthValue}" />
                                                            <c:set var="year" value="${billet.dateAchat.year}" />
                                                            <c:set var="hour" value="${billet.dateAchat.hour}" />
                                                            <c:set var="minute" value="${billet.dateAchat.minute}" />
                                                            <c:if test="${day < 10}">0</c:if>${day}/<c:if test="${month < 10}">0</c:if>${month}/${year} <c:if test="${hour < 10}">0</c:if>${hour}:<c:if test="${minute < 10}">0</c:if>${minute}
                                                        </c:when>
                                                        <c:otherwise>
                                                            Non définie
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${billet.etat eq 'ACHETE'}">
                                                            <span class="badge badge-success">Acheté</span>
                                                        </c:when>
                                                        <c:when test="${billet.etat eq 'UTILISE'}">
                                                            <span class="badge badge-secondary">Utilisé</span>
                                                        </c:when>
                                                        <c:when test="${billet.etat eq 'ANNULE'}">
                                                            <span class="badge badge-danger">Annulé</span>
                                                        </c:when>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <c:if test="${billet.etat eq 'ACHETE'}">
                                                        <a href="${pageContext.request.contextPath}/admin/billets?action=approuverAnnulation&id=${billet.id}" class="btn btn-sm btn-danger" onclick="return confirm('Êtes-vous sûr de vouloir annuler ce billet?')">
                                                            <i class="fas fa-times"></i> Annuler
                                                        </a>
                                                    </c:if>
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
