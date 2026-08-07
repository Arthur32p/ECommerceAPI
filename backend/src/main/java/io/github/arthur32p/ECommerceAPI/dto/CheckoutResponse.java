package io.github.arthur32p.ECommerceAPI.dto;

import java.util.UUID;

public record CheckoutResponse(
        UUID pedidoId,
        String urlPagamento
) {
}
