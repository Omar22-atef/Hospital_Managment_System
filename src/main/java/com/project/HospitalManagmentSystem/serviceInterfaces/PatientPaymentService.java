package com.project.HospitalManagmentSystem.serviceInterfaces;

import com.project.HospitalManagmentSystem.dto.PaymentResponseDTO;

import java.util.List;

public interface PatientPaymentService {
    List<PaymentResponseDTO> getPayments(String email);
}
