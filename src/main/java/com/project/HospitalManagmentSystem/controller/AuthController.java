package com.project.HospitalManagmentSystem.controller;

import com.project.HospitalManagmentSystem.dto.ForgotPasswordRequestDTO;
import com.project.HospitalManagmentSystem.dto.LoginRequestDTO;
import com.project.HospitalManagmentSystem.dto.LoginResponseDTO;
import com.project.HospitalManagmentSystem.dto.RegisterRequestDTO;
import com.project.HospitalManagmentSystem.dto.ResetPasswordRequestDTO;
import com.project.HospitalManagmentSystem.enums.UserRole;
import com.project.HospitalManagmentSystem.serviceInterfaces.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO request) {
        authService.register(request);
        return ResponseEntity.ok("Registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        LoginResponseDTO response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequestDTO request) {
        authService.forgotPassword(request);
        return ResponseEntity.ok("OTP sent to your email successfully");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequestDTO request) {
        authService.resetPassword(request);
        return ResponseEntity.ok("Password reset successfully");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam UserRole role) {
        String token = authHeader.substring(7);
        authService.logout(token, role);
        return ResponseEntity.ok("Logged out successfully");
    }
}