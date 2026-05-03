package com.project.HospitalManagmentSystem.Repository;

import com.project.HospitalManagmentSystem.entity.PasswordResetToken;
import com.project.HospitalManagmentSystem.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByResetTokenAndUserRoleAndIsUsedFalse(String resetToken, UserRole userRole);
}