package com.project.HospitalManagmentSystem.service;

import com.project.HospitalManagmentSystem.dto.PaymentResponseDTO;
import com.project.HospitalManagmentSystem.entity.Payment;
import com.project.HospitalManagmentSystem.mapper.PaymentMapper;
import com.project.HospitalManagmentSystem.repository.PatientRepository;
import com.project.HospitalManagmentSystem.repository.PaymentRepository;
import com.project.HospitalManagmentSystem.serviceInterfaces.PatientPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientPaymentServiceImpl implements PatientPaymentService {
    private final PaymentRepository paymentRepository;

    public List<PaymentResponseDTO> getPayments(Long patientId)
    {
        List<Payment> payments = paymentRepository.findByAppointmentPatientId(patientId);

        return payments.stream().map(PaymentMapper::toDTO).toList();
    }
}
