package com.samuel.ecommerce.kafka;

import com.samuel.ecommerce.customer.CustomerResponse;
import com.samuel.ecommerce.order.PaymentMethod;
import com.samuel.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
