package com.samuel.ecommerce.payment.payment;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record Customer(
        String id,
        @NotNull(message = "Firstname is required")
        String firstName,
        @NotNull(message = "Lastname is required")

        String lastName,
        @NotNull(message = "Email is required")
        @Email(message = "Customer email is not correctly formatted")
        String email
) {
}
