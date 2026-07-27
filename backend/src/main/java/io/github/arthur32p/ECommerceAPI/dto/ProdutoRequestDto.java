package io.github.arthur32p.ECommerceAPI.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ProdutoRequestDto(
        @NotBlank(message = "Campo obrigatório")
        String nome,

        @NotBlank(message = "Campo obrigatório")
        String descricao,

        @NotNull(message = "Campo obrigatório")
        @Positive(message = "O preço deve ser maior que zero")
        BigDecimal preco,

        @NotNull(message = "Campo obrigatório")
        @PositiveOrZero(message = "A quantidade deve ser zero ou maior")
        Integer quantidadeEstoque
) {
}