package com.project.HospitalManagmentSystem.service;

import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import com.project.HospitalManagmentSystem.event.AppointmentCancelledEvent;
import com.project.HospitalManagmentSystem.entity.Appointment;
import com.project.HospitalManagmentSystem.entity.Doctor;
import com.project.HospitalManagmentSystem.enums.AppointmentStatus;
import com.project.HospitalManagmentSystem.repository.AppointmentRepository;
import com.project.HospitalManagmentSystem.repository.DoctorRepository;
import com.project.HospitalManagmentSystem.dto.AppointmentResponseDTO;
import com.project.HospitalManagmentSystem.dto.DoctorResponseDTO;
import com.project.HospitalManagmentSystem.mapper.AppointmentMapper;
import com.project.HospitalManagmentSystem.mapper.DoctorMapper;


@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final ApplicationEventPublisher publisher;

    public DoctorService(DoctorRepository doctorRepository,
                         AppointmentRepository appointmentRepository,
                        ApplicationEventPublisher publisher) {
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
        this.publisher = publisher;
    }

    
    public DoctorResponseDTO getDoctorByEmail(String email) {
        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        return DoctorMapper.toDTO(doctor);
    }

    
    public List<AppointmentResponseDTO> getDoctorAppointmentsByEmail(String email) {
        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        return appointmentRepository.findByDoctor(doctor)
                .stream()
                .map(AppointmentMapper::toDTO)
                .toList();
    }

    
    public void cancelAppointment(String email, Long appointmentId, String reason) {

        if (reason == null || reason.isBlank()) {
            throw new RuntimeException("Cancellation reason is required");
        }

        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        // 🔒 Ensure doctor owns this appointment
        if (!appointment.getDoctor().getId().equals(doctor.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            throw new RuntimeException("Already cancelled");
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointment.setCancellationReason(reason);
        

        appointmentRepository.save(appointment);

    
       publisher.publishEvent(
     new AppointmentCancelledEvent(appointment));
    }
}