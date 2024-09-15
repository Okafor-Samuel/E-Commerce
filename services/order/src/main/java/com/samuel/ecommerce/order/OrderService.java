package com.samuel.ecommerce.order;

import com.samuel.ecommerce.customer.CustomerClient;
import com.samuel.ecommerce.exception.BusinessException;
import com.samuel.ecommerce.kafka.OrderConfirmation;
import com.samuel.ecommerce.kafka.OrderProducer;
import com.samuel.ecommerce.orderLine.OrderLineRequest;
import com.samuel.ecommerce.orderLine.OrderLineService;
import com.samuel.ecommerce.payment.PaymentClient;
import com.samuel.ecommerce.payment.PaymentRequest;
import com.samuel.ecommerce.product.ProductClient;
import com.samuel.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;
    public Integer createOrder(OrderCreationRequest orderCreationRequest) {
        //check the customer --> OpenFeign
        var customer = this.customerClient.findCustomerById(orderCreationRequest.customerId())
                .orElseThrow(()-> new BusinessException("Cannot create order:: No customer exists with the provided ID"));

        //purchase the product --> product microservice(RestTemplate)
        var purchasedProducts = this.productClient.purchasedProducts(orderCreationRequest.products());


        //persist order
        var order = this.orderRepository.save(mapper.toOrder(orderCreationRequest));

        //persist order lines
        for(PurchaseRequest purchaseRequest : orderCreationRequest.products()){
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        //start payment process
        var paymentRequest = new PaymentRequest(
                orderCreationRequest.amount(),
                orderCreationRequest.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);

        //send order confirmation --> notification microservice(kafka)
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        orderCreationRequest.reference(),
                        orderCreationRequest.amount(),
                        orderCreationRequest.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );
        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(mapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer orderId) {
        return orderRepository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(()-> new EntityNotFoundException(String.format("No order found with the provided ID: %d", orderId)));
    }

}
