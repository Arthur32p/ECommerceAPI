package io.github.arthur32p.ECommerceAPI.controller;

import com.stripe.exception.StripeException;
import io.github.arthur32p.ECommerceAPI.dto.CheckoutResponse;
import io.github.arthur32p.ECommerceAPI.model.UserAuthenticated;
import io.github.arthur32p.ECommerceAPI.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkout")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<CheckoutResponse> checkout(@AuthenticationPrincipal UserAuthenticated userAuthenticated) throws StripeException {
        CheckoutResponse checkoutResponse = pedidoService.realizarCheckout(userAuthenticated);

        return ResponseEntity.ok(checkoutResponse);
    }


}
