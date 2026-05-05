package com.project.HospitalManagmentSystem.service;

import org.springframework.stereotype.Service;

import com.project.HospitalManagmentSystem.entity.Appointment;
import com.project.HospitalManagmentSystem.repository.AppointmentRepository;
import com.project.HospitalManagmentSystem.enums.AppointmentStatus;
import com.project.HospitalManagmentSystem.entity.Patient;
import com.project.HospitalManagmentSystem.entity.Doctor;
import com.project.HospitalManagmentSystem.repository.PatientRepository;
import com.project.HospitalManagmentSystem.repository.DoctorRepository;
import com.project.HospitalManagmentSystem.dto.AppointmentRequestDTO;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final EmailService emailService;
    private final AppointmentFactory appointmentFactory;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;




    public AppointmentService(AppointmentRepository appointmentRepository,
                              EmailService emailService,
                              AppointmentFactory appointmentFactory,
                              PatientRepository patientRepository,
                              DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.emailService = emailService;
        this.appointmentFactory = appointmentFactory;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }


    public void cancelAppointment(Long id, String reason) {

        if (reason == null || reason.isBlank()) {
            throw new RuntimeException("Cancellation reason is required");
        }

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            throw new RuntimeException("Appointment already cancelled");
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointment.setCancellationReason(reason);

        appointmentRepository.save(appointment);

        String patientEmail = appointment.getPatient().getEmail();


        String message = "Your appointment on "
                + appointment.getAppointmentDate() + " at "
                + appointment.getAppointmentTime()
                + " has been cancelled.\nReason: " + reason;

        emailService.sendCancellationEmail(patientEmail, message);
    }

    public void confirmAppointment(Long id) {

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (appointment.getStatus() != AppointmentStatus.PENDING) {
            throw new RuntimeException("Only pending appointments can be confirmed");
        }

        appointment.setStatus(AppointmentStatus.CONFIRMED);
        appointmentRepository.save(appointment);
    }


    public void completeAppointment(Long id) {

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (appointment.getStatus() != AppointmentStatus.CONFIRMED) {
            throw new RuntimeException("Only confirmed appointments can be completed");
        }

        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointmentRepository.save(appointment);
    }




    public void bookAppointment(AppointmentRequestDTO dto) {
        if (dto.getAppointmentDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Cannot book in the past");
        }


        long dailyCount = appointmentRepository.countByDoctorIdAndAppointmentDateAndStatusNot(
                dto.getDoctorId(), dto.getAppointmentDate(), AppointmentStatus.CANCELLED);

        if (dailyCount >= 10) {
            throw new RuntimeException("Doctor reached daily limit of 10 patients");
        }

        if (appointmentRepository.existsByDoctorIdAndAppointmentDateAndAppointmentTime(
                dto.getDoctorId(), dto.getAppointmentDate(), dto.getAppointmentTime())) {
            throw new RuntimeException("Time slot already booked");
        }

        Patient patient = patientRepository.findById(dto.getPatientId()).orElseThrow();
        Doctor doctor = doctorRepository.findById(dto.getDoctorId()).orElseThrow();


        Appointment appointment = appointmentFactory.createAppointment(patient, doctor, dto.getAppointmentDate(), dto.getAppointmentTime());
        appointmentRepository.save(appointment);
    }


    public void rescheduleAppointment(Long id, LocalDate newDate, LocalTime newTime) {
        // 1. Find the existing appointment
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));


        boolean isBusy = appointmentRepository.existsByDoctorIdAndAppointmentDateAndAppointmentTime(
                appointment.getDoctor().getId(), newDate, newTime);

        if (isBusy) {
            throw new RuntimeException("Doctor is already booked at the new requested time");
        }

        appointment.setAppointmentDate(newDate);
        appointment.setAppointmentTime(newTime);
        appointment.setStatus(AppointmentStatus.PENDING);

        appointmentRepository.save(appointment);
    }


    public void patientCancelAppointment(Long appointmentId, Long patientId, String reason) {

        if (reason == null || reason.isBlank()) {
            throw new RuntimeException("Please provide a reason for your cancellation.");
        }


        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found."));

        // 3. SECURITY CHECK: Ensure the logged-in patient owns this appointment
        if (!appointment.getPatient().getId().equals(patientId)) {
            throw new RuntimeException("Unauthorized: You can only cancel your own appointments.");
        }


        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointment.setCancellationReason("Cancelled by Patient: " + reason);

        appointmentRepository.save(appointment);


        emailService.sendCancellationEmail(appointment.getPatient().getEmail(),
                "Your appointment has been successfully cancelled.");
    }


}