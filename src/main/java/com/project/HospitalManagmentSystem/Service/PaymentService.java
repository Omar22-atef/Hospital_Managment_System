package com.project.HospitalManagmentSystem.Service;

import com.project.HospitalManagmentSystem.dto.PaymentRequestDTO;
import com.project.HospitalManagmentSystem.dto.PaymentResponseDTO;
import com.project.HospitalManagmentSystem.Entity.Payment;
import com.project.HospitalManagmentSystem.Repository.PaymentRepository;
import com.project.HospitalManagmentSystem.Repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final AppointmentRepository appointmentRepository;

    public PaymentResponseDTO createPayment(PaymentRequestDTO request) {
        var appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        Payment payment = Payment.builder()
                .amount(request.getAmount())
                .paymentStatus(request.getPaymentStatus())
                .paymentMethod(request.getPaymentMethod())
                .appointment(appointment)
                .build();

        Payment saved = paymentRepository.save(payment);
        return mapToResponse(saved);
    }

    public List<PaymentResponseDTO> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private PaymentResponseDTO mapToResponse(Payment p) {
        return PaymentResponseDTO.builder()
                .id(p.getId())
                .amount(p.getAmount())
                .paymentStatus(p.getPaymentStatus())
                .paymentMethod(p.getPaymentMethod())
                .appointmentId(p.getAppointment().getId())
                .createdAt(p.getCreatedAt())
                .updatedAt(p.getUpdatedAt())
                .build();
    }
}