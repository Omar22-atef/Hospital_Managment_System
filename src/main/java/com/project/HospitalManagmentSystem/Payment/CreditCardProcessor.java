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

public class CreditCardProcessor implements PaymentProcessor {
    private static final String STRIPE_SECRET_KEY = "sk_test_51TSiJ0Q5CVl6R8ElZb3hHeFtozP1lwxw9XIcQeNsLBIg0nAb06rkzBwKMDnY5WVlBE9psWX4coWKUvDwcCzbv2i300Mb5jzS8B";

    @Override
    public Payment process(BigDecimal amount, PaymentRequestDTO request, Appointment appointment) {
        Stripe.apiKey = STRIPE_SECRET_KEY;
        if (request.getStripeToken() == null || request.getStripeToken().isBlank()) {
            throw new RuntimeException("Stripe token is required");
        }

        try {
            Map<String, Object> params = new HashMap<>();
            params.put("amount", amount.multiply(new BigDecimal(100)).intValue());
            params.put("currency", "usd");
            params.put("source", request.getStripeToken());

            Charge charge = Charge.create(params);

            return Payment.builder()
                    .amount(amount)
                    .paymentStatus(PaymentStatus.PAID)
                    .paymentMethod(PaymentMethod.CREDIT_CARD)
                    .stripePaymentId(charge.getId())
                    .appointment(appointment)
                    .build();
        } catch (StripeException e) {
            throw new RuntimeException("Stripe Payment Failed: " + e.getMessage());
        }
    }
}