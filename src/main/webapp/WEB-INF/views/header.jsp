<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<nav class="navbar">
    <div class="container">
        <div class="navbar-content">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">
                <i class="fas fa-train"></i>
                <span>TrainTicket</span>
            </a>

            <button class="navbar-toggle" id="navbarToggle" aria-label="Toggle navigation">
                <span></span>
                <span></span>
                <span></span>
            </button>

            <div class="navbar-menu" id="navbarMenu">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/">
                            <i class="fas fa-home"></i>
                            <span>Accueil</span>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/recherche">
                            <i class="fas fa-search"></i>
                            <span>Rechercher</span>
                        </a>
                    </li>
                    <c:if test="${not empty sessionScope.user}">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/mes-billets">
                                <i class="fas fa-ticket-alt"></i>
                                <span>Mes Billets</span>
                            </a>
                        </li>
                        <c:if test="${sessionScope.user.role.nom eq 'ADMIN'}">
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/admin/dashboard">
                                    <i class="fas fa-cog"></i>
                                    <span>Administration</span>
                                </a>
                            </li>
                        </c:if>
                    </c:if>
                </ul>

                <div class="navbar-actions">
                    <c:choose>
                        <c:when test="${empty sessionScope.user}">
                            <a class="btn btn-outline-primary btn-sm" href="${pageContext.request.contextPath}/auth?page=login">
                                <i class="fas fa-sign-in-alt"></i>
                                <span>Connexion</span>
                            </a>
                            <a class="btn btn-primary btn-sm" href="${pageContext.request.contextPath}/auth?page=register">
                                <i class="fas fa-user-plus"></i>
                                <span>Inscription</span>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <div class="user-menu">
                                <button class="user-menu-toggle" id="userMenuToggle">
                                    <div class="user-avatar">
                                        <i class="fas fa-user"></i>
                                    </div>
                                    <div class="user-info">
                                        <span class="user-name">${sessionScope.user.prenom} ${sessionScope.user.nom}</span>
                                        <span class="user-role">${sessionScope.user.role.nom}</span>
                                    </div>
                                    <i class="fas fa-chevron-down"></i>
                                </button>

                                <div class="user-dropdown" id="userDropdown">
                                    <div class="user-dropdown-header">
                                        <div class="user-avatar-large">
                                            <i class="fas fa-user"></i>
                                        </div>
                                        <div class="user-details">
                                            <div class="user-name">${sessionScope.user.prenom} ${sessionScope.user.nom}</div>
                                            <div class="user-email">${sessionScope.user.email}</div>
                                        </div>
                                    </div>

                                    <div class="user-dropdown-menu">
                                        <a class="dropdown-item" href="${pageContext.request.contextPath}/mes-billets">
                                            <i class="fas fa-ticket-alt"></i>
                                            <span>Mes Billets</span>
                                        </a>
                                        <c:if test="${sessionScope.user.role.nom eq 'ADMIN'}">
                                            <a class="dropdown-item" href="${pageContext.request.contextPath}/admin/dashboard">
                                                <i class="fas fa-cog"></i>
                                                <span>Administration</span>
                                            </a>
                                        </c:if>
                                        <div class="dropdown-divider"></div>
                                        <a class="dropdown-item logout" href="${pageContext.request.contextPath}/auth?action=logout">
                                            <i class="fas fa-sign-out-alt"></i>
                                            <span>Déconnexion</span>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</nav>

<script>
document.addEventListener('DOMContentLoaded', function() {
    // Toggle mobile menu
    const navbarToggle = document.getElementById('navbarToggle');
    const navbarMenu = document.getElementById('navbarMenu');

    if (navbarToggle && navbarMenu) {
        navbarToggle.addEventListener('click', function() {
            navbarMenu.classList.toggle('show');
        });
    }

    // Toggle user dropdown
    const userMenuToggle = document.getElementById('userMenuToggle');
    const userDropdown = document.getElementById('userDropdown');

    if (userMenuToggle && userDropdown) {
        userMenuToggle.addEventListener('click', function(e) {
            e.stopPropagation();
            userDropdown.classList.toggle('show');
        });

        // Close dropdown when clicking outside
        document.addEventListener('click', function() {
            userDropdown.classList.remove('show');
        });

        userDropdown.addEventListener('click', function(e) {
            e.stopPropagation();
        });
    }
});
</script>
