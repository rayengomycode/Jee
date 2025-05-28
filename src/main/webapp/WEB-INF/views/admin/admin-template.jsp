<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!-- Template CSS et JS pour les pages admin -->
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
        margin-bottom: 2rem;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }
    
    .admin-header h2 {
        color: #1f2937;
        font-weight: 700;
        margin: 0;
        font-size: 2rem;
        display: flex;
        align-items: center;
        gap: 0.75rem;
    }
    
    .admin-header h2 i {
        color: #6366f1;
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
    
    .btn {
        border-radius: 8px;
        font-weight: 500;
        transition: all 0.3s ease;
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
    
    .btn-warning {
        background: linear-gradient(135deg, #f59e0b, #d97706);
        border: none;
        color: white;
    }
    
    .btn-danger {
        background: linear-gradient(135deg, #ef4444, #dc2626);
        border: none;
    }
    
    .btn-success {
        background: linear-gradient(135deg, #10b981, #047857);
        border: none;
    }
    
    .btn-info {
        background: linear-gradient(135deg, #06b6d4, #0891b2);
        border: none;
    }
    
    @media (max-width: 768px) {
        .admin-content {
            padding: 1rem 0.5rem;
        }
        
        .admin-header {
            flex-direction: column;
            gap: 1rem;
            text-align: center;
        }
        
        .admin-header h2 {
            font-size: 1.8rem;
        }
    }
</style>

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
