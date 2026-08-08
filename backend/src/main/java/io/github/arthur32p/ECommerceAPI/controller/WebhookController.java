package io.github.arthur32p.ECommerceAPI.controller;

import com.stripe.exception.SignatureVerificationException;
import io.github.arthur32p.ECommerceAPI.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhooks/stripe")
@RequiredArgsConstructor
public class WebhookController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) throws SignatureVerificationException {
            paymentService.processarWebhookStripe(payload, sigHeader);
            return ResponseEntity.ok("Webhook processado com sucesso.");
    }
}
