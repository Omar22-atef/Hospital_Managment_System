package com.project.HospitalManagmentSystem.Service;

import org.springframework.stereotype.Service;

import java.util.List;

import com.project.HospitalManagmentSystem.Entity.Appointment;
import com.project.HospitalManagmentSystem.Entity.Doctor;
import com.project.HospitalManagmentSystem.Repository.AppointmentRepository;
import com.project.HospitalManagmentSystem.Repository.DoctorRepository;
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
        return doctorMapper.toResponseDTO(doctor);
    }

    
    public List<AppointmentResponseDTO> getDoctorAppointments(Long doctorId) {

        Doctor doctor = findDoctorOrThrow(doctorId);

        List<Appointment> appointments = appointmentRepository.findByDoctor(doctor);

        return appointments.stream()
                .map(appointmentMapper::toResponseDTO)
                .toList();
    }

    
    private Doctor findDoctorOrThrow(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
    }
}