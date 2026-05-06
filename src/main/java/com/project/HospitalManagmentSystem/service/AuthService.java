package com.project.HospitalManagmentSystem.service;

import com.project.HospitalManagmentSystem.config.JwtUtil;
import com.project.HospitalManagmentSystem.dto.ForgotPasswordRequestDTO;
import com.project.HospitalManagmentSystem.dto.LoginRequestDTO;
import com.project.HospitalManagmentSystem.dto.LoginResponseDTO;
import com.project.HospitalManagmentSystem.dto.RegisterRequestDTO;
import com.project.HospitalManagmentSystem.dto.ResetPasswordRequestDTO;
import com.project.HospitalManagmentSystem.entity.*;
import com.project.HospitalManagmentSystem.enums.UserRole;
import com.project.HospitalManagmentSystem.repository.AdminRepository;
import com.project.HospitalManagmentSystem.repository.DoctorRepository;
import com.project.HospitalManagmentSystem.repository.PatientRepository;
import com.project.HospitalManagmentSystem.repository.OtpTokenRepository;
import com.project.HospitalManagmentSystem.serviceInterfaces.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final AdminRepository adminRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final OtpTokenRepository otpTokenRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    public void register(RegisterRequestDTO request) {

        if (request.getRole() != UserRole.PATIENT) {
            throw new RuntimeException("Only patients can register. Doctors are added by the Admin.");
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());

        Patient patient = Patient.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(hashedPassword)
                .phone(request.getPhone())
                .dateOfBirth(request.getDateOfBirth())
                .build();
        patientRepository.save(patient);
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {
        String email = request.getEmail();
        String password = request.getPassword();

        String token;
        String name;
        UserRole role;

        var adminOpt = adminRepository.findByEmail(email);
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();

            if (!passwordEncoder.matches(password, admin.getPassword())) {
                throw new RuntimeException("Invalid password");
            }

            role = UserRole.ADMIN;
            name = admin.getName();
            token = jwtUtil.generateToken(email, role.name());

            admin.setToken(token);
            adminRepository.save(admin);

            return new LoginResponseDTO(token, role.name(), name, email);
        }

        var doctorOpt = doctorRepository.findByEmail(email);
        if (doctorOpt.isPresent()) {
            Doctor doctor = doctorOpt.get();

            if (!passwordEncoder.matches(password, doctor.getPassword())) {
                throw new RuntimeException("Invalid password");
            }

            role = UserRole.DOCTOR;
            name = doctor.getName();
            token = jwtUtil.generateToken(email, role.name());

            doctor.setToken(token);
            doctorRepository.save(doctor);

            return new LoginResponseDTO(token, role.name(), name, email);
        }

        var patientOpt = patientRepository.findByEmail(email);
        if (patientOpt.isPresent()) {
            Patient patient = patientOpt.get();

            if (!passwordEncoder.matches(password, patient.getPassword())) {
                throw new RuntimeException("Invalid password");
            }

            role = UserRole.PATIENT;
            name = patient.getName();
            token = jwtUtil.generateToken(email, role.name());

            patient.setToken(token);
            patientRepository.save(patient);

            return new LoginResponseDTO(token, role.name(), name, email);
        }

        throw new RuntimeException("User not found");
    }

    @Override
    @Transactional
    public void forgotPassword(ForgotPasswordRequestDTO request) {
        String email = request.getEmail();
        UserRole role = request.getRole();

        boolean exists = switch (role) {
            case ADMIN -> adminRepository.findByEmail(email).isPresent();
            case DOCTOR -> doctorRepository.findByEmail(email).isPresent();
            case PATIENT -> patientRepository.findByEmail(email).isPresent();
        };

        if (!exists) {
            throw new RuntimeException("User not found");
        }

        // ✅ FIX: Delete old OTPs first to avoid duplicate rows
        otpTokenRepository.deleteAllByEmailAndUserRole(email, role);

        String otp = String.format("%06d", new Random().nextInt(999999));

        OtpToken otpToken = OtpToken.builder()
                .email(email)
                .otpCode(otp)
                .userRole(role)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(10))
                .isUsed(false)
                .build();

        otpTokenRepository.save(otpToken);
        emailService.sendOtpEmail(email, otp);
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordRequestDTO request) {
        OtpToken otpToken = otpTokenRepository
                .findByEmailAndUserRoleAndIsUsedFalse(request.getEmail(), request.getRole())
                .orElseThrow(() -> new RuntimeException("Invalid or expired OTP"));

        if (otpToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP has expired");
        }

        if (!otpToken.getOtpCode().equals(request.getOtpCode())) {
            throw new RuntimeException("Wrong OTP code");
        }

        String hashedPassword = passwordEncoder.encode(request.getNewPassword());

        switch (request.getRole()) {
            case ADMIN -> {
                Admin admin = adminRepository.findByEmail(request.getEmail())
                        .orElseThrow(() -> new RuntimeException("Admin not found"));
                admin.setPassword(hashedPassword);
                adminRepository.save(admin);
            }
            case DOCTOR -> {
                Doctor doctor = doctorRepository.findByEmail(request.getEmail())
                        .orElseThrow(() -> new RuntimeException("Doctor not found"));
                doctor.setPassword(hashedPassword);
                doctorRepository.save(doctor);
            }
            case PATIENT -> {
                Patient patient = patientRepository.findByEmail(request.getEmail())
                        .orElseThrow(() -> new RuntimeException("Patient not found"));
                patient.setPassword(hashedPassword);
                patientRepository.save(patient);
            }
        }

        // ✅ FIX: Delete OTP after use instead of just marking it as used
        otpTokenRepository.delete(otpToken);
    }

    @Override
    public void logout(String token, UserRole role) {
        switch (role) {
            case ADMIN -> {
                Admin admin = adminRepository.findByToken(token)
                        .orElseThrow(() -> new RuntimeException("Admin not found"));
                admin.setToken(null);
                adminRepository.save(admin);
            }
            case DOCTOR -> {
                Doctor doctor = doctorRepository.findByToken(token)
                        .orElseThrow(() -> new RuntimeException("Doctor not found"));
                doctor.setToken(null);
                doctorRepository.save(doctor);
            }
            case PATIENT -> {
                Patient patient = patientRepository.findByToken(token)
                        .orElseThrow(() -> new RuntimeException("Patient not found"));
                patient.setToken(null);
                patientRepository.save(patient);
            }
        }
    }
}