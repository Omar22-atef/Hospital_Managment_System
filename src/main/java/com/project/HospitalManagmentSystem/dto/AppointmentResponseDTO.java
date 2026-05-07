package com.project.HospitalManagmentSystem.dto;

import com.project.HospitalManagmentSystem.enums.AppointmentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AppointmentResponseDTO {
    private Long id;
    private LocalDate appointmentDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime appointmentTime;
    private AppointmentStatus status;
    private Long patientId;
    private Long doctorId;
    private String doctorName;
    private String patientName;
    private BigDecimal consultationFee;
}
