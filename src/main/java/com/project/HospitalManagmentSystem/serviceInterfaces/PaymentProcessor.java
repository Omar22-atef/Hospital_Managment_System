package com.project.HospitalManagmentSystem.serviceInterfaces;
import com.project.HospitalManagmentSystem.dto.PaymentRequestDTO;
import com.project.HospitalManagmentSystem.entity.Appointment;
import com.project.HospitalManagmentSystem.entity.Payment;
import java.math.BigDecimal;

public interface PaymentProcessor {
    Payment process(BigDecimal amount, PaymentRequestDTO request, Appointment appointment);
}

