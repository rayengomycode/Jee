<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Statistiques - Administration</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css">

    <!-- Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">

    <!-- Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <!-- Chart.js -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

    <!-- Custom Styles -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/navbar.css">

    <jsp:include page="admin-template.jsp" />

    <style>
        .stat-card {
            background: linear-gradient(135deg, #6366f1 0%, #8b5cf6 100%);
            color: white;
            border-radius: 15px;
            padding: 1.5rem;
            margin-bottom: 1.5rem;
            box-shadow: 0 8px 25px rgba(99, 102, 241, 0.3);
            transition: all 0.3s ease;
        }

        .stat-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 15px 40px rgba(99, 102, 241, 0.4);
        }

        .stat-card.success {
            background: linear-gradient(135deg, #10b981 0%, #047857 100%);
            box-shadow: 0 8px 25px rgba(16, 185, 129, 0.3);
        }

        .stat-card.warning {
            background: linear-gradient(135deg, #f59e0b 0%, #d97706 100%);
            box-shadow: 0 8px 25px rgba(245, 158, 11, 0.3);
        }

        .stat-card.info {
            background: linear-gradient(135deg, #06b6d4 0%, #0891b2 100%);
            box-shadow: 0 8px 25px rgba(6, 182, 212, 0.3);
        }

        .stat-card.danger {
            background: linear-gradient(135deg, #ef4444 0%, #dc2626 100%);
            box-shadow: 0 8px 25px rgba(239, 68, 68, 0.3);
        }

        .stat-number {
            font-size: 2.5rem;
            font-weight: 800;
            margin-bottom: 0.5rem;
        }

        .stat-label {
            font-size: 0.9rem;
            opacity: 0.9;
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }

        .chart-container {
            position: relative;
            height: 300px;
            margin: 20px 0;
        }

        .table-stats {
            background: white;
            border-radius: 15px;
            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.08);
            border: 1px solid rgba(226, 232, 240, 0.8);
            transition: all 0.3s ease;
        }

        .table-stats:hover {
            transform: translateY(-3px);
            box-shadow: 0 15px 40px rgba(0, 0, 0, 0.15);
        }

        .table-stats .card-header {
            background: var(--gray-50);
            border-bottom: 1px solid var(--gray-200);
            border-radius: 15px 15px 0 0;
            padding: 1.5rem;
        }

        .table-stats .card-header h5 {
            margin: 0;
            font-weight: 600;
            color: var(--gray-700);
            display: flex;
            align-items: center;
            gap: 0.75rem;
        }

        .table-stats .card-header h5 i {
            color: var(--primary-color);
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
                    <div class="admin-header">
                        <h2><i class="fas fa-chart-bar"></i> Statistiques et Rapports</h2>
                        <button class="btn btn-primary" onclick="window.print()">
                            <i class="fas fa-print"></i> Imprimer
                        </button>
                    </div>

                <c:if test="${not empty error}">
                    <div class="alert alert-danger">${error}</div>
                </c:if>

                    <!-- Statistiques générales -->
                    <div class="row mb-4">
                        <div class="col-md-2">
                            <div class="stat-card">
                                <div class="stat-number">${generalStats.totalBillets}</div>
                                <div class="stat-label"><i class="fas fa-ticket-alt"></i> Total Billets</div>
                            </div>
                        </div>
                        <div class="col-md-2">
                            <div class="stat-card success">
                                <div class="stat-number">${generalStats.totalVoyages}</div>
                                <div class="stat-label"><i class="fas fa-train"></i> Voyages</div>
                            </div>
                        </div>
                        <div class="col-md-2">
                            <div class="stat-card warning">
                                <div class="stat-number">${generalStats.totalUtilisateurs}</div>
                                <div class="stat-label"><i class="fas fa-users"></i> Utilisateurs</div>
                            </div>
                        </div>
                        <div class="col-md-2">
                            <div class="stat-card info">
                                <div class="stat-number">${generalStats.totalTrajets}</div>
                                <div class="stat-label"><i class="fas fa-route"></i> Trajets</div>
                            </div>
                        </div>
                        <div class="col-md-2">
                            <div class="stat-card danger">
                                <div class="stat-number">${generalStats.totalGares}</div>
                                <div class="stat-label"><i class="fas fa-map-marker-alt"></i> Gares</div>
                            </div>
                        </div>
                        <div class="col-md-2">
                            <div class="stat-card success">
                                <div class="stat-number">
                                    <fmt:formatNumber value="${financialStats.revenusTotal}" pattern="#,##0.00"/>
                                </div>
                                <div class="stat-label"><i class="fas fa-euro-sign"></i> Revenus (TND)</div>
                            </div>
                        </div>
                    </div>

                <!-- État des billets -->
                <div class="row mb-4">
                    <div class="col-md-4">
                        <div class="card table-stats">
                            <div class="card-header">
                                <h5><i class="fas fa-chart-pie"></i> État des Billets</h5>
                            </div>
                            <div class="card-body">
                                <canvas id="billetsChart"></canvas>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="card table-stats">
                            <div class="card-header">
                                <h5><i class="fas fa-chart-bar"></i> Ventes par Classe</h5>
                            </div>
                            <div class="card-body">
                                <canvas id="classesChart"></canvas>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="card table-stats">
                            <div class="card-header">
                                <h5><i class="fas fa-users"></i> Utilisateurs</h5>
                            </div>
                            <div class="card-body">
                                <canvas id="usersChart"></canvas>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Ventes par jour -->
                <div class="row mb-4">
                    <div class="col-md-8">
                        <div class="card table-stats">
                            <div class="card-header">
                                <h5><i class="fas fa-chart-line"></i> Évolution des Ventes (7 derniers jours)</h5>
                            </div>
                            <div class="card-body">
                                <canvas id="ventesChart"></canvas>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="card table-stats">
                            <div class="card-header">
                                <h5><i class="fas fa-calculator"></i> Résumé Financier</h5>
                            </div>
                            <div class="card-body">
                                <table class="table table-sm">
                                    <tr>
                                        <td><strong>Revenus Total:</strong></td>
                                        <td class="text-right">
                                            <fmt:formatNumber value="${financialStats.revenusTotal}" pattern="#,##0.00"/> TND
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><strong>Revenus ce mois:</strong></td>
                                        <td class="text-right">
                                            <fmt:formatNumber value="${financialStats.revenusMois}" pattern="#,##0.00"/> TND
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><strong>Prix moyen:</strong></td>
                                        <td class="text-right">
                                            <fmt:formatNumber value="${financialStats.prixMoyen}" pattern="#,##0.00"/> TND
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Trajets populaires -->
                <div class="row mb-4">
                    <div class="col-md-6">
                        <div class="card table-stats">
                            <div class="card-header">
                                <h5><i class="fas fa-star"></i> Top 5 Trajets Populaires</h5>
                            </div>
                            <div class="card-body">
                                <c:choose>
                                    <c:when test="${not empty trajetsPopulaires}">
                                        <table class="table table-sm">
                                            <thead>
                                                <tr>
                                                    <th>Trajet</th>
                                                    <th class="text-right">Billets vendus</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${trajetsPopulaires}" var="trajet" varStatus="status">
                                                    <tr>
                                                        <td>
                                                            <span class="badge badge-primary">${status.index + 1}</span>
                                                            ${trajet.key}
                                                        </td>
                                                        <td class="text-right">
                                                            <strong>${trajet.value}</strong>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </c:when>
                                    <c:otherwise>
                                        <p class="text-muted">Aucune donnée disponible</p>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="card table-stats">
                            <div class="card-header">
                                <h5><i class="fas fa-money-bill-wave"></i> Revenus par Classe</h5>
                            </div>
                            <div class="card-body">
                                <canvas id="revenusChart"></canvas>
                            </div>
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
        // Configuration des couleurs
        const colors = {
            primary: '#667eea',
            success: '#4facfe',
            warning: '#f093fb',
            info: '#43e97b',
            danger: '#f5576c'
        };

        // Graphique état des billets
        const billetsCtx = document.getElementById('billetsChart').getContext('2d');
        new Chart(billetsCtx, {
            type: 'doughnut',
            data: {
                labels: ['Achetés', 'Utilisés', 'Annulés'],
                datasets: [{
                    data: [
                        ${generalStats.billetsAchetes},
                        ${generalStats.billetsUtilises},
                        ${generalStats.billetsAnnules}
                    ],
                    backgroundColor: [colors.success, colors.info, colors.danger]
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                legend: { position: 'bottom' }
            }
        });

        // Graphique ventes par classe
        const classesCtx = document.getElementById('classesChart').getContext('2d');
        new Chart(classesCtx, {
            type: 'bar',
            data: {
                labels: [<c:forEach items="${ventesParClasse}" var="classe" varStatus="status">'${classe.key}'<c:if test="${!status.last}">,</c:if></c:forEach>],
                datasets: [{
                    label: 'Billets vendus',
                    data: [<c:forEach items="${ventesParClasse}" var="classe" varStatus="status">${classe.value}<c:if test="${!status.last}">,</c:if></c:forEach>],
                    backgroundColor: colors.primary
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                legend: { display: false }
            }
        });

        // Graphique utilisateurs
        const usersCtx = document.getElementById('usersChart').getContext('2d');
        new Chart(usersCtx, {
            type: 'pie',
            data: {
                labels: ['Actifs', 'Bloqués'],
                datasets: [{
                    data: [${userStats.usersActifs}, ${userStats.usersBloqués}],
                    backgroundColor: [colors.success, colors.danger]
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                legend: { position: 'bottom' }
            }
        });

        // Graphique évolution des ventes
        const ventesCtx = document.getElementById('ventesChart').getContext('2d');
        new Chart(ventesCtx, {
            type: 'line',
            data: {
                labels: [<c:forEach items="${ventesParJour}" var="vente" varStatus="status">'${vente.key}'<c:if test="${!status.last}">,</c:if></c:forEach>],
                datasets: [{
                    label: 'Billets vendus',
                    data: [<c:forEach items="${ventesParJour}" var="vente" varStatus="status">${vente.value}<c:if test="${!status.last}">,</c:if></c:forEach>],
                    borderColor: colors.primary,
                    backgroundColor: colors.primary + '20',
                    fill: true,
                    tension: 0.4
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                scales: {
                    y: { beginAtZero: true }
                }
            }
        });

        // Graphique revenus par classe
        const revenusCtx = document.getElementById('revenusChart').getContext('2d');
        new Chart(revenusCtx, {
            type: 'doughnut',
            data: {
                labels: [<c:forEach items="${financialStats.revenusParClasse}" var="revenu" varStatus="status">'${revenu.key}'<c:if test="${!status.last}">,</c:if></c:forEach>],
                datasets: [{
                    data: [<c:forEach items="${financialStats.revenusParClasse}" var="revenu" varStatus="status">${revenu.value}<c:if test="${!status.last}">,</c:if></c:forEach>],
                    backgroundColor: [colors.primary, colors.success, colors.warning]
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                legend: { position: 'bottom' }
            }
        });
    </script>
</body>
</html>
