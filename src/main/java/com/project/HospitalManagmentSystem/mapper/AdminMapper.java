package com.project.HospitalManagmentSystem.mapper;

import com.project.HospitalManagmentSystem.entity.Admin;
import com.project.HospitalManagmentSystem.dto.AdminRequestDTO;
import com.project.HospitalManagmentSystem.dto.AdminResponseDTO;
import org.mapstruct.Mapper;

public interface AdminMapper {
    public static Admin toEntity(AdminRequestDTO dto) {
        if (dto == null) return null;

        Admin admin = new Admin();
        admin.setName(dto.getName());
        admin.setEmail(dto.getEmail());
        admin.setPassword(dto.getPassword());

        return admin;
    }

    // 🔹 Entity → Response
    public static AdminResponseDTO toDTO(Admin entity) {
        if (entity == null) return null;

        return AdminResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .build();
    }
}
