package com.project.HospitalManagmentSystem.mapper;

import com.project.HospitalManagmentSystem.Entity.Appointment;
import com.project.HospitalManagmentSystem.dto.AppointmentRequestDTO;
import com.project.HospitalManagmentSystem.dto.AppointmentResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PatientMapper.class, DoctorMapper.class})
public interface AppointmentMapper {
    @Mapping(target = "patient.id", source = "patientId")
    @Mapping(target = "doctor.id", source = "doctorId")
    Appointment toEntity(AppointmentRequestDTO dto);

    AppointmentResponseDTO toResponseDTO(Appointment entity);
}
