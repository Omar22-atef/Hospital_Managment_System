package com.project.HospitalManagmentSystem.Service;
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
import com.project.HospitalManagmentSystem.entity.Payment;
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
        // 1. Initialize Stripe (Use a test key from Stripe dashboard)
        Stripe.apiKey = "sk_test_your_key_here";

        try {
            // 2. Create the Charge params
            Map<String, Object> params = new HashMap<>();
            // Stripe uses cents (e.g., $10.00 = 1000)
            params.put("amount", request.getAmount().multiply(new BigDecimal(100)).intValue());
            params.put("currency", "usd");
            params.put("description", "Hospital Bill - Appointment #" + request.getAppointmentId());
            params.put("source", request.getStripeToken());

            // 3. Make the actual charge
            Charge charge = Charge.create(params);

            // 4. If successful, save to your DB
            var appointment = appointmentRepository.findById(request.getAppointmentId())
                    .orElseThrow(() -> new RuntimeException("Appointment not found"));

            Payment payment = Payment.builder()
                    .amount(request.getAmount())
                    .paymentStatus(PaymentStatus.PAID)
                    .paymentMethod(PaymentMethod.CREDIT_CARD)
                    .stripePaymentId(charge.getId()) // Save the Stripe ID!
                    .appointment(appointment)
                    .build();

            return mapToResponse(paymentRepository.save(payment));

        } catch (StripeException e) {
            throw new RuntimeException("Stripe Payment Failed: " + e.getMessage());
        }
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