package com.ecommerce.paymentservice.web.dto;

import com.ecommerce.paymentservice.domain.PaymentStatus;
import com.ecommerce.paymentservice.entity.Payment;
import java.time.Instant;

/** View of a payment record. */
public record PaymentResponse(
    Long paymentId, Long orderId, long amountCents, PaymentStatus status, Instant createdAt) {

  public static PaymentResponse from(Payment payment) {
    return new PaymentResponse(
        payment.getId(),
        payment.getOrderId(),
        payment.getAmountCents(),
        payment.getStatus(),
        payment.getCreatedAt());
  }
}
