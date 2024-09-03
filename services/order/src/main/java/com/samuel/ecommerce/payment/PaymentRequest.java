package com.samuel.ecommerce.payment;

import com.samuel.ecommerce.customer.CustomerResponse;
import com.samuel.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
