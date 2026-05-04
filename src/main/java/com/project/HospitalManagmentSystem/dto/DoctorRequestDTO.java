package com.project.HospitalManagmentSystem.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.HospitalManagmentSystem.enums.DaysOfWeek;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorRequestDTO {
    @NotBlank(message = "Doctor Name is required")
    @Size(min = 3, max = 50, message = "Doctor Name length must be between 3 and 50")
    private String name;

    private String phone;

    @NotBlank(message = "Doctor Email is required")
    @Email(message = "Invalid Email format")
    private String email;

    @NotBlank(message = "Doctor Password is required")
    private String password;

    @NotBlank(message = "Doctor Specialization is required")
    private String specialization;

    @NotNull(message = "Day of Week is required")
    private DaysOfWeek dayOfWeek;

    @NotNull(message = "Start Time is required")
    private LocalTime startTime;

    @JsonFormat(pattern = "HH:mm:ss")
    @NotNull(message = "End Time is required")
    private LocalTime endTime;
}
