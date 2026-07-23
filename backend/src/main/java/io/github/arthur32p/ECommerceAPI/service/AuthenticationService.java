package io.github.arthur32p.ECommerceAPI.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;

    public String authenticate(Authentication authentication){
        return jwtService.generateToken(authentication);
    }

}
