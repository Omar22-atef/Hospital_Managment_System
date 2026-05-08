package com.project.HospitalManagmentSystem.listener;

import com.project.HospitalManagmentSystem.event.PaymentCompletedEvent;
import com.project.HospitalManagmentSystem.service.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentListener {

    private final EmailService emailService;

    public PaymentListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventListener
    public void handlePaymentCompleted(PaymentCompletedEvent event) {

        emailService.sendReservationConfirmationEmail(
                event.getPayment()
        );
    }
}