package com.project.HospitalManagmentSystem.service;

import org.springframework.security.access.AccessDeniedException;
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
import java.util.List;

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




    public void bookAppointment(AppointmentRequestDTO dto, String email) {

        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        Appointment appointment = Appointment.builder()
                .patient(patient)
                .doctor(doctor)
                .appointmentDate(dto.getAppointmentDate())
                .appointmentTime(dto.getAppointmentTime())
                .status(AppointmentStatus.PENDING)
                .build();

        appointmentRepository.save(appointment);
    }


    public void rescheduleAppointment(Long id, LocalDate date, LocalTime time, String email) {

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        if (!appointment.getPatient().getEmail().equals(email)) {
            throw new AccessDeniedException("You are not allowed to reschedule this appointment");
        }

        appointment.setAppointmentDate(date);
        appointment.setAppointmentTime(time);
        appointmentRepository.save(appointment); // persist changes
    }


    public void patientCancelAppointment(Long id, String email, String reason) {

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (!appointment.getPatient().getEmail().equals(email)) {
            throw new AccessDeniedException("You are not allowed to cancel this appointment");
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointment.setCancellationReason(reason);

        appointmentRepository.save(appointment);
    }

    public List<String> getBookedSlots(Long doctorId, LocalDate date) {

        // Return only booked times for THIS doctor on THIS specific date.
        // Without date filtering, 09:00 Monday would block 09:00 on every other day.
        List<Appointment> appointments =
                appointmentRepository
                        .findByDoctorIdAndAppointmentDateAndStatusNot(
                                doctorId,
                                date,
                                AppointmentStatus.CANCELLED
                        );

        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss");
        return appointments.stream()
                .map(a -> a.getAppointmentTime().format(formatter))
                .toList();
    }
}