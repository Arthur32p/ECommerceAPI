package io.github.arthur32p.ECommerceAPI.repository;

import io.github.arthur32p.ECommerceAPI.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProdutoRepository extends JpaRepository<Produto, UUID> {

    Page<Produto> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

}
