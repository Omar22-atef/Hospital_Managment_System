package com.project.HospitalManagmentSystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientRequestDTO {
    @NotBlank(message = "Patient Name is required")
    @Size(min = 3, max = 50, message = "Patient Name length must be between 3 and 50")
    private String name;

    @NotBlank(message = "Patient Email is required")
    @Email(message = "Invalid Email format")
    private String email;

    @NotBlank(message = "Patient Password is required")
    private String password;
    private String phone;
    private LocalDate dateOfBirth;

}
