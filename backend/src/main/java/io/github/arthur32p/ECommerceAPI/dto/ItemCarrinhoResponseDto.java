package io.github.arthur32p.ECommerceAPI.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemCarrinhoResponseDto(
        UUID id,
        UUID produtoId,
        String produtoNome,
        BigDecimal precoUnitario,
        Integer quantidade,
        BigDecimal subtotal
) {
}
