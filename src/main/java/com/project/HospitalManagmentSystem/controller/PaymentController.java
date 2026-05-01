package com.project.HospitalManagmentSystem.controller;

import com.project.HospitalManagmentSystem.service.PaymentService;
import com.project.HospitalManagmentSystem.dto.ApiResponse;
import com.project.HospitalManagmentSystem.dto.PaymentRequestDTO;
import com.project.HospitalManagmentSystem.dto.PaymentResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    // POST /api/payments
    @PostMapping
    public ResponseEntity<ApiResponse<PaymentResponseDTO>> processPayment(@RequestBody PaymentRequestDTO request) {
        return ResponseEntity.ok(new ApiResponse<>("success", paymentService.createPayment(request)));
    }

    // GET /api/payments
    @GetMapping
    public ResponseEntity<ApiResponse<List<PaymentResponseDTO>>> getPaymentHistory() {
        return ResponseEntity.ok(new ApiResponse<>("success", paymentService.getAllPayments()));
    }
}