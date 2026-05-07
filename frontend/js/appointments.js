/**
 * Appointment API integrations
 * Strictly synchronized with AppointmentController + Service + DTOs.
 *
 * AppointmentStatus enum (backend): PENDING, CONFIRMED, CANCELLED, COMPLETED
 * PaymentMethod enum (backend):     CASH, CREDIT_CARD
 * PaymentStatus enum (backend):     PENDING, PAID, FAILED, REFUNDED
 */

// ---------------------------------------------------------------------------
// Appointment: Book
// POST /api/appointments/book  [PATIENT]
// Body: AppointmentRequestDTO { appointmentDate, appointmentTime, doctorId }
// Note: backend extracts patientId from the JWT — do NOT send patientId from frontend
// ---------------------------------------------------------------------------
async function bookAppointment(data) {
    return await apiFetch('/api/appointments/book', {
        method: 'POST',
        body: JSON.stringify(data)
    });
}

// ---------------------------------------------------------------------------
// Appointments: List (role-aware)
// PATIENT → GET /api/patient/appointments  → List<AppointmentResponseDTO> (no wrapper)
// DOCTOR  → GET /api/doctor/appointments   → ApiResponse<List<AppointmentResponseDTO>>
// ADMIN   → GET /api/admin/appointments    → ApiResponse<List<AppointmentResponseDTO>>
// ---------------------------------------------------------------------------
async function getAppointments() {
    const role = localStorage.getItem('role');
    if (!role) throw new Error('Not authenticated');
    const r = role.toUpperCase();
    if (r === 'PATIENT') return await apiFetch('/api/patient/appointments');
    if (r === 'DOCTOR')  return await apiFetch('/api/doctor/appointments');
    if (r === 'ADMIN')   return await apiFetch('/api/admin/appointments');
    throw new Error('Unknown role: ' + role);
}

// Convenience alias used by patient pages
async function getPatientAppointments() {
    return await apiFetch('/api/patient/appointments');
}

// ---------------------------------------------------------------------------
// Appointment: Confirm  [DOCTOR or ADMIN]
// PATCH /api/appointments/{id}/confirm
// ---------------------------------------------------------------------------
async function confirmAppointment(id) {
    return await apiFetch(`/api/appointments/${id}/confirm`, { method: 'PATCH' });
}

// ---------------------------------------------------------------------------
// Appointment: Complete  [DOCTOR]
// PATCH /api/appointments/{id}/complete
// ---------------------------------------------------------------------------
async function completeAppointment(id) {
    return await apiFetch(`/api/appointments/${id}/complete`, { method: 'PATCH' });
}

// ---------------------------------------------------------------------------
// Appointment: Doctor Cancel  [DOCTOR]
// PATCH /api/appointments/{id}/cancel
// Body: CancelRequest { cancelReason }  — must be non-blank (backend validates)
// ---------------------------------------------------------------------------
async function doctorCancelAppointment(id, reason) {
    return await apiFetch(`/api/appointments/${id}/cancel`, {
        method: 'PATCH',
        body: JSON.stringify({ cancelReason: reason })
    });
}

// ---------------------------------------------------------------------------
// Legacy router — used by doctor-dashboard and admin-dashboard.
// Accepts the enum value string and routes to the correct endpoint.
// AppointmentStatus enum: PENDING | CONFIRMED | CANCELLED | COMPLETED
// ---------------------------------------------------------------------------
async function updateAppointmentStatus(id, status, reason = '') {
    const s = status.toUpperCase();
    if (s === 'CONFIRMED') return confirmAppointment(id);
    if (s === 'CANCELLED') return doctorCancelAppointment(id, reason);
    if (s === 'COMPLETED') return completeAppointment(id);
    throw new Error('Unsupported status: ' + status);
}

// ---------------------------------------------------------------------------
// Appointment: Patient Cancel  [PATIENT]
// PATCH /api/appointments/{id}/patient-cancel
// Body: CancelRequest { cancelReason }
// ---------------------------------------------------------------------------
async function patientCancelAppointment(id, cancelReason) {
    return await apiFetch(`/api/appointments/${id}/patient-cancel`, {
        method: 'PATCH',
        body: JSON.stringify({ cancelReason })
    });
}

// ---------------------------------------------------------------------------
// Appointment: Reschedule  [PATIENT]
// PATCH /api/appointments/{id}/reschedule
// Body: AppointmentRequestDTO — backend reads appointmentDate + appointmentTime
// @param appointmentDate  string  YYYY-MM-DD
// @param appointmentTime  string  HH:mm:ss  (seconds mandatory for LocalTime parsing)
// ---------------------------------------------------------------------------
async function rescheduleAppointment(id, appointmentDate, appointmentTime) {
    return await apiFetch(`/api/appointments/${id}/reschedule`, {
        method: 'PATCH',
        body: JSON.stringify({ appointmentDate, appointmentTime })
    });
}

// ---------------------------------------------------------------------------
// Appointment: Get booked slots for a doctor on a specific date  [PATIENT]
// GET /api/appointments/booked-slots/{doctorId}?date=YYYY-MM-DD
// Returns list of booked time strings ("HH:mm:ss") for that doctor on that date only.
// Date-scoped: 09:00 on Monday will NOT block 09:00 on Tuesday.
// @param {number|string} doctorId
// @param {string} date  YYYY-MM-DD
// ---------------------------------------------------------------------------
async function getBookedSlots(doctorId, date) {
    return await apiFetch('/api/appointments/booked-slots/' + doctorId + '?date=' + date);
}

// ---------------------------------------------------------------------------
// Payment: Patient history  [PATIENT]
// GET /api/patient/payments  → List<PaymentResponseDTO> (no wrapper)
// PaymentResponseDTO: { id, paymentStatus, amount, paymentMethod, appointmentId }
// ---------------------------------------------------------------------------
async function getPatientPayments() {
    return await apiFetch('/api/patient/payments');
}

// ---------------------------------------------------------------------------
// Payment: Process  [authenticated — no role restriction in SecurityConfig]
// POST /api/payments
// Body: PaymentRequestDTO { amount, paymentMethod (CASH|CREDIT_CARD), appointmentId, stripeToken? }
// ---------------------------------------------------------------------------
async function processPayment(data) {
    return await apiFetch('/api/payments', {
        method: 'POST',
        body: JSON.stringify(data)
    });
}
