package com.project.HospitalManagmentSystem.service;
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

    private static final String STRIPE_SECRET_KEY =
            "sk_test_51TSiJ0Q5CVl6R8ElZb3hHeFtozP1lwxw9XIcQeNsLBIg0nAb06rkzBwKMDnY5WVlBE9psWX4coWKUvDwcCzbv2i300Mb5jzS8B";

    public PaymentResponseDTO createPayment(PaymentRequestDTO request) {

        // Look up the appointment to derive the consultation fee from the doctor
        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        // The amount is the doctor's consultation fee — NOT supplied by the client
        BigDecimal amount = appointment.getDoctor().getConsultationFee();
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Doctor consultation fee is not configured");
        }

        Payment payment;

        if (request.getPaymentMethod() == PaymentMethod.CREDIT_CARD) {
            // ── Stripe credit card charge ──────────────────────────────
            Stripe.apiKey = STRIPE_SECRET_KEY;

            if (request.getStripeToken() == null || request.getStripeToken().isBlank()) {
                throw new RuntimeException("Stripe token is required for credit card payments");
            }

            try {
                Map<String, Object> params = new HashMap<>();
                // Stripe uses integer cents: $10.00 → 1000
                params.put("amount", amount.multiply(new BigDecimal(100)).intValue());
                params.put("currency", "usd");
                params.put("description", "Hospital Consultation – Appointment #" + request.getAppointmentId());
                params.put("source", request.getStripeToken());

                Charge charge = Charge.create(params);

                payment = Payment.builder()
                        .amount(amount)
                        .paymentStatus(PaymentStatus.PAID)
                        .paymentMethod(PaymentMethod.CREDIT_CARD)
                        .stripePaymentId(charge.getId())
                        .appointment(appointment)
                        .build();

            } catch (StripeException e) {
                throw new RuntimeException("Stripe Payment Failed: " + e.getMessage());
            }

        } else {
            // ── Cash payment — record immediately as PAID ──────────────
            payment = Payment.builder()
                    .amount(amount)
                    .paymentStatus(PaymentStatus.PAID)
                    .paymentMethod(PaymentMethod.CASH)
                    .appointment(appointment)
                    .build();
        }

        Payment savedPayment = paymentRepository.save(payment);

        publisher.publishEvent(
        new PaymentCompletedEvent(savedPayment)
        );

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