package io.github.arthur32p.ECommerceAPI.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CarrinhoResponseDto(
        UUID id,
        UUID userId,
        List<ItemCarrinhoResponseDto> itens,
        BigDecimal valorTotal
) { }
