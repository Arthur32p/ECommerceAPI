package io.github.arthur32p.ECommerceAPI.mapper;

import io.github.arthur32p.ECommerceAPI.dto.ProdutoRequestDto;
import io.github.arthur32p.ECommerceAPI.dto.ProdutoResponseDto;
import io.github.arthur32p.ECommerceAPI.model.Produto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    public abstract Produto toEntity(ProdutoRequestDto dto);

    public abstract ProdutoRequestDto toDto(Produto produto);

    public abstract ProdutoResponseDto toResponse(Produto produto);

}
