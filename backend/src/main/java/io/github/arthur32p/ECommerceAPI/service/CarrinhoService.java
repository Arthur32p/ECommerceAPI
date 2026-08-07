package io.github.arthur32p.ECommerceAPI.service;

import io.github.arthur32p.ECommerceAPI.dto.AtualizarQuantidadeItemDto;
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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarrinhoService {

    private final CarrinhoRepository carrinhoRepository;
    private final ProdutoRepository produtoRepository;
    private final CarrinhoMapper carrinhoMapper;
    private final UserRepository userRepository;

    @Transactional
    public CarrinhoResponseDto adicionarItem(UserAuthenticated userAuthenticated, ItemCarrinhoRequestDto dto) {
        Carrinho carrinho = carrinhoRepository.findByUserId(userAuthenticated.getId())
                .orElseGet(() -> {
                    User userReference = userRepository.getReferenceById(userAuthenticated.getId());
                    Carrinho novoCarrinho = new Carrinho();
                    novoCarrinho.setUser(userReference);
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

    @Transactional(readOnly = true)
    public CarrinhoResponseDto buscarCarrinho(UserAuthenticated userAuthenticated) {
        Carrinho carrinho = carrinhoRepository.findByUserId(userAuthenticated.getId())
                .orElseThrow(() -> new EntityNotFoundException("Carrinho não encontrado"));

        return carrinhoMapper.toDto(carrinho);
    }

    @Transactional
    public CarrinhoResponseDto atualizarCarrinho(UserAuthenticated userAuthenticated, UUID itemId, AtualizarQuantidadeItemDto dto) {
        Carrinho carrinho = carrinhoRepository.findByUserId(userAuthenticated.getId())
                .orElseThrow(() -> new EntityNotFoundException("Carrinho não encontrado"));

        ItemCarrinho item = carrinho.getItens().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Item não encontrado no carrinho"));

        if (item.getProduto().getQuantidadeEstoque() < dto.quantidade()) {
            throw new IllegalArgumentException("Estoque insuficiente para o produto: " + item.getProduto().getNome());
        }

        item.setQuantidade(dto.quantidade());

        recalcularValorTotal(carrinho);
        Carrinho carrinhoSalvo = carrinhoRepository.save(carrinho);

        return carrinhoMapper.toDto(carrinhoSalvo);
    }

    @Transactional
    public void deletarItem(UserAuthenticated userAuthenticated, UUID itemId) {
        Carrinho carrinho = carrinhoRepository.findByUserId(userAuthenticated.getId())
                .orElseThrow(() -> new EntityNotFoundException("Carrinho não encontrado"));

        ItemCarrinho item = carrinho.getItens().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Item não encontrado no carrinho"));

        carrinho.getItens().remove(item);

        recalcularValorTotal(carrinho);
        carrinhoRepository.save(carrinho);
    }

    private void recalcularValorTotal(Carrinho carrinho) {
        BigDecimal total = carrinho.getItens().stream()
                .map(ItemCarrinho::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        carrinho.setValorTotal(total);
    }
}