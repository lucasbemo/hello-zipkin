package com.example.paymentservice;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
  @GetMapping("/payments/hello")
  public Map<String, String> hello() {
    return Map.of("message", "hello from payment-service");
  }
}
