package com.project.HospitalManagmentSystem.mapper;

import com.project.HospitalManagmentSystem.Entity.Admin;
import com.project.HospitalManagmentSystem.dto.AdminRequestDTO;
import com.project.HospitalManagmentSystem.dto.AdminResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdminMapper {
    Admin toEntity(AdminRequestDTO dto);
    AdminResponseDTO toResponseDTO(Admin entity);
}
