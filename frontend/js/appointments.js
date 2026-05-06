/**
 * Appointment API integrations
 */

/**
 * Books a new appointment
 * @param {Object} data { doctorId, appointmentDate, time }
 * @returns {Promise<Object>}
 */
async function bookAppointment(data) {
    return await apiFetch('/api/appointments', {
        method: 'POST',
        body: JSON.stringify(data)
    });
}

/**
 * Gets the list of appointments for the current user (Patient/Doctor/Admin)
 * The backend endpoints vary slightly as per the initial specs, so we route based on role if necessary,
 * or use the single endpoint provided in the latest prompt if the backend handles routing by token.
 * We will use the ones specified in the original prompt:
 * PATIENT: GET /api/patient/appointments
 * DOCTOR: GET /api/doctor/appointments
 * ADMIN: (Prompt didn't specify admin appointments explicitly but asked to show all, assuming GET /api/admin/appointments or GET /api/appointments)
 * However, the prompt for STEP 2 specifies: "return await apiFetch('/api/appointments');"
 * So we will follow the explicit instruction for STEP 2.
 * @returns {Promise<Array>}
 */
async function getAppointments() {
    const role = localStorage.getItem('role');

    let endpoint = '/api/appointments';

    if (role === 'PATIENT') endpoint = '/api/patient/appointments';
    else if (role === 'DOCTOR') endpoint = '/api/appointments/doctor';
    else if (role === 'ADMIN') endpoint = '/api/appointments';

    return await apiFetch(endpoint);
}

/**
 * Updates the status of an appointment (Approve/Reject/Complete/Cancel)
 * @param {number|string} id 
 * @param {string} status 
 * @returns {Promise<Object>}
 */
async function updateAppointmentStatus(id, status) {
    return await apiFetch(`/api/appointments/${id}/status`, {
        method: 'PUT',
        body: JSON.stringify({ status })
    });
}
