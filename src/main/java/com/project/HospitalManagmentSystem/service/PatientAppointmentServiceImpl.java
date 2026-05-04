package com.project.HospitalManagmentSystem.service;

import com.project.HospitalManagmentSystem.dto.AppointmentResponseDTO;
import com.project.HospitalManagmentSystem.entity.Appointment;
import com.project.HospitalManagmentSystem.mapper.AppointmentMapper;
import com.project.HospitalManagmentSystem.repository.AppointmentRepository;
import com.project.HospitalManagmentSystem.serviceInterfaces.PatientAppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientAppointmentServiceImpl implements PatientAppointmentService {
    private final AppointmentRepository appointmentRepository;

    @Override
    public List<AppointmentResponseDTO> getAllAppointments(String email)
    {
        List<Appointment> appointments = appointmentRepository.findByPatientEmail(email);
        return appointments.stream()
                .map(AppointmentMapper::toDTO)
                .toList();
    }

}
