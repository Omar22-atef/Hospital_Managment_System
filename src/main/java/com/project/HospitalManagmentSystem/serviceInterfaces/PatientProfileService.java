package com.project.HospitalManagmentSystem.serviceInterfaces;

import com.project.HospitalManagmentSystem.dto.PatientResponseDTO;
import com.project.HospitalManagmentSystem.dto.PatientUpdateRequestDTO;

public interface PatientProfileService {
    PatientResponseDTO getPatientById(Long id);
    PatientResponseDTO updatePatient(Long id, PatientUpdateRequestDTO dto);
}
