package com.project.HospitalManagmentSystem.service;

import com.project.HospitalManagmentSystem.repository.*;

import com.project.HospitalManagmentSystem.entity.Doctor;

import com.project.HospitalManagmentSystem.dto.DoctorRequestDTO;
import com.project.HospitalManagmentSystem.dto.DoctorResponseDTO;
import com.project.HospitalManagmentSystem.dto.PatientResponseDTO;
import com.project.HospitalManagmentSystem.repository.PatientRepository;
import com.project.HospitalManagmentSystem.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminFacadeService {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final com.project.HospitalManagmentSystem.repository.PaymentRepository paymentRepository;
    private final PasswordEncoder passwordEncoder;

    // --- 1. Dashboard Logic ---
    public Map<String, Object> getDashboardData() {
        return Map.of(
                "totalPatients", patientRepository.count(),
                "totalDoctors", doctorRepository.count(),
                "totalAppointments", appointmentRepository.count(),
                "totalPayments", paymentRepository.count()
        );
    }

    // --- 2. Doctor CRUD Operations ---

    public List<DoctorResponseDTO> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(this::mapToDoctorResponse)
                .collect(Collectors.toList());
    }

    public DoctorResponseDTO addDoctor(DoctorRequestDTO dto) {
        Doctor doctor = Doctor.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .specialization(dto.getSpecialization())
                .phone(dto.getPhone())
                .dayOfWeek(dto.getDayOfWeek())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .build();

        return mapToDoctorResponse(doctorRepository.save(doctor));
    }

    public DoctorResponseDTO updateDoctor(Long id, DoctorRequestDTO dto) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));

        doctor.setName(dto.getName());
        doctor.setEmail(dto.getEmail());
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setPhone(dto.getPhone());
        doctor.setDayOfWeek(dto.getDayOfWeek());
        doctor.setStartTime(dto.getStartTime());
        doctor.setEndTime(dto.getEndTime());

        return mapToDoctorResponse(doctorRepository.save(doctor));
    }

    public void deleteDoctor(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new RuntimeException("Doctor not found with id: " + id);
        }
        doctorRepository.deleteById(id);
    }

    // --- 3. Patient Management ---

    public List<PatientResponseDTO> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(p -> PatientResponseDTO.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .email(p.getEmail())
                        .phone(p.getPhone())
                        .dateOfBirth(p.getDateOfBirth())
                        .build())
                .collect(Collectors.toList());
    }

    // --- Helper Mapper ---
    private DoctorResponseDTO mapToDoctorResponse(Doctor d) {
        return DoctorResponseDTO.builder()
                .id(d.getId())
                .name(d.getName())
                .email(d.getEmail())
                .specialization(d.getSpecialization())
                .phone(d.getPhone())
                .dayOfWeek(d.getDayOfWeek())
                .startTime(d.getStartTime())
                .endTime(d.getEndTime())
                .createdAt(d.getCreatedAt())
                .updatedAt(d.getUpdatedAt())
                .build();
    }
}