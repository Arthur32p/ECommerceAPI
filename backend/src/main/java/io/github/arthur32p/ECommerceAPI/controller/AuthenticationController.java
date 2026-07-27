package io.github.arthur32p.ECommerceAPI.controller;

import io.github.arthur32p.ECommerceAPI.controller.common.GenericController;
import io.github.arthur32p.ECommerceAPI.dto.LoginRequestDto;
import io.github.arthur32p.ECommerceAPI.dto.RegisterRequestDto;
import io.github.arthur32p.ECommerceAPI.dto.TokenResponseDto;
import io.github.arthur32p.ECommerceAPI.dto.UserResponseDto;
import io.github.arthur32p.ECommerceAPI.model.User;
import io.github.arthur32p.ECommerceAPI.model.UserAuthenticated;
import io.github.arthur32p.ECommerceAPI.service.TokenService;
import io.github.arthur32p.ECommerceAPI.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController implements GenericController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody @Valid RegisterRequestDto dto){
        UserResponseDto savedUser = userService.register(dto);
        URI location = gerarHeaderLocation(savedUser.id());

        return ResponseEntity.created(location).body(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> authenticate(@RequestBody @Valid LoginRequestDto dto){
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((UserAuthenticated) auth.getPrincipal());

        return ResponseEntity.ok(new TokenResponseDto(token));
    }
}
