/**
 * Admin API integrations
 * Mapped to AdminController
 */

/**
 * Gets the admin dashboard data.
 * GET /api/admin/dashboard
 */
async function getAdminDashboard() {
    return await apiFetch('/api/admin/dashboard');
}

/**
 * Gets all doctors.
 * GET /api/admin/doctors
 */
async function getAllDoctors() {
    return await apiFetch('/api/admin/doctors');
}

/**
 * Gets all appointments.
 * GET /api/admin/appointments
 */
async function getAllAppointments() {
    return await apiFetch('/api/admin/appointments');
}

/**
 * Gets all patients.
 * GET /api/admin/patients
 */
async function getAllPatients() {
    return await apiFetch('/api/admin/patients');
}

/**
 * Creates a new doctor.
 * POST /api/admin/doctors
 * @param {Object} data DoctorRequestDTO
 */
async function createDoctor(data) {
    return await apiFetch('/api/admin/doctors', {
        method: 'POST',
        body: JSON.stringify(data)
    });
}

/**
 * Updates an existing doctor.
 * PUT /api/admin/doctors/{id}
 * @param {number|string} id 
 * @param {Object} data DoctorRequestDTO
 */
async function updateDoctor(id, data) {
    return await apiFetch(`/api/admin/doctors/${id}`, {
        method: 'PUT',
        body: JSON.stringify(data)
    });
}

/**
 * Deletes a doctor.
 * DELETE /api/admin/doctors/{id}
 * @param {number|string} id 
 */
async function deleteDoctor(id) {
    return await apiFetch(`/api/admin/doctors/${id}`, {
        method: 'DELETE'
    });
}
