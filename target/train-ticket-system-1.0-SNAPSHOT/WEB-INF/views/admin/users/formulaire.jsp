<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Administration - Modifier un utilisateur</title>

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
                        <h2><i class="fas fa-user-edit"></i> Modifier un utilisateur</h2>
                        <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-secondary">
                            <i class="fas fa-arrow-left"></i> Retour à la liste
                        </a>
                    </div>

                    <form action="${pageContext.request.contextPath}/admin/users" method="post">
                        <input type="hidden" name="action" value="modifier">
                        <input type="hidden" name="id" value="${userToEdit.id}">

                        <!-- Informations personnelles -->
                        <div class="form-section">
                            <h4><i class="fas fa-user"></i> Informations personnelles</h4>

                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label for="username">Nom d'utilisateur</label>
                                    <input type="text" class="form-control" id="username" name="username" value="${userToEdit.username}" required placeholder="Ex: john.doe">
                                </div>

                                <div class="form-group col-md-6">
                                    <label for="email">Email</label>
                                    <input type="email" class="form-control" id="email" name="email" value="${userToEdit.email}" required placeholder="Ex: john.doe@example.com">
                                </div>
                            </div>

                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label for="nom">Nom</label>
                                    <input type="text" class="form-control" id="nom" name="nom" value="${userToEdit.nom}" required placeholder="Ex: Doe">
                                </div>

                                <div class="form-group col-md-6">
                                    <label for="prenom">Prénom</label>
                                    <input type="text" class="form-control" id="prenom" name="prenom" value="${userToEdit.prenom}" required placeholder="Ex: John">
                                </div>
                            </div>
                        </div>

                        <!-- Permissions -->
                        <div class="form-section">
                            <h4><i class="fas fa-shield-alt"></i> Permissions</h4>

                            <div class="form-group">
                                <label for="roleId">Rôle</label>
                                <select class="form-control" id="roleId" name="roleId" required>
                                    <c:forEach items="${roles}" var="role">
                                        <option value="${role.id}" ${userToEdit.role.id eq role.id ? 'selected' : ''}>
                                            ${role.nom}
                                        </option>
                                    </c:forEach>
                                </select>
                                <small class="form-text text-muted">Définit les permissions de l'utilisateur dans le système</small>
                            </div>
                        </div>

                        <!-- Actions -->
                        <div class="form-section">
                            <div class="text-right">
                                <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-secondary mr-2">
                                    <i class="fas fa-times"></i> Annuler
                                </a>
                                <button type="submit" class="btn btn-primary">
                                    <i class="fas fa-save"></i> Enregistrer
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
