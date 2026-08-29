package com.ecommerce.paymentservice.entity;

import com.ecommerce.paymentservice.domain.PaymentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

/** A recorded payment authorization attempt. */
@Entity
@Table(name = "payments")
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long orderId;

  @Column(nullable = false)
  private long amountCents;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private PaymentStatus status;

  @Column(nullable = false, updatable = false)
  private Instant createdAt;

  protected Payment() {
    // for JPA
  }

  public Payment(Long orderId, long amountCents, PaymentStatus status) {
    this.orderId = orderId;
    this.amountCents = amountCents;
    this.status = status;
    this.createdAt = Instant.now();
  }

  public Long getId() {
    return id;
  }

  public Long getOrderId() {
    return orderId;
  }

  public long getAmountCents() {
    return amountCents;
  }

  public PaymentStatus getStatus() {
    return status;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }
}
