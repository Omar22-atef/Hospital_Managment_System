// Auth related logic

/**
 * Handles the login process
 * @param {Event} event 
 */
async function login(event) {
    event.preventDefault();

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    if (!email || !password) {
        showToast("Please enter both email and password.", "error");
        return;
    }

    try {
        const response = await apiFetch('/api/auth/login', {
            method: 'POST',
            body: JSON.stringify({ email, password })
        });

        // The API returns token and role directly in the response
        if (response && response.token && response.role) {
            // Save token and role to localStorage
            localStorage.setItem('token', response.token);
            localStorage.setItem('role', response.role);

            showToast("Login successful! Redirecting...", "success");

            // Redirect based on role
            setTimeout(() => {
                const role = response.role.toUpperCase();
                if (role === 'PATIENT') {
                    window.location.href = 'patient-dashboard.html';
                } else if (role === 'DOCTOR') {
                    window.location.href = 'doctor-dashboard.html';
                } else if (role === 'ADMIN') {
                    window.location.href = 'admin-dashboard.html';
                } else {
                    showToast("Unknown role received.", "error");
                }
            }, 1000);
        } else {
            showToast("Invalid response format from server.", "error");
        }

    } catch (error) {
        // Show user-friendly error message
        showToast(error.message || "Invalid email or password", "error");
    }
}

/**
 * Helper to get the current stored token
 */
function getToken() {
    return localStorage.getItem('token');
}

/**
 * Helper to clear auth data (logout)
 */
function clearAuth() {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
}

/**
 * Ensures the user is authenticated. If not, redirects to login page.
 */
function requireAuth() {
    const token = localStorage.getItem("token");
    if (!token) {
        window.location.href = "login.html";
    }
}

/**
 * Logs the user out and redirects to the login page.
 */
function logout() {
    clearAuth();
    window.location.href = "login.html";
}

// Bind event listeners if the form exists on the current page
document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById('loginForm');
    if (loginForm) loginForm.addEventListener('submit', login);

    const registerForm = document.getElementById('registerForm');
    if (registerForm) registerForm.addEventListener('submit', register);

    const forgotPasswordForm = document.getElementById('forgotPasswordForm');
    if (forgotPasswordForm) forgotPasswordForm.addEventListener('submit', forgotPassword);

    const resetPasswordForm = document.getElementById('resetPasswordForm');
    if (resetPasswordForm) resetPasswordForm.addEventListener('submit', resetPassword);
});

/**
 * Handles the registration process
 * @param {Event} event 
 */
async function register(event) {
    event.preventDefault();

    const name = document.getElementById('name').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const phone = document.getElementById('phone').value;
    const dateOfBirth = document.getElementById('dateOfBirth').value;
    // Assuming the user registers as a patient based on the prompt's API contract.
    // If a different role is needed in the future, it can be passed or selected.
    const role = "PATIENT";

    if (!name || !email || !password || !phone || !dateOfBirth) {
        showToast("Please fill in all fields.", "error");
        return;
    }

    try {
        const payload = { name, email, password, phone, dateOfBirth, role };
        await apiFetch('/api/auth/register', {
            method: 'POST',
            body: JSON.stringify(payload)
        });

        showToast("Registration successful! Redirecting to login...", "success");

        setTimeout(() => {
            window.location.href = 'login.html';
        }, 1500);

    } catch (error) {
        showToast(error.message || "Registration failed", "error");
    }
}

/**
 * Handles the forgot password process
 * @param {Event} event 
 */
async function forgotPassword(event) {
    event.preventDefault();

    const email = document.getElementById('email').value;
    const role = document.getElementById('role').value;

    if (!email || !role) {
        showToast("Please provide your email and role.", "error");
        return;
    }

    try {
        await apiFetch('/api/auth/forgot-password', {
            method: 'POST',
            body: JSON.stringify({ email, role })
        });

        // Store email and role temporarily to pre-fill the next page (optional UX improvement)
        sessionStorage.setItem('resetEmail', email);
        sessionStorage.setItem('resetRole', role);

        showToast("OTP sent to your email.", "success");

        setTimeout(() => {
            window.location.href = 'reset-password.html';
        }, 1500);

    } catch (error) {
        showToast(error.message || "Failed to send OTP", "error");
    }
}

/**
 * Handles the reset password process
 * @param {Event} event 
 */
async function resetPassword(event) {
    event.preventDefault();

    const email = document.getElementById('email').value;
    const role = document.getElementById('role').value;
    const otpCode = document.getElementById('otpCode').value;
    const newPassword = document.getElementById('newPassword').value;

    if (!email || !role || !otpCode || !newPassword) {
        showToast("Please fill in all fields.", "error");
        return;
    }

    try {
        await apiFetch('/api/auth/reset-password', {
            method: 'POST',
            body: JSON.stringify({ email, role, otpCode, newPassword })
        });

        // Clear session storage since it's no longer needed
        sessionStorage.removeItem('resetEmail');
        sessionStorage.removeItem('resetRole');

        showToast("Password reset successfully. Please login.", "success");

        setTimeout(() => {
            window.location.href = 'login.html';
        }, 1500);

    } catch (error) {
        showToast(error.message || "Failed to reset password", "error");
    }
}
