package com.project.HospitalManagmentSystem.service;

import com.project.HospitalManagmentSystem.dto.AppointmentResponseDTO;
import com.project.HospitalManagmentSystem.entity.Appointment;
import com.project.HospitalManagmentSystem.Mapper.AppointmentMapper;
import com.project.HospitalManagmentSystem.Repository.AppointmentRepository;
import com.project.HospitalManagmentSystem.serviceInterfaces.PatientAppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientAppointmentServiceImpl implements PatientAppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;

    @Override
    public List<AppointmentResponseDTO> getAllAppointments(Long patientId) {
        List<Appointment> appointments = appointmentRepository.findByPatientId(patientId);
        return appointments.stream()
                .map(appointmentMapper::toDTO)
                .toList();
    }
}