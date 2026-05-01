package com.project.HospitalManagmentSystem.service;

import org.springframework.stereotype.Service;

import java.util.List;

import com.project.HospitalManagmentSystem.entity.Appointment;
import com.project.HospitalManagmentSystem.entity.Doctor;
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

    private final DoctorMapper doctorMapper;
    private final AppointmentMapper appointmentMapper;

   
    public DoctorService(DoctorRepository doctorRepository,
                         AppointmentRepository appointmentRepository,
                         DoctorMapper doctorMapper,
                         AppointmentMapper appointmentMapper) {
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
        this.doctorMapper = doctorMapper;
        this.appointmentMapper = appointmentMapper;
    }

   
    public DoctorResponseDTO getDoctorById(Long id) {
        Doctor doctor = findDoctorOrThrow(id);
        return DoctorMapper.toDTO(doctor);
    }

    
    public List<AppointmentResponseDTO> getDoctorAppointments(Long doctorId) {

        Doctor doctor = findDoctorOrThrow(doctorId);

        List<Appointment> appointments = appointmentRepository.findByDoctor(doctor);

        return appointments.stream()
                .map(AppointmentMapper::toDTO)
                .toList();
    }

    
    private Doctor findDoctorOrThrow(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
    }
}