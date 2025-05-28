/**
 * Train Ticket System - JavaScript Application
 * Gestion des interactions et animations
 */

document.addEventListener('DOMContentLoaded', function() {
    // Initialisation des composants
    initializeAnimations();
    initializeFormValidation();
    initializeSearchForm();
    initializeNavigation();
    initializeTooltips();
    initializeModals();
});

/**
 * Animations d'entrée pour les éléments
 */
function initializeAnimations() {
    // Observer pour les animations au scroll
    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add('fade-in-up');
                observer.unobserve(entry.target);
            }
        });
    }, {
        threshold: 0.1,
        rootMargin: '0px 0px -50px 0px'
    });

    // Observer les cartes et sections
    document.querySelectorAll('.feature-card, .voyage-card, .ticket-card, .card').forEach(el => {
        observer.observe(el);
    });
}

/**
 * Validation des formulaires
 */
function initializeFormValidation() {
    const forms = document.querySelectorAll('form[data-validate]');
    
    forms.forEach(form => {
        form.addEventListener('submit', function(e) {
            if (!validateForm(this)) {
                e.preventDefault();
                e.stopPropagation();
            }
        });

        // Validation en temps réel
        const inputs = form.querySelectorAll('input, select, textarea');
        inputs.forEach(input => {
            input.addEventListener('blur', function() {
                validateField(this);
            });
        });
    });
}

/**
 * Validation d'un champ individuel
 */
function validateField(field) {
    const value = field.value.trim();
    const type = field.type;
    const required = field.hasAttribute('required');
    let isValid = true;
    let message = '';

    // Supprime les messages d'erreur existants
    removeFieldError(field);

    if (required && !value) {
        isValid = false;
        message = 'Ce champ est requis';
    } else if (value) {
        switch (type) {
            case 'email':
                const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                if (!emailRegex.test(value)) {
                    isValid = false;
                    message = 'Adresse email invalide';
                }
                break;
            case 'tel':
                const phoneRegex = /^[\d\s\-\+\(\)]+$/;
                if (!phoneRegex.test(value)) {
                    isValid = false;
                    message = 'Numéro de téléphone invalide';
                }
                break;
            case 'date':
                const selectedDate = new Date(value);
                const today = new Date();
                today.setHours(0, 0, 0, 0);
                if (selectedDate < today) {
                    isValid = false;
                    message = 'La date ne peut pas être dans le passé';
                }
                break;
        }
    }

    if (!isValid) {
        showFieldError(field, message);
    }

    return isValid;
}

/**
 * Validation complète du formulaire
 */
function validateForm(form) {
    const fields = form.querySelectorAll('input[required], select[required], textarea[required]');
    let isValid = true;

    fields.forEach(field => {
        if (!validateField(field)) {
            isValid = false;
        }
    });

    return isValid;
}

/**
 * Affiche une erreur sur un champ
 */
function showFieldError(field, message) {
    field.classList.add('is-invalid');
    
    const errorDiv = document.createElement('div');
    errorDiv.className = 'field-error';
    errorDiv.textContent = message;
    errorDiv.style.color = 'var(--error-color)';
    errorDiv.style.fontSize = 'var(--font-size-sm)';
    errorDiv.style.marginTop = 'var(--spacing-1)';
    
    field.parentNode.appendChild(errorDiv);
}

/**
 * Supprime l'erreur d'un champ
 */
function removeFieldError(field) {
    field.classList.remove('is-invalid');
    const errorDiv = field.parentNode.querySelector('.field-error');
    if (errorDiv) {
        errorDiv.remove();
    }
}

/**
 * Gestion du formulaire de recherche
 */
function initializeSearchForm() {
    const searchForm = document.querySelector('#searchForm');
    if (!searchForm) return;

    // Auto-complétion pour les villes
    const cityInputs = searchForm.querySelectorAll('input[data-autocomplete="city"]');
    cityInputs.forEach(input => {
        setupCityAutocomplete(input);
    });

    // Échange des villes
    const swapButton = searchForm.querySelector('#swapCities');
    if (swapButton) {
        swapButton.addEventListener('click', function() {
            const departInput = searchForm.querySelector('input[name="villeDepart"]');
            const arriveInput = searchForm.querySelector('input[name="villeArrivee"]');
            
            if (departInput && arriveInput) {
                const temp = departInput.value;
                departInput.value = arriveInput.value;
                arriveInput.value = temp;
                
                // Animation
                this.style.transform = 'rotate(180deg)';
                setTimeout(() => {
                    this.style.transform = 'rotate(0deg)';
                }, 300);
            }
        });
    }
}

/**
 * Auto-complétion pour les villes
 */
function setupCityAutocomplete(input) {
    const cities = [
        'Tunis', 'Sfax', 'Sousse', 'Kairouan', 'Bizerte', 'Gabès', 'Ariana',
        'Gafsa', 'Monastir', 'Ben Arous', 'Kasserine', 'Médenine', 'Nabeul',
        'Tataouine', 'Béja', 'Jendouba', 'Mahdia', 'Siliana', 'Manouba',
        'Zaghouan', 'Tozeur', 'Kébili', 'Le Kef', 'Sidi Bouzid'
    ];

    let currentFocus = -1;
    
    input.addEventListener('input', function() {
        const value = this.value.toLowerCase();
        closeAllLists();
        
        if (!value) return;
        
        const listDiv = document.createElement('div');
        listDiv.className = 'autocomplete-list';
        listDiv.style.cssText = `
            position: absolute;
            top: 100%;
            left: 0;
            right: 0;
            background: white;
            border: 1px solid var(--gray-300);
            border-top: none;
            border-radius: 0 0 var(--border-radius) var(--border-radius);
            box-shadow: var(--shadow-md);
            max-height: 200px;
            overflow-y: auto;
            z-index: 1000;
        `;
        
        this.parentNode.style.position = 'relative';
        this.parentNode.appendChild(listDiv);
        
        cities.forEach(city => {
            if (city.toLowerCase().includes(value)) {
                const itemDiv = document.createElement('div');
                itemDiv.className = 'autocomplete-item';
                itemDiv.style.cssText = `
                    padding: var(--spacing-3) var(--spacing-4);
                    cursor: pointer;
                    border-bottom: 1px solid var(--gray-100);
                `;
                itemDiv.textContent = city;
                
                itemDiv.addEventListener('click', function() {
                    input.value = city;
                    closeAllLists();
                });
                
                itemDiv.addEventListener('mouseenter', function() {
                    this.style.backgroundColor = 'var(--gray-100)';
                });
                
                itemDiv.addEventListener('mouseleave', function() {
                    this.style.backgroundColor = 'transparent';
                });
                
                listDiv.appendChild(itemDiv);
            }
        });
    });
    
    function closeAllLists() {
        const lists = document.querySelectorAll('.autocomplete-list');
        lists.forEach(list => list.remove());
    }
    
    document.addEventListener('click', function(e) {
        if (!input.contains(e.target)) {
            closeAllLists();
        }
    });
}

/**
 * Navigation active
 */
function initializeNavigation() {
    const currentPath = window.location.pathname;
    const navLinks = document.querySelectorAll('.nav-link');
    
    navLinks.forEach(link => {
        const href = link.getAttribute('href');
        if (href && currentPath.includes(href)) {
            link.classList.add('active');
        }
    });
}

/**
 * Tooltips
 */
function initializeTooltips() {
    const tooltipElements = document.querySelectorAll('[data-tooltip]');
    
    tooltipElements.forEach(element => {
        element.addEventListener('mouseenter', showTooltip);
        element.addEventListener('mouseleave', hideTooltip);
    });
}

function showTooltip(e) {
    const text = e.target.getAttribute('data-tooltip');
    const tooltip = document.createElement('div');
    tooltip.className = 'tooltip';
    tooltip.textContent = text;
    tooltip.style.cssText = `
        position: absolute;
        background: var(--gray-900);
        color: white;
        padding: var(--spacing-2) var(--spacing-3);
        border-radius: var(--border-radius);
        font-size: var(--font-size-sm);
        z-index: 1000;
        pointer-events: none;
        opacity: 0;
        transition: opacity 0.2s ease;
    `;
    
    document.body.appendChild(tooltip);
    
    const rect = e.target.getBoundingClientRect();
    tooltip.style.left = rect.left + (rect.width / 2) - (tooltip.offsetWidth / 2) + 'px';
    tooltip.style.top = rect.top - tooltip.offsetHeight - 8 + 'px';
    
    setTimeout(() => {
        tooltip.style.opacity = '1';
    }, 10);
    
    e.target._tooltip = tooltip;
}

function hideTooltip(e) {
    if (e.target._tooltip) {
        e.target._tooltip.remove();
        delete e.target._tooltip;
    }
}

/**
 * Modales
 */
function initializeModals() {
    const modalTriggers = document.querySelectorAll('[data-modal]');
    
    modalTriggers.forEach(trigger => {
        trigger.addEventListener('click', function(e) {
            e.preventDefault();
            const modalId = this.getAttribute('data-modal');
            openModal(modalId);
        });
    });
}

function openModal(modalId) {
    const modal = document.getElementById(modalId);
    if (!modal) return;
    
    modal.style.display = 'flex';
    document.body.style.overflow = 'hidden';
    
    // Fermeture par clic sur le fond
    modal.addEventListener('click', function(e) {
        if (e.target === this) {
            closeModal(modalId);
        }
    });
    
    // Fermeture par bouton
    const closeBtn = modal.querySelector('.modal-close');
    if (closeBtn) {
        closeBtn.addEventListener('click', () => closeModal(modalId));
    }
}

function closeModal(modalId) {
    const modal = document.getElementById(modalId);
    if (!modal) return;
    
    modal.style.display = 'none';
    document.body.style.overflow = 'auto';
}

/**
 * Utilitaires
 */
function showNotification(message, type = 'info') {
    const notification = document.createElement('div');
    notification.className = `notification notification-${type}`;
    notification.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        padding: var(--spacing-4) var(--spacing-6);
        border-radius: var(--border-radius);
        box-shadow: var(--shadow-lg);
        z-index: 1000;
        transform: translateX(100%);
        transition: transform 0.3s ease;
    `;
    
    const colors = {
        success: 'var(--success-color)',
        warning: 'var(--warning-color)',
        error: 'var(--error-color)',
        info: 'var(--primary-color)'
    };
    
    notification.style.backgroundColor = colors[type] || colors.info;
    notification.style.color = 'white';
    notification.textContent = message;
    
    document.body.appendChild(notification);
    
    setTimeout(() => {
        notification.style.transform = 'translateX(0)';
    }, 10);
    
    setTimeout(() => {
        notification.style.transform = 'translateX(100%)';
        setTimeout(() => notification.remove(), 300);
    }, 3000);
}

// Export des fonctions utilitaires
window.TrainTicketApp = {
    showNotification,
    openModal,
    closeModal,
    validateForm
};
