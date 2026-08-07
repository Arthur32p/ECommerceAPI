package io.github.arthur32p.ECommerceAPI.controller;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import io.github.arthur32p.ECommerceAPI.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhooks/stripe")
@RequiredArgsConstructor
public class WebhookController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader){
        try {
            paymentService.processarWebhookStripe(payload, sigHeader);
            return ResponseEntity.ok("Webhook processado com sucesso.");
        } catch (SignatureVerificationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Assinatura do Webhook inválida.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao processar Webhook.");
        }
    }
}
