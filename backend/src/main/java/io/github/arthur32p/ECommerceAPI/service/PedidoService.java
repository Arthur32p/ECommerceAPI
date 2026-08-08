package io.github.arthur32p.ECommerceAPI.service;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import io.github.arthur32p.ECommerceAPI.dto.CheckoutResponse;
import io.github.arthur32p.ECommerceAPI.exceptions.CarrinhoVazioException;
import io.github.arthur32p.ECommerceAPI.exceptions.EstoqueInsuficienteException;
import io.github.arthur32p.ECommerceAPI.model.*;
import io.github.arthur32p.ECommerceAPI.repository.CarrinhoRepository;
import io.github.arthur32p.ECommerceAPI.repository.PedidoRepository;
import io.github.arthur32p.ECommerceAPI.repository.ProdutoRepository;
import io.github.arthur32p.ECommerceAPI.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final CarrinhoRepository carrinhoRepository;
    private final ProdutoRepository produtoRepository;
    private final PaymentService paymentService;
    private final UserRepository userRepository;

    @Transactional(rollbackFor = Exception.class)
    public CheckoutResponse realizarCheckout(UserAuthenticated userAuthenticated) throws StripeException {
        User user = userRepository.findById(userAuthenticated.getId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        Carrinho carrinho = carrinhoRepository.findByUserId(userAuthenticated.getId())
                .orElseThrow(() -> new EntityNotFoundException("Carrinho não encontrado"));

        if (carrinho.getItens().isEmpty()) {
            throw new CarrinhoVazioException("Não é possível realizar o checkout com o carrinho vazio.");
        }

        BigDecimal valorTotal = BigDecimal.ZERO;
        List<ItemPedido> itensPedido = new ArrayList<>();

        Pedido pedido = new Pedido();
        pedido.setUser(user);
        pedido.setStatus(StatusPedido.PENDENTE);
        pedido.setDataCriacao(LocalDateTime.now());

        for (ItemCarrinho item : carrinho.getItens()) {
            Produto produto = item.getProduto();

            if (produto.getQuantidadeEstoque() < item.getQuantidade()) {
                throw new EstoqueInsuficienteException("Estoque insuficiente para o produto: " + produto.getNome());
            }

            int novoEstoque = produto.getQuantidadeEstoque() - item.getQuantidade();
            produto.setQuantidadeEstoque(novoEstoque);

            if(novoEstoque == 0){
                produto.setAtivo(false);
            }

            produtoRepository.save(produto);

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setPedido(pedido);
            itemPedido.setProduto(produto);
            itemPedido.setQuantidade(item.getQuantidade());
            itemPedido.setPrecoUnitario(produto.getPreco());

            itensPedido.add(itemPedido);

            BigDecimal subtotal = produto.getPreco().multiply(BigDecimal.valueOf(item.getQuantidade()));
            valorTotal = valorTotal.add(subtotal);
        }

        pedido.setItens(itensPedido);
        pedido.setValorTotal(valorTotal);

        pedido = pedidoRepository.save(pedido);

        Session session = paymentService.criarSessaoCheckout(pedido);

        pedido.setStripePaymentIntentId(session.getId());
        pedidoRepository.save(pedido);

        carrinho.getItens().clear();
        carrinho.setValorTotal(BigDecimal.ZERO);
        carrinhoRepository.save(carrinho);

        return new CheckoutResponse(pedido.getId(), session.getUrl());
    }
}