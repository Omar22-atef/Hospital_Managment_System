package com.project.HospitalManagmentSystem.mapper;

import com.project.HospitalManagmentSystem.Entity.Patient;
import com.project.HospitalManagmentSystem.dto.PatientRequestDTO;
import com.project.HospitalManagmentSystem.dto.PatientResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    Patient toEntity(PatientRequestDTO dto);
    PatientResponseDTO toResponseDTO(Patient entity);
}
