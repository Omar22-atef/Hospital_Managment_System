package com.project.HospitalManagmentSystem.Service;

import com.project.HospitalManagmentSystem.config.JwtUtil;
import com.project.HospitalManagmentSystem.dto.ForgotPasswordRequestDTO;
import com.project.HospitalManagmentSystem.dto.LoginRequestDTO;
import com.project.HospitalManagmentSystem.dto.LoginResponseDTO;
import com.project.HospitalManagmentSystem.dto.RegisterRequestDTO;
import com.project.HospitalManagmentSystem.dto.ResetPasswordRequestDTO;
import com.project.HospitalManagmentSystem.entity.*;
import com.project.HospitalManagmentSystem.enums.UserRole;
import com.project.HospitalManagmentSystem.Repository.AdminRepository;
import com.project.HospitalManagmentSystem.Repository.DoctorRepository;
import com.project.HospitalManagmentSystem.Repository.PatientRepository;
import com.project.HospitalManagmentSystem.Repository.OtpTokenRepository;
import com.project.HospitalManagmentSystem.Repository.PasswordResetTokenRepository;
import com.project.HospitalManagmentSystem.service.EmailService;
import com.project.HospitalManagmentSystem.serviceInterfaces.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final AdminRepository adminRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final OtpTokenRepository otpTokenRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    public void register(RegisterRequestDTO request) {
        String hashedPassword = passwordEncoder.encode(request.getPassword());

        switch (request.getRole()) {
            case ADMIN -> {
                Admin admin = Admin.builder()
                        .name(request.getName())
                        .email(request.getEmail())
                        .password(hashedPassword)
                        .build();
                adminRepository.save(admin);
            }
            case DOCTOR -> {
                Doctor doctor = Doctor.builder()
                        .name(request.getName())
                        .email(request.getEmail())
                        .password(hashedPassword)
                        .build();
                doctorRepository.save(doctor);
            }
            case PATIENT -> {
                Patient patient = Patient.builder()
                        .name(request.getName())
                        .email(request.getEmail())
                        .password(hashedPassword)
                        .build();
                patientRepository.save(patient);
            }
        }
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {
        String email = request.getEmail();
        String password = request.getPassword();
        UserRole role = request.getRole();

        String encodedPassword = null;
        String name = null;
        String token = null;

        if (role == UserRole.ADMIN) {
            Admin admin = adminRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Admin not found"));
            encodedPassword = admin.getPassword();
            name = admin.getName();
            if (!passwordEncoder.matches(password, encodedPassword)) {
                throw new RuntimeException("Invalid password");
            }
            token = jwtUtil.generateToken(email, role.name());
            admin.setToken(token);
            adminRepository.save(admin);

        } else if (role == UserRole.DOCTOR) {
            Doctor doctor = doctorRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));
            encodedPassword = doctor.getPassword();
            name = doctor.getName();
            if (!passwordEncoder.matches(password, encodedPassword)) {
                throw new RuntimeException("Invalid password");
            }
            token = jwtUtil.generateToken(email, role.name());
            doctor.setToken(token);
            doctorRepository.save(doctor);

        } else if (role == UserRole.PATIENT) {
            Patient patient = patientRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Patient not found"));
            encodedPassword = patient.getPassword();
            name = patient.getName();
            if (!passwordEncoder.matches(password, encodedPassword)) {
                throw new RuntimeException("Invalid password");
            }
            token = jwtUtil.generateToken(email, role.name());
            patient.setToken(token);
            patientRepository.save(patient);
        }

        return new LoginResponseDTO(token, role.name(), name, email);
    }

    @Override
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

        otpToken.setIsUsed(true);
        otpTokenRepository.save(otpToken);
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