package io.github.arthur32p.ECommerceAPI.mapper;

import io.github.arthur32p.ECommerceAPI.dto.CarrinhoResponseDto;
import io.github.arthur32p.ECommerceAPI.dto.ItemCarrinhoResponseDto;
import io.github.arthur32p.ECommerceAPI.model.Carrinho;
import io.github.arthur32p.ECommerceAPI.model.ItemCarrinho;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CarrinhoMapper {

    @Mapping(target = "userId", source = "user.id")
    CarrinhoResponseDto toDto(Carrinho carrinho);

    @Mapping(target = "produtoId", source = "produto.id")
    @Mapping(target = "produtoNome", source = "produto.nome")
    ItemCarrinhoResponseDto toItemDTO(ItemCarrinho item);
}
