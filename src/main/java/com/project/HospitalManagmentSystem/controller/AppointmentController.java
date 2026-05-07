package com.project.HospitalManagmentSystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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

    @PreAuthorize("hasRole('DOCTOR')")
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<?> cancelAppointment(@PathVariable Long id,
                                               @RequestBody CancelRequest request) {

        appointmentService.cancelAppointment(id, request.getCancelReason());

        return ResponseEntity.ok().body(
                new ApiResponse<>("Appointment cancelled and email sent", id)
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @PatchMapping("/{id}/confirm")
    public ResponseEntity<?> confirmAppointment(@PathVariable Long id) {

        appointmentService.confirmAppointment(id);

        return ResponseEntity.ok().body(
               new ApiResponse<>("Appointment confirmed", id)
        );
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PatchMapping("/{id}/complete")
    public ResponseEntity<?> completeAppointment(@PathVariable Long id) {

        appointmentService.completeAppointment(id);

        return ResponseEntity.ok().body(
                new ApiResponse<>("Appointment completed", id)
        );
    }

    @PreAuthorize("hasRole('PATIENT')")
    @PostMapping("/book")
    public ResponseEntity<?> book(@RequestBody AppointmentRequestDTO dto,
                                  Authentication authentication) {

        String email = authentication.getName();

        appointmentService.bookAppointment(dto, email);

        return ResponseEntity.ok().body(
                new ApiResponse<>("Appointment booked successfully", null)
        );
    }


    @PreAuthorize("hasRole('PATIENT')")
    @PatchMapping("/{id}/reschedule")
    public ResponseEntity<?> reschedule(@PathVariable Long id,
                                        @RequestBody AppointmentRequestDTO dto,
                                        Authentication authentication) {

        String email = authentication.getName();

        appointmentService.rescheduleAppointment(
                id,
                dto.getAppointmentDate(),
                dto.getAppointmentTime(),
                email
        );

        return ResponseEntity.ok().body(
                new ApiResponse<>("Appointment rescheduled successfully", id)
        );
    }


    @PreAuthorize("hasRole('PATIENT')")
    @PatchMapping("/{id}/patient-cancel")
    public ResponseEntity<?> patientCancel(@PathVariable Long id,
                                           @RequestBody CancelRequest request,
                                           Authentication authentication) {

        String email = authentication.getName();

        appointmentService.patientCancelAppointment(id, email, request.getCancelReason());

        return ResponseEntity.ok().body(
                new ApiResponse<>("Your appointment has been cancelled", id)
        );
    }

    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/booked-slots/{doctorId}")
    public ResponseEntity<?> getBookedSlots(
            @PathVariable Long doctorId,
            @RequestParam(required = false) String date) {

        if (date == null || date.trim().isEmpty() || "undefined".equals(date)) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>("Valid date parameter is required", null)
            );
        }

        LocalDate localDate;
        try {
            localDate = LocalDate.parse(date);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>("Invalid date format. Expected YYYY-MM-DD", null)
            );
        }

        return ResponseEntity.ok(
                appointmentService.getBookedSlots(doctorId, localDate)
        );
    }
}