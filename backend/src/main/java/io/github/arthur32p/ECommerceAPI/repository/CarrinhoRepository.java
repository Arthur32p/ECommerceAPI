package io.github.arthur32p.ECommerceAPI.repository;

import io.github.arthur32p.ECommerceAPI.model.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

public interface CarrinhoRepository extends JpaRepository<Carrinho, UUID> {

    Optional<Carrinho> findByUserId(UUID id);
}
