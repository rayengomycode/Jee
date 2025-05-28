<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Administration - Tableau de bord</title>

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

    <style>
        body {
            background: linear-gradient(135deg, #f8fafc 0%, #e2e8f0 50%, #f1f5f9 100%);
            font-family: 'Inter', sans-serif;
            padding-top: 0 !important;
        }

        /* Header adjustments for admin */
        .navbar {
            position: sticky;
            top: 0;
            z-index: 1000;
        }

        .admin-content {
            padding: 2rem 1rem;
            margin-top: 0;
        }

        .container-fluid {
            padding: 0;
        }

        .admin-header {
            background: rgba(255, 255, 255, 0.9);
            backdrop-filter: blur(10px);
            padding: 1.5rem;
            border-radius: 15px;
            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.08);
            border: 1px solid rgba(226, 232, 240, 0.8);
        }

        .admin-header h2 {
            color: #1f2937;
            font-weight: 700;
            margin-bottom: 1rem;
            font-size: 2rem;
        }

        .admin-header h2 i {
            color: #6366f1;
            margin-right: 0.75rem;
        }

        .admin-header hr {
            border: none;
            height: 3px;
            background: linear-gradient(135deg, #6366f1 0%, #8b5cf6 50%, #ec4899 100%);
            border-radius: 3px;
            margin: 1rem 0 0 0;
        }

        .card {
            border: none;
            border-radius: 15px;
            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.08);
            transition: all 0.3s ease;
            overflow: hidden;
        }

        .card:hover {
            transform: translateY(-3px);
            box-shadow: 0 15px 40px rgba(0, 0, 0, 0.15);
        }

        .card-body {
            padding: 1.5rem;
        }

        .card-title {
            font-size: 0.9rem;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 0.5px;
            margin-bottom: 0.5rem;
        }

        .card h2 {
            font-size: 2.5rem;
            font-weight: 800;
            margin: 0;
        }

        .card i.fa-3x {
            opacity: 0.8;
            transition: all 0.3s ease;
        }

        .card:hover i.fa-3x {
            opacity: 1;
            transform: scale(1.1);
        }

        .card-footer {
            padding: 1rem 1.5rem;
            border-top: 1px solid rgba(255, 255, 255, 0.2);
        }

        .card-footer a {
            font-weight: 500;
            transition: all 0.3s ease;
            text-decoration: none;
        }

        .card-footer a:hover {
            text-decoration: none;
            transform: translateX(3px);
        }

        .bg-gradient-primary {
            background: linear-gradient(135deg, #6366f1 0%, #8b5cf6 100%) !important;
        }

        .table {
            border: none;
        }

        .table thead th {
            border: none;
            background: #f8fafc;
            font-weight: 600;
            color: #374151;
            padding: 1rem;
            font-size: 0.9rem;
        }

        .table tbody td {
            border: none;
            padding: 1rem;
            vertical-align: middle;
            border-bottom: 1px solid #f3f4f6;
        }

        .table tbody tr:hover {
            background: #f8fafc;
        }

        @media (max-width: 768px) {
            .admin-content {
                padding: 1rem 0.5rem;
            }

            h2 {
                font-size: 1.8rem;
            }

            .card h2 {
                font-size: 2rem;
            }

            .card i.fa-3x {
                font-size: 2rem !important;
            }
        }
    </style>
</head>
<body>
    <jsp:include page="header.jsp" />

    <div class="container-fluid">
        <div class="admin-content">
            <div class="row">
                <div class="col-md-3">
                    <jsp:include page="sidebar.jsp" />
                </div>

                <div class="col-md-9">
                    <div class="admin-header mb-4">
                        <h2><i class="fas fa-tachometer-alt"></i> Tableau de bord</h2>
                        <hr>
                    </div>

                <div class="row">
                    <div class="col-md-4 mb-4">
                        <div class="card bg-primary text-white">
                            <div class="card-body">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div>
                                        <h6 class="card-title">Utilisateurs</h6>
                                        <h2 class="mb-0">${totalUsers}</h2>
                                    </div>
                                    <i class="fas fa-users fa-3x"></i>
                                </div>
                            </div>
                            <div class="card-footer bg-transparent border-0">
                                <a href="${pageContext.request.contextPath}/admin/users" class="text-white">Voir détails <i class="fas fa-arrow-right"></i></a>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-4 mb-4">
                        <div class="card bg-success text-white">
                            <div class="card-body">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div>
                                        <h6 class="card-title">Trains</h6>
                                        <h2 class="mb-0">${totalTrains}</h2>
                                    </div>
                                    <i class="fas fa-train fa-3x"></i>
                                </div>
                            </div>
                            <div class="card-footer bg-transparent border-0">
                                <a href="${pageContext.request.contextPath}/admin/trains" class="text-white">Voir détails <i class="fas fa-arrow-right"></i></a>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-4 mb-4">
                        <div class="card bg-info text-white">
                            <div class="card-body">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div>
                                        <h6 class="card-title">Gares</h6>
                                        <h2 class="mb-0">${totalGares}</h2>
                                    </div>
                                    <i class="fas fa-building fa-3x"></i>
                                </div>
                            </div>
                            <div class="card-footer bg-transparent border-0">
                                <a href="${pageContext.request.contextPath}/admin/gares" class="text-white">Voir détails <i class="fas fa-arrow-right"></i></a>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-4 mb-4">
                        <div class="card bg-warning text-white">
                            <div class="card-body">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div>
                                        <h6 class="card-title">Trajets</h6>
                                        <h2 class="mb-0">${totalTrajets}</h2>
                                    </div>
                                    <i class="fas fa-route fa-3x"></i>
                                </div>
                            </div>
                            <div class="card-footer bg-transparent border-0">
                                <a href="${pageContext.request.contextPath}/admin/trajets" class="text-white">Voir détails <i class="fas fa-arrow-right"></i></a>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-4 mb-4">
                        <div class="card bg-danger text-white">
                            <div class="card-body">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div>
                                        <h6 class="card-title">Voyages</h6>
                                        <h2 class="mb-0">${totalVoyages}</h2>
                                    </div>
                                    <i class="fas fa-calendar-alt fa-3x"></i>
                                </div>
                            </div>
                            <div class="card-footer bg-transparent border-0">
                                <a href="${pageContext.request.contextPath}/admin/voyages" class="text-white">Voir détails <i class="fas fa-arrow-right"></i></a>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-4 mb-4">
                        <div class="card bg-secondary text-white">
                            <div class="card-body">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div>
                                        <h6 class="card-title">Billets</h6>
                                        <h2 class="mb-0">${totalBillets}</h2>
                                    </div>
                                    <i class="fas fa-ticket-alt fa-3x"></i>
                                </div>
                            </div>
                            <div class="card-footer bg-transparent border-0">
                                <a href="${pageContext.request.contextPath}/admin/billets" class="text-white">Voir détails <i class="fas fa-arrow-right"></i></a>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Accès rapide aux statistiques -->
                <div class="row mt-4">
                    <div class="col-md-12">
                        <div class="card bg-gradient-primary text-white">
                            <div class="card-body">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div>
                                        <h5 class="card-title mb-2">
                                            <i class="fas fa-chart-bar"></i> Statistiques et Rapports
                                        </h5>
                                        <p class="card-text">
                                            Consultez les statistiques détaillées, les rapports de ventes et l'analyse des performances
                                        </p>
                                    </div>
                                    <div class="text-right">
                                        <a href="${pageContext.request.contextPath}/admin/statistiques"
                                           class="btn btn-light btn-lg">
                                            <i class="fas fa-chart-line"></i> Voir les Statistiques
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="card mt-4">
                    <div class="card-header bg-primary text-white">
                        <h5 class="mb-0"><i class="fas fa-ticket-alt"></i> Derniers billets achetés</h5>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-striped table-hover">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Utilisateur</th>
                                        <th>Voyage</th>
                                        <th>Date d'achat</th>
                                        <th>Prix</th>
                                        <th>État</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${derniersBillets}" var="billet">
                                        <tr>
                                            <td>${billet.id}</td>
                                            <td>${billet.user.prenom} ${billet.user.nom}</td>
                                            <td>${billet.voyage.trajet.gareDepart.ville} → ${billet.voyage.trajet.gareArrivee.ville}</td>
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
                                            <td>${billet.prix} TND</td>
                                            <td>${billet.etat}</td>
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

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // Add hover effects to cards
            document.querySelectorAll('.card').forEach(card => {
                card.addEventListener('mouseenter', function() {
                    this.style.transform = 'translateY(-3px)';
                    this.style.boxShadow = '0 8px 25px rgba(0, 0, 0, 0.15)';
                });

                card.addEventListener('mouseleave', function() {
                    this.style.transform = 'translateY(0)';
                    this.style.boxShadow = '';
                });
            });
        });
    </script>
</body>
</html>
