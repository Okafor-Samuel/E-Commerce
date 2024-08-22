package com.samuel.ecommerce.order;

import org.springframework.stereotype.Service;

@Service
public class OrderMapper {
    public Order toOrder(OrderCreationRequest orderCreationRequest) {
        return Order.builder()
                .id(orderCreationRequest.Id())
                .customerId(orderCreationRequest.customerId())
                .reference(orderCreationRequest.reference())
                .totalAmount(orderCreationRequest.amount())
                .paymentMethod(orderCreationRequest.paymentMethod())
                .build();
    }

    public OrderResponse fromOrder(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getReference(),
                order.getTotalAmount(),
                order.getPaymentMethod(),
                order.getCustomerId()
        );
    }
}
