package com.project.HospitalManagmentSystem.serviceInterfaces;

import com.project.HospitalManagmentSystem.dto.ForgotPasswordRequestDTO;
import com.project.HospitalManagmentSystem.dto.LoginRequestDTO;
import com.project.HospitalManagmentSystem.dto.LoginResponseDTO;
import com.project.HospitalManagmentSystem.dto.RegisterRequestDTO;
import com.project.HospitalManagmentSystem.dto.ResetPasswordRequestDTO;
import com.project.HospitalManagmentSystem.enums.UserRole;

public interface IAuthService {

    void register(RegisterRequestDTO request);

    LoginResponseDTO login(LoginRequestDTO request);

    void forgotPassword(ForgotPasswordRequestDTO request);

    void resetPassword(ResetPasswordRequestDTO request);

    void logout(String token, UserRole role);
}