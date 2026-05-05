package com.project.HospitalManagmentSystem.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.project.HospitalManagmentSystem.service.DoctorService;
import com.project.HospitalManagmentSystem.dto.ApiResponse;
import com.project.HospitalManagmentSystem.dto.DoctorResponseDTO;
import com.project.HospitalManagmentSystem.dto.AppointmentResponseDTO;
import org.springframework.security.core.Authentication;

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
}