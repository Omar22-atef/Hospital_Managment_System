package com.project.HospitalManagmentSystem.controller;

import com.project.HospitalManagmentSystem.dto.AppointmentResponseDTO;
import com.project.HospitalManagmentSystem.dto.PatientResponseDTO;
import com.project.HospitalManagmentSystem.dto.PatientUpdateRequestDTO;
import com.project.HospitalManagmentSystem.dto.PaymentResponseDTO;
import com.project.HospitalManagmentSystem.serviceInterfaces.PatientAppointmentService;
import com.project.HospitalManagmentSystem.serviceInterfaces.PatientPaymentService;
import com.project.HospitalManagmentSystem.serviceInterfaces.PatientProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patient")
@RequiredArgsConstructor
public class PatientController {
    private final PatientProfileService profileService;
    private final PatientAppointmentService appointmentService;
    private final PatientPaymentService paymentService;

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> getPatient(@PathVariable Long id) {
        return ResponseEntity.ok(profileService.getPatientById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatient(
            @PathVariable Long id,
           @Valid @RequestBody PatientUpdateRequestDTO dto) {

        return ResponseEntity.ok(profileService.updatePatient(id, dto));
    }

    @GetMapping("/{id}/appointments")
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointments(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getAllAppointments(id));
    }

    @GetMapping("/{id}/payments")
    public ResponseEntity<List<PaymentResponseDTO>> getPayments(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getPayments(id));
    }
}
