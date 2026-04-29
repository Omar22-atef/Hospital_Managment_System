package com.project.HospitalManagmentSystem.dto;

import com.project.HospitalManagmentSystem.enums.PaymentMethod;
import com.project.HospitalManagmentSystem.enums.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDTO {
    @NotNull(message = "Payment Status is required")
    private PaymentStatus paymentStatus;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @NotNull(message = "Payment Method is required")
    private PaymentMethod paymentMethod;

    @NotNull(message = "Appointment ID is required")
    private Long appointmentId;
}
