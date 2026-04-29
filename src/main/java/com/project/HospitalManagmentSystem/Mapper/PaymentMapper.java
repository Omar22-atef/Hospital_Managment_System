package com.project.HospitalManagmentSystem.mapper;

import com.project.HospitalManagmentSystem.Entity.Payment;
import com.project.HospitalManagmentSystem.dto.PaymentRequestDTO;
import com.project.HospitalManagmentSystem.dto.PaymentResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    @Mapping(target = "appointment.id", source = "appointmentId")
    Payment toEntity(PaymentRequestDTO dto);

    @Mapping(target = "appointmentId", source = "appointment.id")
    PaymentResponseDTO toResponseDTO(Payment entity);
}
