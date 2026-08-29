package com.ecommerce.paymentservice.web;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ecommerce.paymentservice.domain.PaymentStatus;
import com.ecommerce.paymentservice.entity.Payment;
import com.ecommerce.paymentservice.service.PaymentNotFoundException;
import com.ecommerce.paymentservice.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

  @Autowired private MockMvc mvc;

  @MockitoBean private PaymentService service;

  @Test
  void authorize_returnsPaymentView() throws Exception {
    when(service.authorize(eq(1L), anyLong()))
        .thenReturn(new Payment(1L, 349700, PaymentStatus.APPROVED));

    mvc.perform(
            post("/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"orderId\":1,\"amountCents\":349700}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("APPROVED"))
        .andExpect(jsonPath("$.orderId").value(1));
  }

  @Test
  void getById_missing_returns404() throws Exception {
    when(service.getById(9L)).thenThrow(new PaymentNotFoundException(9L));

    mvc.perform(get("/payments/9"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.code").value("PAYMENT_NOT_FOUND"));
  }

  @Test
  void authorize_rejectsNonPositiveAmount() throws Exception {
    mvc.perform(
            post("/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"orderId\":1,\"amountCents\":0}"))
        .andExpect(status().isBadRequest());
  }
}
