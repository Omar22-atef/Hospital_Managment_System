package com.project.HospitalManagmentSystem.mapper;

import com.project.HospitalManagmentSystem.Entity.Doctor;
import com.project.HospitalManagmentSystem.dto.DoctorRequestDTO;
import com.project.HospitalManagmentSystem.dto.DoctorResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    Doctor toEntity(DoctorRequestDTO dto);
    DoctorResponseDTO toResponseDTO(Doctor entity);
}
