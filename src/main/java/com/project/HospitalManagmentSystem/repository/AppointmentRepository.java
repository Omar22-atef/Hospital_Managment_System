package com.project.HospitalManagmentSystem.repository;

import com.project.HospitalManagmentSystem.entity.Appointment;
import com.project.HospitalManagmentSystem.entity.Doctor;
import com.project.HospitalManagmentSystem.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDoctor(Doctor doctor);
    List<Appointment> findByPatientId(Long patientId);
    List<Appointment> findByPatientEmail(String email);
    boolean existsByDoctorIdAndAppointmentDateAndAppointmentTime(Long doctorId, LocalDate date, LocalTime time);
    long countByDoctorIdAndAppointmentDateAndStatusNot(Long doctorId, LocalDate date, AppointmentStatus status);


}
