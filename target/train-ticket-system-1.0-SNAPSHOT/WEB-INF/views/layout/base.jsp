<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>
        <c:choose>
            <c:when test="${not empty pageTitle}">${pageTitle} - TrainTicket</c:when>
            <c:otherwise>TrainTicket - Réservation de billets de train en ligne</c:otherwise>
        </c:choose>
    </title>
    
    <c:if test="${not empty pageDescription}">
        <meta name="description" content="${pageDescription}">
    </c:if>
    
    <!-- Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    
    <!-- Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
    <!-- Styles -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/navbar.css">
    
    <!-- Page specific styles -->
    <c:if test="${not empty pageStyles}">
        <c:forEach items="${pageStyles}" var="style">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/${style}">
        </c:forEach>
    </c:if>
    
    <!-- Inline styles -->
    <c:if test="${not empty inlineStyles}">
        <style>${inlineStyles}</style>
    </c:if>
</head>
<body class="${not empty bodyClass ? bodyClass : ''}">
    <!-- Header -->
    <jsp:include page="/WEB-INF/views/header.jsp" />
    
    <!-- Page Header (if specified) -->
    <c:if test="${not empty showPageHeader and showPageHeader}">
        <section class="page-header">
            <div class="container">
                <div class="page-header-content">
                    <c:if test="${not empty breadcrumbs}">
                        <nav class="breadcrumb-nav">
                            <ol class="breadcrumb">
                                <c:forEach items="${breadcrumbs}" var="crumb" varStatus="status">
                                    <li class="breadcrumb-item ${status.last ? 'active' : ''}">
                                        <c:choose>
                                            <c:when test="${not empty crumb.url and not status.last}">
                                                <a href="${crumb.url}">${crumb.title}</a>
                                            </c:when>
                                            <c:otherwise>
                                                ${crumb.title}
                                            </c:otherwise>
                                        </c:choose>
                                    </li>
                                </c:forEach>
                            </ol>
                        </nav>
                    </c:if>
                    
                    <c:if test="${not empty pageTitle}">
                        <h1 class="page-title">${pageTitle}</h1>
                    </c:if>
                    
                    <c:if test="${not empty pageSubtitle}">
                        <p class="page-subtitle">${pageSubtitle}</p>
                    </c:if>
                </div>
            </div>
        </section>
    </c:if>
    
    <!-- Main Content -->
    <main class="main-content ${not empty contentClass ? contentClass : ''}">
        <!-- Alerts -->
        <c:if test="${not empty sessionScope.successMessage}">
            <div class="container">
                <div class="alert alert-success alert-dismissible">
                    <i class="fas fa-check-circle"></i>
                    <span>${sessionScope.successMessage}</span>
                    <button type="button" class="alert-close" onclick="this.parentElement.remove()">
                        <i class="fas fa-times"></i>
                    </button>
                </div>
            </div>
            <c:remove var="successMessage" scope="session"/>
        </c:if>
        
        <c:if test="${not empty sessionScope.errorMessage}">
            <div class="container">
                <div class="alert alert-danger alert-dismissible">
                    <i class="fas fa-exclamation-circle"></i>
                    <span>${sessionScope.errorMessage}</span>
                    <button type="button" class="alert-close" onclick="this.parentElement.remove()">
                        <i class="fas fa-times"></i>
                    </button>
                </div>
            </div>
            <c:remove var="errorMessage" scope="session"/>
        </c:if>
        
        <c:if test="${not empty sessionScope.warningMessage}">
            <div class="container">
                <div class="alert alert-warning alert-dismissible">
                    <i class="fas fa-exclamation-triangle"></i>
                    <span>${sessionScope.warningMessage}</span>
                    <button type="button" class="alert-close" onclick="this.parentElement.remove()">
                        <i class="fas fa-times"></i>
                    </button>
                </div>
            </div>
            <c:remove var="warningMessage" scope="session"/>
        </c:if>
        
        <c:if test="${not empty sessionScope.infoMessage}">
            <div class="container">
                <div class="alert alert-info alert-dismissible">
                    <i class="fas fa-info-circle"></i>
                    <span>${sessionScope.infoMessage}</span>
                    <button type="button" class="alert-close" onclick="this.parentElement.remove()">
                        <i class="fas fa-times"></i>
                    </button>
                </div>
            </div>
            <c:remove var="infoMessage" scope="session"/>
        </c:if>
        
        <!-- Page Content -->
        <jsp:include page="${contentPage}" />
    </main>
    
    <!-- Footer -->
    <jsp:include page="/WEB-INF/views/footer.jsp" />
    
    <!-- Scripts -->
    <script src="${pageContext.request.contextPath}/assets/js/app.js"></script>
    
    <!-- Page specific scripts -->
    <c:if test="${not empty pageScripts}">
        <c:forEach items="${pageScripts}" var="script">
            <script src="${pageContext.request.contextPath}/assets/js/${script}"></script>
        </c:forEach>
    </c:if>
    
    <!-- Inline scripts -->
    <c:if test="${not empty inlineScripts}">
        <script>${inlineScripts}</script>
    </c:if>
</body>
</html>
