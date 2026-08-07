package io.github.arthur32p.ECommerceAPI.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carrinho")
@RequiredArgsConstructor
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    @PostMapping("/itens")
    public ResponseEntity<CarrinhoResponseDto> adicionarItem(@AuthenticationPrincipal UserAuthenticated userAuthenticated, @RequestBody @Valid ItemCarrinhoRequestDto dto){
        CarrinhoResponseDto carrinhoAtualizado = carrinhoService.adicionarItem(userAuthenticated, dto);
        return ResponseEntity.ok(carrinhoAtualizado);
    }
}
