package com.project.HospitalManagmentSystem.Mapper;

import com.project.HospitalManagmentSystem.entity.Appointment;
import com.project.HospitalManagmentSystem.dto.AppointmentRequestDTO;
import com.project.HospitalManagmentSystem.dto.AppointmentResponseDTO;
import com.project.HospitalManagmentSystem.enums.AppointmentStatus;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

    public Appointment toEntity(AppointmentRequestDTO dto) {
        if (dto == null) return null;

        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setAppointmentTime(dto.getAppointmentTime());
        appointment.setStatus(AppointmentStatus.PENDING);

        return appointment;
    }

    public AppointmentResponseDTO toDTO(Appointment entity) {
        if (entity == null) return null;

        return AppointmentResponseDTO.builder()
                .id(entity.getId())
                .appointmentDate(entity.getAppointmentDate())
                .appointmentTime(entity.getAppointmentTime())
                .status(entity.getStatus())
                .patientId(entity.getPatient().getId())
                .doctorId(entity.getDoctor().getId())
                .build();
    }
}