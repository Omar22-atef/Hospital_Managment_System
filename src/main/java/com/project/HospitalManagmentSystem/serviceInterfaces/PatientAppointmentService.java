package com.project.HospitalManagmentSystem.serviceInterfaces;

import com.project.HospitalManagmentSystem.dto.AppointmentResponseDTO;

import java.util.List;

public interface PatientAppointmentService {
    List<AppointmentResponseDTO> getAllAppointments(Long patientId);
}
