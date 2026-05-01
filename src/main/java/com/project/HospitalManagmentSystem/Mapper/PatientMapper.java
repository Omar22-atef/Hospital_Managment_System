package com.project.HospitalManagmentSystem.mapper;

import com.project.HospitalManagmentSystem.dto.PatientUpdateRequestDTO;
import com.project.HospitalManagmentSystem.entity.Patient;
import com.project.HospitalManagmentSystem.dto.PatientResponseDTO;

public interface PatientMapper {
    public static PatientResponseDTO toDTO(Patient patient) {
        if (patient == null) return null;

        return PatientResponseDTO.builder()
                .id(patient.getId())
                .name(patient.getName())
                .email(patient.getEmail())
                .phone(patient.getPhone())
                .dateOfBirth(patient.getDateOfBirth())
                .build();
    }

    public static void updatePatient(Patient patient, PatientUpdateRequestDTO dto) {
        if (dto == null) return;

        if (dto.getName() != null)
            patient.setName(dto.getName());

        if (dto.getPhone() != null)
            patient.setPhone(dto.getPhone());

        if (dto.getDateOfBirth() != null)
            patient.setDateOfBirth(dto.getDateOfBirth());
    }
}
