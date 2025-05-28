<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Administration - Gestion des utilisateurs</title>

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
                        <h2><i class="fas fa-users"></i> Gestion des utilisateurs</h2>
                    </div>

                    <div class="card">
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-striped table-hover">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Nom d'utilisateur</th>
                                            <th>Nom</th>
                                            <th>Prénom</th>
                                            <th>Email</th>
                                            <th>Rôle</th>
                                            <th>Statut</th>
                                            <th>Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${users}" var="userItem">
                                            <tr>
                                                <td>${userItem.id}</td>
                                                <td>${userItem.username}</td>
                                                <td>${userItem.nom}</td>
                                                <td>${userItem.prenom}</td>
                                                <td>${userItem.email}</td>
                                                <td>
                                                    <span class="badge ${userItem.role.nom eq 'ADMIN' ? 'badge-danger' : 'badge-primary'}">
                                                        ${userItem.role.nom}
                                                    </span>
                                                </td>
                                                <td>
                                                    <span class="badge ${userItem.actif ? 'badge-success' : 'badge-secondary'}">
                                                        ${userItem.actif ? 'Actif' : 'Bloqué'}
                                                    </span>
                                                </td>
                                                <td>
                                                    <a href="${pageContext.request.contextPath}/admin/users?action=modifier&id=${userItem.id}"
                                                       class="btn btn-sm btn-warning" title="Modifier">
                                                        <i class="fas fa-edit"></i>
                                                    </a>
                                                    <c:choose>
                                                        <c:when test="${userItem.actif}">
                                                            <a href="${pageContext.request.contextPath}/admin/users?action=bloquer&id=${userItem.id}"
                                                               class="btn btn-sm btn-danger" title="Bloquer"
                                                               onclick="return confirm('Êtes-vous sûr de vouloir bloquer cet utilisateur ?');">
                                                                <i class="fas fa-ban"></i>
                                                            </a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <a href="${pageContext.request.contextPath}/admin/users?action=debloquer&id=${userItem.id}"
                                                               class="btn btn-sm btn-success" title="Débloquer"
                                                               onclick="return confirm('Êtes-vous sûr de vouloir débloquer cet utilisateur ?');">
                                                                <i class="fas fa-check"></i>
                                                            </a>
                                                        </c:otherwise>
                                                    </c:choose>
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
