/**
 * Doctor API integrations
 * Mapped to DoctorController
 */

/**
 * Gets the current doctor's profile.
 * GET /api/doctor/me
 */
async function getDoctorProfile() {
    return await apiFetch('/api/doctor/me');
}

/**
 * Gets all appointments for the current doctor.
 * GET /api/doctor/appointments
 */
async function getDoctorAppointments() {
    return await apiFetch('/api/doctor/appointments');
}
