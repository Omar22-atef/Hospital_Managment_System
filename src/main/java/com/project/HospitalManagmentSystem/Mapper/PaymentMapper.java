package com.project.HospitalManagmentSystem.Mapper;

import com.project.HospitalManagmentSystem.entity.Payment;
import com.project.HospitalManagmentSystem.dto.PaymentRequestDTO;
import com.project.HospitalManagmentSystem.dto.PaymentResponseDTO;
import com.project.HospitalManagmentSystem.enums.PaymentStatus;

public interface PaymentMapper {
    public static Payment toEntity(PaymentRequestDTO dto) {
        if (dto == null) return null;

        Payment payment = new Payment();
        payment.setAmount(dto.getAmount());
        payment.setPaymentMethod(dto.getPaymentMethod());

        payment.setPaymentStatus(PaymentStatus.PENDING);

        return payment;
    }

    public static PaymentResponseDTO toDTO(Payment entity) {
        if (entity == null) return null;

        return PaymentResponseDTO.builder()
                .id(entity.getId())
                .amount(entity.getAmount())
                .paymentMethod(entity.getPaymentMethod())
                .paymentStatus(entity.getPaymentStatus())
                .appointmentId(entity.getAppointment().getId())
                .build();
    }
}
