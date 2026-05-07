/**
 * Patient API integrations
 * Mapped to PatientController
 */

/**
 * Gets the current patient's profile.
 * GET /api/patient/me
 */
async function getPatientProfile() {
    return await apiFetch('/api/patient/me');
}

/**
 * Updates the current patient's profile.
 * PUT /api/patient/me
 * @param {Object} data { name, phone, dateOfBirth }
 */
async function updatePatientProfile(data) {
    return await apiFetch('/api/patient/me', {
        method: 'PUT',
        body: JSON.stringify(data)
    });
}

/**
 * Gets all doctors available in the system for the patient.
 * GET /api/patient/doctors
 */
async function getPatientDoctors() {
    return await apiFetch('/api/patient/doctors');
}

/**
 * Gets all appointments for the current patient.
 * GET /api/patient/appointments
 */
async function getPatientAppointments() {
    return await apiFetch('/api/patient/appointments');
}

/**
 * Gets all payments for the current patient.
 * GET /api/patient/payments
 */
async function getPatientPayments() {
    return await apiFetch('/api/patient/payments');
}
