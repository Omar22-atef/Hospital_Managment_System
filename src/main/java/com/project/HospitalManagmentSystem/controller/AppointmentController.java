package com.project.HospitalManagmentSystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.HospitalManagmentSystem.service.AppointmentService;
import com.project.HospitalManagmentSystem.dto.ApiResponse;
import com.project.HospitalManagmentSystem.dto.CancelRequest;
import com.project.HospitalManagmentSystem.dto.AppointmentRequestDTO;
import java.time.LocalDate;
import java.time.LocalTime;

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


    @PostMapping("/book")
    public ResponseEntity<?> book(@RequestBody AppointmentRequestDTO dto) {
        appointmentService.bookAppointment(dto);
        return ResponseEntity.ok().body(
                new ApiResponse<>("Appointment booked successfully", null)
        );
    }


    @PatchMapping("/{id}/reschedule")
    public ResponseEntity<?> reschedule(@PathVariable Long id,
                                        @RequestBody AppointmentRequestDTO dto) {
        appointmentService.rescheduleAppointment(id, dto.getAppointmentDate(), dto.getAppointmentTime());
        return ResponseEntity.ok().body(
                new ApiResponse<>("Appointment rescheduled successfully", id)
        );
    }


    @PatchMapping("/{id}/patient-cancel")
    public ResponseEntity<?> patientCancel(@PathVariable Long id,
                                           @RequestBody CancelRequest request) {

        appointmentService.patientCancelAppointment(id, request.getPatientId(), request.getReason());
        return ResponseEntity.ok().body(
                new ApiResponse<>("Your appointment has been cancelled", id)
        );
    }
}