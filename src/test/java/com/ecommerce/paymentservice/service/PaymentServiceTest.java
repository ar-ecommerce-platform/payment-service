package com.ecommerce.paymentservice.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.ecommerce.paymentservice.domain.PaymentStatus;
import com.ecommerce.paymentservice.entity.Payment;
import com.ecommerce.paymentservice.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PaymentServiceTest {

  private PaymentService service;

  @BeforeEach
  void setUp() {
    PaymentRepository repository = Mockito.mock(PaymentRepository.class);
    Mockito.when(repository.save(Mockito.any(Payment.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));
    service = new PaymentService(repository, 500_000L);
  }

  @Test
  void authorize_approvesAmountsAtOrBelowCeiling() {
    assertThat(service.authorize(1L, 500_000L).getStatus()).isEqualTo(PaymentStatus.APPROVED);
  }

  @Test
  void authorize_declinesAmountsAboveCeiling() {
    assertThat(service.authorize(1L, 500_001L).getStatus()).isEqualTo(PaymentStatus.DECLINED);
  }
}
