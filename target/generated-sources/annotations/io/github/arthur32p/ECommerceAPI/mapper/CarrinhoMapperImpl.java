package io.github.arthur32p.ECommerceAPI.mapper;

import io.github.arthur32p.ECommerceAPI.dto.CarrinhoResponseDto;
import io.github.arthur32p.ECommerceAPI.dto.ItemCarrinhoResponseDto;
import io.github.arthur32p.ECommerceAPI.model.Carrinho;
import io.github.arthur32p.ECommerceAPI.model.ItemCarrinho;
import io.github.arthur32p.ECommerceAPI.model.Produto;
import io.github.arthur32p.ECommerceAPI.model.User;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-07T17:28:56-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.12 (Arch Linux)"
)
@Component
public class CarrinhoMapperImpl implements CarrinhoMapper {

    @Override
    public CarrinhoResponseDto toDto(Carrinho carrinho) {
        if ( carrinho == null ) {
            return null;
        }

        UUID userId = null;
        UUID id = null;
        List<ItemCarrinhoResponseDto> itens = null;
        BigDecimal valorTotal = null;

        userId = carrinhoUserId( carrinho );
        id = carrinho.getId();
        itens = itemCarrinhoListToItemCarrinhoResponseDtoList( carrinho.getItens() );
        valorTotal = carrinho.getValorTotal();

        CarrinhoResponseDto carrinhoResponseDto = new CarrinhoResponseDto( id, userId, itens, valorTotal );

        return carrinhoResponseDto;
    }

    @Override
    public ItemCarrinhoResponseDto toItemDTO(ItemCarrinho item) {
        if ( item == null ) {
            return null;
        }

        UUID produtoId = null;
        String produtoNome = null;
        UUID id = null;
        BigDecimal precoUnitario = null;
        Integer quantidade = null;
        BigDecimal subtotal = null;

        produtoId = itemProdutoId( item );
        produtoNome = itemProdutoNome( item );
        id = item.getId();
        precoUnitario = item.getPrecoUnitario();
        quantidade = item.getQuantidade();
        subtotal = item.getSubtotal();

        ItemCarrinhoResponseDto itemCarrinhoResponseDto = new ItemCarrinhoResponseDto( id, produtoId, produtoNome, precoUnitario, quantidade, subtotal );

        return itemCarrinhoResponseDto;
    }

    private UUID carrinhoUserId(Carrinho carrinho) {
        if ( carrinho == null ) {
            return null;
        }
        User user = carrinho.getUser();
        if ( user == null ) {
            return null;
        }
        UUID id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected List<ItemCarrinhoResponseDto> itemCarrinhoListToItemCarrinhoResponseDtoList(List<ItemCarrinho> list) {
        if ( list == null ) {
            return null;
        }

        List<ItemCarrinhoResponseDto> list1 = new ArrayList<ItemCarrinhoResponseDto>( list.size() );
        for ( ItemCarrinho itemCarrinho : list ) {
            list1.add( toItemDTO( itemCarrinho ) );
        }

        return list1;
    }

    private UUID itemProdutoId(ItemCarrinho itemCarrinho) {
        if ( itemCarrinho == null ) {
            return null;
        }
        Produto produto = itemCarrinho.getProduto();
        if ( produto == null ) {
            return null;
        }
        UUID id = produto.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String itemProdutoNome(ItemCarrinho itemCarrinho) {
        if ( itemCarrinho == null ) {
            return null;
        }
        Produto produto = itemCarrinho.getProduto();
        if ( produto == null ) {
            return null;
        }
        String nome = produto.getNome();
        if ( nome == null ) {
            return null;
        }
        return nome;
    }
}
