package com.project.HospitalManagmentSystem.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendCancellationEmail(String to, String reason) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Appointment Cancelled");

        message.setText(
                "Dear Patient,\n\n" +
                "Your appointment has been cancelled.\n\n" +
                "Reason: " + reason + "\n\n" +
                "Regards,\nHospital System"
        );

        mailSender.send(message);
    }
}