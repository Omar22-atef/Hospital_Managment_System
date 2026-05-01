package com.project.HospitalManagmentSystem.dto;

import com.project.HospitalManagmentSystem.enums.PaymentMethod;
import com.project.HospitalManagmentSystem.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDTO {
    private Long id;
    private PaymentStatus paymentStatus;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    private Long appointmentId;
}
