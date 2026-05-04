package com.project.HospitalManagmentSystem.service;

import com.project.HospitalManagmentSystem.dto.PatientResponseDTO;
import com.project.HospitalManagmentSystem.dto.PatientUpdateRequestDTO;
import com.project.HospitalManagmentSystem.entity.Patient;
import com.project.HospitalManagmentSystem.mapper.PatientMapper;
import com.project.HospitalManagmentSystem.repository.PatientRepository;
import com.project.HospitalManagmentSystem.serviceInterfaces.PatientProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientProfileServiceImpl implements PatientProfileService {
    private final PatientRepository patientRepository;

    @Override
    public PatientResponseDTO getPatientById(Long id)
    {
        Patient patient = patientRepository.findById(id).orElseThrow(()-> new RuntimeException("Patient Not Found"));
        return PatientMapper.toDTO(patient);
    }

    @Override
    public PatientResponseDTO updatePatient(Long id, PatientUpdateRequestDTO dto)
    {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new RuntimeException("Patient Not Found"));
        PatientMapper.updatePatient(patient, dto);

        return PatientMapper.toDTO(patientRepository.save(patient));
    }

    public PatientResponseDTO getPatientByEmail(String email) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Patient Not Found"));

        return PatientMapper.toDTO(patient);
    }

    public PatientResponseDTO updatePatientByEmail(String email, PatientUpdateRequestDTO dto) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Patient Not Found"));

        PatientMapper.updatePatient(patient, dto);

        return PatientMapper.toDTO(patientRepository.save(patient));
    }
}