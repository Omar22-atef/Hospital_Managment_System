// UI Utilities

document.addEventListener("DOMContentLoaded", () => {
    // Apply fade-in animation to elements
    const elementsToFade = document.querySelectorAll('.fade-in');
    
    // Quick timeout to ensure CSS is ready before applying the visible class
    setTimeout(() => {
        elementsToFade.forEach(el => {
            el.classList.add('visible');
        });
    }, 100);

    // Update navbar based on auth state
    updateNavbar();
});

/**
 * Updates the navbar links depending on whether the user is logged in.
 */
function updateNavbar() {
    const navbarLinksContainer = document.querySelector('.navbar-links');
    if (!navbarLinksContainer) return;

    const token = localStorage.getItem("token");
    const role = localStorage.getItem("role");
    
    // Determine the dashboard link based on role
    let dashboardLink = "index.html";
    if (role) {
        if (role.toUpperCase() === 'PATIENT') dashboardLink = "patient-dashboard.html";
        else if (role.toUpperCase() === 'DOCTOR') dashboardLink = "doctor-dashboard.html";
        else if (role.toUpperCase() === 'ADMIN') dashboardLink = "admin-dashboard.html";
    }

    if (token) {
        // User is logged in
        navbarLinksContainer.innerHTML = `
            <a href="index.html">Home</a>
            <a href="available-doctors.html">Our Doctors</a>
            <a href="${dashboardLink}">Dashboard</a>
            <a href="#" onclick="logout(); return false;" style="color: var(--primary-color);">Logout</a>
        `;
    } else {
        // User is NOT logged in
        navbarLinksContainer.innerHTML = `
            <a href="index.html">Home</a>
            <a href="available-doctors.html">Our Doctors</a>
            <a href="login.html">Login</a>
            <a href="register.html">Register</a>
        `;
    }
}

/**
 * Shows a toast message on the screen.
 * @param {string} message - The message to display
 * @param {string} type - 'success', 'error', or 'info'
 */
function showToast(message, type = 'info') {
    // Check if toast container exists, if not create it
    let container = document.querySelector('.toast-container');
    if (!container) {
        container = document.createElement('div');
        container.className = 'toast-container';
        document.body.appendChild(container);
    }

    const toast = document.createElement('div');
    toast.className = `toast ${type}`;
    
    // Add icon based on type
    let icon = 'ℹ️';
    if (type === 'error') icon = '❌';
    if (type === 'success') icon = '✅';

    toast.innerHTML = `<span>${icon}</span> <span>${message}</span>`;
    
    container.appendChild(toast);

    // Trigger reflow to ensure the transition happens
    toast.offsetHeight;
    
    toast.classList.add('show');

    // Remove toast after 3 seconds
    setTimeout(() => {
        toast.classList.remove('show');
        setTimeout(() => {
            if (toast.parentNode) {
                toast.parentNode.removeChild(toast);
            }
        }, 300); // Wait for transition to finish
    }, 3000);
}

/**
 * Validates a Date of Birth (must be 16-120 years old, no future dates).
 * Returns true if valid, or a string error message if invalid.
 * @param {string} dobString - The date string (YYYY-MM-DD)
 */
function validateDateOfBirth(dobString) {
    if (!dobString) return "Date of Birth is required.";
    const dob = new Date(dobString);
    const today = new Date();
    
    if (dob > today) {
        return "Date of birth cannot be in the future.";
    }
    
    let age = today.getFullYear() - dob.getFullYear();
    const m = today.getMonth() - dob.getMonth();
    if (m < 0 || (m === 0 && today.getDate() < dob.getDate())) {
        age--;
    }
    
    if (age < 16) {
        return "You must be at least 16 years old to use the system.";
    }
    
    if (age > 120) {
        return "Please enter a valid date of birth.";
    }
    
    return true;
}
