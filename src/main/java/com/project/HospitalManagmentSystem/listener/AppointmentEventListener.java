package com.project.HospitalManagmentSystem.listener;

import com.project.HospitalManagmentSystem.event.AppointmentCancelledEvent;
import com.project.HospitalManagmentSystem.service.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AppointmentEventListener {

    private final EmailService emailService;

    public AppointmentEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventListener
    public void handleAppointmentCancelled(AppointmentCancelledEvent event) {

        emailService.sendCancellationEmail(
                event.getAppointment().getPatient().getEmail(),
                event.getAppointment().getCancellationReason()
        );
    }
}