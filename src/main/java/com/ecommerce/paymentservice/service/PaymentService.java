package com.ecommerce.paymentservice.service;

import com.ecommerce.paymentservice.domain.PaymentStatus;
import com.ecommerce.paymentservice.entity.Payment;
import com.ecommerce.paymentservice.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Stand-in payment authorization. Approves everything except amounts above a configured ceiling, so
 * the order flow has a deterministic decline path to demonstrate.
 */
@Service
public class PaymentService {

  private final PaymentRepository repository;
  private final long autoDeclineAboveCents;

  public PaymentService(
      PaymentRepository repository,
      @Value("${payment.auto-decline-above-cents}") long autoDeclineAboveCents) {
    this.repository = repository;
    this.autoDeclineAboveCents = autoDeclineAboveCents;
  }

  @Transactional
  public Payment authorize(Long orderId, long amountCents) {
    PaymentStatus status =
        amountCents > autoDeclineAboveCents ? PaymentStatus.DECLINED : PaymentStatus.APPROVED;
    return repository.save(new Payment(orderId, amountCents, status));
  }

  @Transactional(readOnly = true)
  public Payment getById(Long id) {
    return repository.findById(id).orElseThrow(() -> new PaymentNotFoundException(id));
  }
}
