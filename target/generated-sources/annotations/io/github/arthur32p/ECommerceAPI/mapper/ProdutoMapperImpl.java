package io.github.arthur32p.ECommerceAPI.mapper;

import io.github.arthur32p.ECommerceAPI.dto.ProdutoRequestDto;
import io.github.arthur32p.ECommerceAPI.dto.ProdutoResponseDto;
import io.github.arthur32p.ECommerceAPI.model.Produto;
import java.math.BigDecimal;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-26T17:54:48-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.12 (Arch Linux)"
)
@Component
public class ProdutoMapperImpl implements ProdutoMapper {

    @Override
    public Produto toEntity(ProdutoRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Produto produto = new Produto();

        produto.setNome( dto.nome() );
        produto.setDescricao( dto.descricao() );
        produto.setPreco( dto.preco() );
        produto.setQuantidadeEstoque( dto.quantidadeEstoque() );

        return produto;
    }

    @Override
    public ProdutoRequestDto toDto(Produto produto) {
        if ( produto == null ) {
            return null;
        }

        String nome = null;
        String descricao = null;
        BigDecimal preco = null;
        Integer quantidadeEstoque = null;

        nome = produto.getNome();
        descricao = produto.getDescricao();
        preco = produto.getPreco();
        quantidadeEstoque = produto.getQuantidadeEstoque();

        ProdutoRequestDto produtoRequestDto = new ProdutoRequestDto( nome, descricao, preco, quantidadeEstoque );

        return produtoRequestDto;
    }

    @Override
    public ProdutoResponseDto toResponse(Produto produto) {
        if ( produto == null ) {
            return null;
        }

        UUID id = null;
        String nome = null;
        String descricao = null;
        BigDecimal preco = null;
        Integer quantidadeEstoque = null;
        Boolean ativo = null;

        id = produto.getId();
        nome = produto.getNome();
        descricao = produto.getDescricao();
        preco = produto.getPreco();
        quantidadeEstoque = produto.getQuantidadeEstoque();
        ativo = produto.getAtivo();

        ProdutoResponseDto produtoResponseDto = new ProdutoResponseDto( id, nome, descricao, preco, quantidadeEstoque, ativo );

        return produtoResponseDto;
    }
}
