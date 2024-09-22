package com.samuel.ecommerce.payment;

import com.samuel.ecommerce.notification.NotificationProducer;
import com.samuel.ecommerce.notification.PaymentNotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper mapper;
    private final NotificationProducer notificationProducer;
    public Integer createPayment(PaymentRequest paymentRequest) {
        var payment = paymentRepository.save(mapper.toPayment(paymentRequest));

        // send notification to the notification service
        notificationProducer.sendNotification(
                new PaymentNotificationRequest(
                        paymentRequest.orderReference(),
                        paymentRequest.amount(),
                        paymentRequest.paymentMethod(),
                        paymentRequest.customer().firstName(),
                        paymentRequest.customer().lastName(),
                        paymentRequest.customer().email()

                )
        );

        return payment.getId();
    }
}
