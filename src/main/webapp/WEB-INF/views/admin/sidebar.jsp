<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="admin-sidebar-simple">
    <!-- Sidebar Header -->
    <div class="sidebar-header-simple">
        <h4><i class="fas fa-cogs"></i> Administration</h4>
    </div>

    <!-- Navigation Menu -->
    <div class="list-group">
        <a href="${pageContext.request.contextPath}/admin/dashboard"
           class="list-group-item list-group-item-action admin-nav-item ${pageContext.request.requestURI.contains('dashboard') ? 'active' : ''}">
            <i class="fas fa-tachometer-alt"></i> Tableau de bord
        </a>
        <a href="${pageContext.request.contextPath}/admin/trains"
           class="list-group-item list-group-item-action admin-nav-item ${pageContext.request.requestURI.contains('trains') ? 'active' : ''}">
            <i class="fas fa-train"></i> Gestion des trains
        </a>
        <a href="${pageContext.request.contextPath}/admin/gares"
           class="list-group-item list-group-item-action admin-nav-item ${pageContext.request.requestURI.contains('gares') ? 'active' : ''}">
            <i class="fas fa-building"></i> Gestion des gares
        </a>
        <a href="${pageContext.request.contextPath}/admin/trajets"
           class="list-group-item list-group-item-action admin-nav-item ${pageContext.request.requestURI.contains('trajets') ? 'active' : ''}">
            <i class="fas fa-route"></i> Gestion des trajets
        </a>
        <a href="${pageContext.request.contextPath}/admin/voyages"
           class="list-group-item list-group-item-action admin-nav-item ${pageContext.request.requestURI.contains('voyages') ? 'active' : ''}">
            <i class="fas fa-calendar-alt"></i> Gestion des voyages
        </a>
        <a href="${pageContext.request.contextPath}/admin/billets"
           class="list-group-item list-group-item-action admin-nav-item ${pageContext.request.requestURI.contains('billets') ? 'active' : ''}">
            <i class="fas fa-ticket-alt"></i> Gestion des billets
        </a>
        <a href="${pageContext.request.contextPath}/admin/users"
           class="list-group-item list-group-item-action admin-nav-item ${pageContext.request.requestURI.contains('users') ? 'active' : ''}">
            <i class="fas fa-users"></i> Gestion des utilisateurs
        </a>
        <a href="${pageContext.request.contextPath}/admin/statistiques"
           class="list-group-item list-group-item-action admin-nav-item ${pageContext.request.requestURI.contains('statistiques') ? 'active' : ''}">
            <i class="fas fa-chart-bar"></i> Statistiques
        </a>
    </div>

    <!-- Sidebar Footer -->
    <div class="sidebar-footer-simple">
        <div class="admin-user-info">
            <i class="fas fa-user-shield"></i>
            <span>Panel d'Administration</span>
        </div>
        <div class="sidebar-stats">
            <small class="text-muted">Connecté en tant qu'administrateur</small>
        </div>
    </div>
</div>
