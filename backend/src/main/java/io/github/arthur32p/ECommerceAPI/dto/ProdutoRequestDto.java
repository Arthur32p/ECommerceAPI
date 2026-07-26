package io.github.arthur32p.ECommerceAPI.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProdutoRequestDto(
        @NotBlank(message = "Campo obrigatório")
        String nome,

        @NotBlank(message = "Campo obrigatório")
        String descricao,

        @NotNull(message = "Campo obrigatório")
        BigDecimal preco,

        @NotNull(message = "Campo obrigatório")
        Integer quantidadeEstoque
) {
}
