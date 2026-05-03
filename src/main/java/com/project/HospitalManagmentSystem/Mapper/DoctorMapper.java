package com.project.HospitalManagmentSystem.Mapper;

import com.project.HospitalManagmentSystem.entity.Doctor;
import com.project.HospitalManagmentSystem.dto.DoctorRequestDTO;
import com.project.HospitalManagmentSystem.dto.DoctorResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {

    public Doctor toEntity(DoctorRequestDTO dto) {
        if (dto == null) return null;

        Doctor doctor = new Doctor();
        doctor.setName(dto.getName());
        doctor.setEmail(dto.getEmail());
        doctor.setPassword(dto.getPassword());
        doctor.setPhone(dto.getPhone());
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setDayOfWeek(dto.getDayOfWeek());
        doctor.setStartTime(dto.getStartTime());
        doctor.setEndTime(dto.getEndTime());

        return doctor;
    }

    public DoctorResponseDTO toDTO(Doctor entity) {
        if (entity == null) return null;

        return DoctorResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .specialization(entity.getSpecialization())
                .dayOfWeek(entity.getDayOfWeek())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .build();
    }
}