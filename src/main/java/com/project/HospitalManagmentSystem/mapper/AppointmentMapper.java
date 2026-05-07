package com.project.HospitalManagmentSystem.mapper;

import com.project.HospitalManagmentSystem.entity.Appointment;
import com.project.HospitalManagmentSystem.dto.AppointmentRequestDTO;
import com.project.HospitalManagmentSystem.dto.AppointmentResponseDTO;
import com.project.HospitalManagmentSystem.enums.AppointmentStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

public interface AppointmentMapper {
    public static Appointment toEntity(AppointmentRequestDTO dto) {
        if (dto == null) return null;

        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setAppointmentTime(dto.getAppointmentTime());
        appointment.setStatus(AppointmentStatus.PENDING);

        return appointment;
    }

    public static AppointmentResponseDTO toDTO(Appointment entity) {
        if (entity == null) return null;

        return AppointmentResponseDTO.builder()
                .id(entity.getId())
                .appointmentDate(entity.getAppointmentDate())
                .appointmentTime(entity.getAppointmentTime())
                .status(entity.getStatus())
                .patientId(entity.getPatient().getId())
                .doctorId(entity.getDoctor().getId())
                .doctorName(entity.getDoctor().getName())
                .build();
    }
}
