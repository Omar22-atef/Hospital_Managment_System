package com.project.HospitalManagmentSystem.Payment;

import com.project.HospitalManagmentSystem.serviceInterfaces.PaymentProcessor;

import com.project.HospitalManagmentSystem.dto.PaymentRequestDTO;
import com.project.HospitalManagmentSystem.entity.Payment;
import com.project.HospitalManagmentSystem.entity.Appointment;
import com.project.HospitalManagmentSystem.enums.PaymentStatus;
import com.project.HospitalManagmentSystem.enums.PaymentMethod;
import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.stripe.exception.StripeException;

import java.util.Map;
import java.util.HashMap;
import java.math.BigDecimal;

public class CashProcessor implements PaymentProcessor {
    @Override
    public Payment process(BigDecimal amount, PaymentRequestDTO request, Appointment appointment) {
        return Payment.builder()
                .amount(amount)
                .paymentStatus(PaymentStatus.PAID)
                .paymentMethod(PaymentMethod.CASH)
                .appointment(appointment)
                .build();
    }
}
