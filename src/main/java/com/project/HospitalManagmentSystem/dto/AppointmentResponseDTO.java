package com.project.HospitalManagmentSystem.dto;

import com.project.HospitalManagmentSystem.enums.AppointmentStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AppointmentResponseDTO {
    private Long id;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private AppointmentStatus status;
    private Long patientId;
    private Long doctorId;
    private String doctorName;
}
