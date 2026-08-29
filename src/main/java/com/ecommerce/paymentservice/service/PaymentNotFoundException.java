package com.ecommerce.paymentservice.service;

/** Raised when a payment id does not exist. */
public class PaymentNotFoundException extends RuntimeException {

  public PaymentNotFoundException(Long id) {
    super("No payment with id " + id);
  }
}
