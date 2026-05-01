package com.project.HospitalManagmentSystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.HospitalManagmentSystem.service.AppointmentService;
import com.project.HospitalManagmentSystem.dto.ApiResponse;
import com.project.HospitalManagmentSystem.dto.CancelRequest;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<?> cancelAppointment(@PathVariable Long id,
                                               @RequestBody CancelRequest request) {

        appointmentService.cancelAppointment(id, request.getReason());

        return ResponseEntity.ok().body(
                new ApiResponse<>("Appointment cancelled and email sent", id)
        );
    }

    
    @PatchMapping("/{id}/confirm")
    public ResponseEntity<?> confirmAppointment(@PathVariable Long id) {

        appointmentService.confirmAppointment(id);

        return ResponseEntity.ok().body(
               new ApiResponse<>("Appointment confirmed", id)
        );
    }

    
    @PatchMapping("/{id}/complete")
    public ResponseEntity<?> completeAppointment(@PathVariable Long id) {

        appointmentService.completeAppointment(id);

        return ResponseEntity.ok().body(
                new ApiResponse<>("Appointment completed", id)
        );
    }
}