package io.github.arthur32p.ECommerceAPI.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProdutoResponseDto(
        UUID id,
        String nome,
        String descricao,
        BigDecimal preco,
        Integer quantidadeEstoque,
        Boolean ativo
) {
}
