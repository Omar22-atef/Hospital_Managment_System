package com.project.HospitalManagmentSystem.Payment;
import com.project.HospitalManagmentSystem.enums.PaymentMethod;
import com.project.HospitalManagmentSystem.serviceInterfaces.PaymentProcessor;

public class PaymentFactory {
    public static PaymentProcessor getProcessor(PaymentMethod method) {
        return switch (method) {
            case CREDIT_CARD -> new CreditCardProcessor();
            case CASH -> new CashProcessor();
            default -> throw new IllegalArgumentException("Unsupported payment method: " + method);
        };
    }
}
