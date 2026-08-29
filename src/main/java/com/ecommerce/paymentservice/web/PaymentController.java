package com.ecommerce.paymentservice.web;

import com.ecommerce.paymentservice.service.PaymentService;
import com.ecommerce.paymentservice.web.dto.PaymentRequest;
import com.ecommerce.paymentservice.web.dto.PaymentResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Payment authorization endpoints. */
@RestController
@RequestMapping("/payments")
public class PaymentController {

  private final PaymentService service;

  public PaymentController(PaymentService service) {
    this.service = service;
  }

  @PostMapping
  public PaymentResponse authorize(@Valid @RequestBody PaymentRequest request) {
    return PaymentResponse.from(service.authorize(request.orderId(), request.amountCents()));
  }

  @GetMapping("/{id}")
  public PaymentResponse getById(@PathVariable Long id) {
    return PaymentResponse.from(service.getById(id));
  }
}
