package com.project.HospitalManagmentSystem.service;import com.project.HospitalManagmentSystem.serviceInterfaces.PaymentProcessor;
import com.project.HospitalManagmentSystem.Payment.PaymentFactory;
import com.project.HospitalManagmentSystem.entity.Payment;
import com.project.HospitalManagmentSystem.enums.PaymentStatus;
import com.project.HospitalManagmentSystem.enums.PaymentMethod;
import java.util.Map;
import java.util.HashMap;
import java.math.BigDecimal;
import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.stripe.exception.StripeException;
import com.project.HospitalManagmentSystem.dto.PaymentRequestDTO;
import com.project.HospitalManagmentSystem.dto.PaymentResponseDTO;
import com.project.HospitalManagmentSystem.entity.Appointment;
import com.project.HospitalManagmentSystem.entity.Payment;
import com.project.HospitalManagmentSystem.repository.PaymentRepository;
import com.project.HospitalManagmentSystem.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.ApplicationEventPublisher;
import com.project.HospitalManagmentSystem.event.PaymentCompletedEvent;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final AppointmentRepository appointmentRepository;
    private final ApplicationEventPublisher publisher;


    // Inside PaymentService.java
    public PaymentResponseDTO createPayment(PaymentRequestDTO request) {
        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        BigDecimal amount = appointment.getDoctor().getConsultationFee();

        // 1. Get the right processor from the Factory
        PaymentProcessor processor = PaymentFactory.getProcessor(request.getPaymentMethod());

        // 2. Process the payment logic (Stripe or Cash)
        Payment payment = processor.process(amount, request, appointment);

        // 3. Save and Publish
        Payment savedPayment = paymentRepository.save(payment);
        publisher.publishEvent(new PaymentCompletedEvent(savedPayment));

        return mapToResponse(savedPayment);
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
                .build();
    }
}