package com.ecommerce.paymentservice.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/** Request body to authorize a payment for an order. */
public record PaymentRequest(@NotNull Long orderId, @Positive long amountCents) {}
