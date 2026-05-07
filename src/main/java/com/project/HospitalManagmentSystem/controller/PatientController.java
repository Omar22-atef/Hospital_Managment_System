package com.project.HospitalManagmentSystem.controller;

import com.project.HospitalManagmentSystem.dto.*;
import com.project.HospitalManagmentSystem.service.AdminFacadeService;
import com.project.HospitalManagmentSystem.serviceInterfaces.PatientAppointmentService;
import com.project.HospitalManagmentSystem.serviceInterfaces.PatientPaymentService;
import com.project.HospitalManagmentSystem.serviceInterfaces.PatientProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientProfileService profileService;
    private final PatientAppointmentService appointmentService;
    private final PatientPaymentService paymentService;
    private final AdminFacadeService adminFacade;

    // Get my profile
    @GetMapping("/me")
    public ResponseEntity<PatientResponseDTO> getMyProfile(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(profileService.getPatientByEmail(email));
    }

    // Update my profile
    @PutMapping("/me")
    public ResponseEntity<PatientResponseDTO> updatePatient(
            Authentication authentication,
            @Valid @RequestBody PatientUpdateRequestDTO dto) {

        String email = authentication.getName();
        return ResponseEntity.ok(profileService.updatePatientByEmail(email, dto));
    }

    @GetMapping("/doctors")
    public ResponseEntity<ApiResponse<List<DoctorResponseDTO>>> getAllDoctors() {
        return ResponseEntity.ok(new ApiResponse<>("success", adminFacade.getAllDoctors()));
    }

    // Get my appointments
    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointments(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(appointmentService.getAllAppointments(email));
    }

    // Get my payments
    @GetMapping("/payments")
    public ResponseEntity<List<PaymentResponseDTO>> getPayments(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(paymentService.getPayments(email));
    }
}