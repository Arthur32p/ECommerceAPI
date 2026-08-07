package io.github.arthur32p.ECommerceAPI.controller;

import io.github.arthur32p.ECommerceAPI.dto.AtualizarQuantidadeItemDto;
import io.github.arthur32p.ECommerceAPI.dto.CarrinhoResponseDto;
import io.github.arthur32p.ECommerceAPI.dto.ItemCarrinhoRequestDto;
import io.github.arthur32p.ECommerceAPI.model.User;
import io.github.arthur32p.ECommerceAPI.model.UserAuthenticated;
import io.github.arthur32p.ECommerceAPI.service.CarrinhoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/carrinho")
@RequiredArgsConstructor
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    @PostMapping("/itens")
    public ResponseEntity<CarrinhoResponseDto> adicionarItem(@AuthenticationPrincipal UserAuthenticated userAuthenticated, @RequestBody @Valid ItemCarrinhoRequestDto dto){
        CarrinhoResponseDto carrinhoSalvo= carrinhoService.adicionarItem(userAuthenticated, dto);
        return ResponseEntity.ok(carrinhoSalvo);
    }

    @GetMapping
    public ResponseEntity<CarrinhoResponseDto> buscarCarrinho(@AuthenticationPrincipal UserAuthenticated userAuthenticated){
        CarrinhoResponseDto carrinho = carrinhoService.buscarCarrinho(userAuthenticated);

        return ResponseEntity.ok(carrinho);
    }

    @PutMapping("/itens/{itemId}")
    public ResponseEntity<CarrinhoResponseDto> atualizarCarrinho(@AuthenticationPrincipal UserAuthenticated userAuthenticated, @PathVariable UUID itemId, @RequestBody @Valid AtualizarQuantidadeItemDto dto){
        CarrinhoResponseDto carrinhoAtualizado =  carrinhoService.atualizarCarrinho(userAuthenticated, itemId, dto);

        return ResponseEntity.ok(carrinhoAtualizado);
    }

    @DeleteMapping("/itens/{itemId}")
    public ResponseEntity<Void> deletarItem(@AuthenticationPrincipal UserAuthenticated userAuthenticated, @PathVariable UUID itemId){
        carrinhoService.deletarItem(userAuthenticated, itemId);

        return ResponseEntity.noContent().build();
    }
}
