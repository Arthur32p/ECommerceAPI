package io.github.arthur32p.ECommerceAPI.service;

import io.github.arthur32p.ECommerceAPI.dto.CarrinhoResponseDto;
import io.github.arthur32p.ECommerceAPI.dto.ItemCarrinhoRequestDto;
import io.github.arthur32p.ECommerceAPI.mapper.CarrinhoMapper;
import io.github.arthur32p.ECommerceAPI.model.*;
import io.github.arthur32p.ECommerceAPI.repository.CarrinhoRepository;
import io.github.arthur32p.ECommerceAPI.repository.ProdutoRepository;
import io.github.arthur32p.ECommerceAPI.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarrinhoService {

    private final CarrinhoRepository carrinhoRepository;
    private final ProdutoRepository produtoRepository;
    private final CarrinhoMapper carrinhoMapper;
    private final UserRepository userRepository;

    @Transactional
    public CarrinhoResponseDto adicionarItem(UserAuthenticated userAuthenticated, ItemCarrinhoRequestDto dto) {

        User user = userRepository.findById(userAuthenticated.getId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        Carrinho carrinho = carrinhoRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    Carrinho novoCarrinho = new Carrinho();
                    novoCarrinho.setUser(user);
                    return carrinhoRepository.save(novoCarrinho);
                });

        Produto produto = produtoRepository.findById(dto.produtoId())
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        Optional<ItemCarrinho> itemExistente = carrinho.getItens().stream()
                .filter(item -> item.getProduto().getId().equals(produto.getId()))
                .findFirst();

        int quantidadeTotal = dto.quantidade();
        if (itemExistente.isPresent()) {
            quantidadeTotal += itemExistente.get().getQuantidade();
        }

        if (produto.getQuantidadeEstoque() < quantidadeTotal) {
            throw new IllegalArgumentException("Estoque insuficiente para o produto: " + produto.getNome());
        }

        if (itemExistente.isPresent()) {
            ItemCarrinho item = itemExistente.get();
            item.setQuantidade(quantidadeTotal);
        } else {
            ItemCarrinho novoItem = new ItemCarrinho();
            novoItem.setCarrinho(carrinho);
            novoItem.setProduto(produto);
            novoItem.setQuantidade(dto.quantidade());
            novoItem.setPrecoUnitario(produto.getPreco());
            carrinho.getItens().add(novoItem);
        }

        recalcularValorTotal(carrinho);
        Carrinho carrinhoSalvo = carrinhoRepository.save(carrinho);

        return carrinhoMapper.toDto(carrinhoSalvo);
    }

    private void recalcularValorTotal(Carrinho carrinho) {
        BigDecimal total = carrinho.getItens().stream()
                .map(ItemCarrinho::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        carrinho.setValorTotal(total);
    }
}