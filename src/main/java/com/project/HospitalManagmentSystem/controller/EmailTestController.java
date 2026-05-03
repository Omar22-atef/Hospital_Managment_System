package com.project.HospitalManagmentSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.project.HospitalManagmentSystem.service.EmailService;

@RestController
@RequestMapping("/test")
public class EmailTestController {

    //@Autowired
    private EmailService emailService;

    @GetMapping("/email")
    public String sendTestEmail() {
        emailService.sendCancellationEmail("gerabiteg@gmail.com", "Testing email service");
        return "Email sent!";
    }
}