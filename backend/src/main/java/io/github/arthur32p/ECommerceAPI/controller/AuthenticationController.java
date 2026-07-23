package io.github.arthur32p.ECommerceAPI.controller;

import io.github.arthur32p.ECommerceAPI.controller.common.GenericController;
import io.github.arthur32p.ECommerceAPI.dto.RegisterRequestDto;
import io.github.arthur32p.ECommerceAPI.dto.TokenResponseDto;
import io.github.arthur32p.ECommerceAPI.dto.UserResponseDto;
import io.github.arthur32p.ECommerceAPI.model.User;
import io.github.arthur32p.ECommerceAPI.service.AuthenticationService;
import io.github.arthur32p.ECommerceAPI.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController implements GenericController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody RegisterRequestDto dto){
        UserResponseDto savedUser = userService.register(dto);
        URI location = gerarHeaderLocation(savedUser.id());

        return ResponseEntity.created(location).body(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> authenticate(Authentication authentication){
        String token = authenticationService.authenticate(authentication);
        return ResponseEntity.ok(new TokenResponseDto(token, 3600L));
    }
}
