package io.github.arthur32p.ECommerceAPI.repository;

import io.github.arthur32p.ECommerceAPI.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PedidoRepository extends JpaRepository<Pedido, UUID> {

    Optional<Pedido> findByStripePaymentIntentId(String stripePaymentIntentId);

}
