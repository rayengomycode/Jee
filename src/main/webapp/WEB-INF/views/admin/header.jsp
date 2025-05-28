<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<nav class="navbar">
    <div class="container">
        <div class="navbar-content">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">
                <i class="fas fa-train"></i>
                <span>TrainTicket</span>
                <span class="admin-badge">ADMIN</span>
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
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/dashboard">
                            <i class="fas fa-tachometer-alt"></i>
                            <span>Dashboard</span>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/users">
                            <i class="fas fa-users"></i>
                            <span>Utilisateurs</span>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/trains">
                            <i class="fas fa-train"></i>
                            <span>Trains</span>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/gares">
                            <i class="fas fa-building"></i>
                            <span>Gares</span>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/statistiques">
                            <i class="fas fa-chart-bar"></i>
                            <span>Statistiques</span>
                        </a>
                    </li>
                </ul>

                <div class="navbar-actions">
                    <div class="user-menu">
                        <button class="user-menu-toggle" id="userMenuToggle">
                            <div class="user-avatar">
                                <i class="fas fa-user-shield"></i>
                            </div>
                            <div class="user-info">
                                <span class="user-name">${sessionScope.user.prenom} ${sessionScope.user.nom}</span>
                                <span class="user-role">ADMIN</span>
                            </div>
                            <i class="fas fa-chevron-down"></i>
                        </button>

                        <div class="user-dropdown" id="userDropdown">
                            <div class="user-dropdown-header">
                                <div class="user-avatar-large">
                                    <i class="fas fa-user-shield"></i>
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
                                <a class="dropdown-item" href="${pageContext.request.contextPath}/">
                                    <i class="fas fa-home"></i>
                                    <span>Site Public</span>
                                </a>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item logout" href="${pageContext.request.contextPath}/auth?action=logout">
                                    <i class="fas fa-sign-out-alt"></i>
                                    <span>Déconnexion</span>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</nav>

<!-- Badge ADMIN pour la navbar -->
<style>
.admin-badge {
    background: linear-gradient(135deg, #6366f1, #8b5cf6);
    color: white;
    font-size: 0.7rem;
    font-weight: 600;
    padding: 0.2rem 0.5rem;
    border-radius: 12px;
    margin-left: 0.5rem;
    text-transform: uppercase;
    letter-spacing: 0.5px;
}

.user-avatar i.fa-user-shield {
    color: #6366f1;
}

.user-avatar-large i.fa-user-shield {
    color: #6366f1;
}

/* Diminuer la taille du menu utilisateur */
.user-menu-toggle {
    display: flex !important;
    align-items: center !important;
    gap: 0.5rem !important;
    padding: 0.4rem 0.8rem !important;
    background: none !important;
    border: none !important;
    border-radius: 8px !important;
    transition: all 0.3s ease !important;
    cursor: pointer !important;
    font-size: 0.85rem !important;
}

.user-menu-toggle:hover {
    background: var(--gray-100) !important;
}

.user-menu .user-avatar {
    width: 28px !important;
    height: 28px !important;
    font-size: 0.8rem !important;
}

.user-menu .user-info {
    display: flex !important;
    flex-direction: column !important;
    align-items: flex-start !important;
    line-height: 1.2 !important;
}

.user-menu .user-name {
    font-size: 0.8rem !important;
    font-weight: 600 !important;
    color: var(--gray-700) !important;
}

.user-menu .user-role {
    font-size: 0.7rem !important;
    color: var(--gray-500) !important;
    font-weight: 500 !important;
}

.user-menu-toggle .fa-chevron-down {
    font-size: 0.7rem !important;
    color: var(--gray-500) !important;
    transition: transform 0.3s ease !important;
}

.user-dropdown.show + .user-menu-toggle .fa-chevron-down {
    transform: rotate(180deg) !important;
}

/* Force horizontal navigation - utilise les variables CSS existantes */
.navbar .navbar-nav {
    display: flex !important;
    flex-direction: row !important;
    list-style: none !important;
    margin: 0 !important;
    padding: 0 !important;
    gap: var(--spacing-6) !important;
    align-items: center !important;
}

.navbar .navbar-nav .nav-item {
    display: block !important;
    margin: 0 !important;
}

.navbar .navbar-nav .nav-link {
    display: flex !important;
    align-items: center !important;
    gap: var(--spacing-2) !important;
    padding: var(--spacing-2) var(--spacing-3) !important;
    text-decoration: none !important;
    color: var(--gray-600) !important;
    font-weight: 500 !important;
    border-radius: var(--border-radius) !important;
    transition: all 0.2s ease !important;
    white-space: nowrap !important;
}

.navbar .navbar-nav .nav-link:hover {
    background: var(--gray-100) !important;
    color: var(--primary-color) !important;
    text-decoration: none !important;
}

.navbar .navbar-nav .nav-link.active {
    background: rgb(37 99 235 / 0.1) !important;
    color: var(--primary-color) !important;
}

.navbar .navbar-nav .nav-link i {
    font-size: 1rem !important;
}

/* Responsive - mobile vertical */
@media (max-width: 768px) {
    .navbar-menu.show .navbar-nav {
        flex-direction: column !important;
        gap: var(--spacing-2) !important;
        margin-top: var(--spacing-4) !important;
        width: 100% !important;
    }

    .navbar .navbar-nav .nav-link {
        justify-content: flex-start !important;
        width: 100% !important;
        padding: var(--spacing-3) var(--spacing-4) !important;
    }

    /* Menu utilisateur encore plus compact sur mobile */
    .user-menu-toggle {
        padding: 0.3rem 0.6rem !important;
        gap: 0.4rem !important;
    }

    .user-menu .user-avatar {
        width: 24px !important;
        height: 24px !important;
        font-size: 0.7rem !important;
    }

    .user-menu .user-info {
        display: none !important;
    }

    .user-menu-toggle .fa-chevron-down {
        font-size: 0.6rem !important;
    }
}
</style>

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
