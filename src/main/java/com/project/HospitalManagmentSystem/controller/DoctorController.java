package com.project.HospitalManagmentSystem.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.project.HospitalManagmentSystem.service.DoctorService;
import com.project.HospitalManagmentSystem.dto.ApiResponse;
import com.project.HospitalManagmentSystem.dto.DoctorResponseDTO;
import com.project.HospitalManagmentSystem.dto.AppointmentResponseDTO;
import org.springframework.security.core.Authentication;
import com.project.HospitalManagmentSystem.dto.CancelRequest;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    private final DoctorService doctorService;

    
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    
   @GetMapping("/me")
public ApiResponse<DoctorResponseDTO> getMyProfile(Authentication authentication) {

    String email = authentication.getName();

    return new ApiResponse<>(
            "Doctor fetched successfully",
            doctorService.getDoctorByEmail(email)
    );
}

    
    @GetMapping("/appointments")
public ApiResponse<List<AppointmentResponseDTO>> getAppointments(Authentication authentication) {

    String email = authentication.getName();

    return new ApiResponse<>(
            "Appointments fetched successfully",
            doctorService.getDoctorAppointmentsByEmail(email)
    );
}


@PatchMapping("/appointments/{id}/cancel")
public ResponseEntity<?> cancelAppointment(
        @PathVariable Long id,
        @RequestBody CancelRequest request,
        Authentication authentication) {

    String email = authentication.getName();

    doctorService.cancelAppointment(
            email,
            id,
            request.getCancelReason()
    );

    return ResponseEntity.ok(
            new ApiResponse<>("Appointment cancelled successfully", null)
    );
}}
