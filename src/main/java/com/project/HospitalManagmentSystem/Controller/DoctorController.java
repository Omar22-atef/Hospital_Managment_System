package com.project.HospitalManagmentSystem.Controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.project.HospitalManagmentSystem.Service.DoctorService;
import com.project.HospitalManagmentSystem.dto.ApiResponse;
import com.project.HospitalManagmentSystem.dto.DoctorResponseDTO;
import com.project.HospitalManagmentSystem.dto.AppointmentResponseDTO;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    private final DoctorService doctorService;

    
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    
    @GetMapping("/{id}")
    public ApiResponse<DoctorResponseDTO> getDoctor(@PathVariable Long id) {

        return new ApiResponse<>(
                "Doctor fetched successfully",
                doctorService.getDoctorById(id)
        );
    }

    
    @GetMapping("/{id}/appointments")
    public ApiResponse<List<AppointmentResponseDTO>> getAppointments(@PathVariable Long id) {

        return new ApiResponse<>(
                "Appointments fetched successfully",
                doctorService.getDoctorAppointments(id)
        );
    }
}