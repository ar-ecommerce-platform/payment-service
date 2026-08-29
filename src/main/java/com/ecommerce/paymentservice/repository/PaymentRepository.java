package com.ecommerce.paymentservice.repository;

import com.ecommerce.paymentservice.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

/** Data access for {@link Payment}. */
public interface PaymentRepository extends JpaRepository<Payment, Long> {}
