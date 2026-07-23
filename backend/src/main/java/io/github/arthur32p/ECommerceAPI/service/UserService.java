package io.github.arthur32p.ECommerceAPI.service;

import io.github.arthur32p.ECommerceAPI.dto.RegisterRequestDto;
import io.github.arthur32p.ECommerceAPI.dto.UserResponseDto;
import io.github.arthur32p.ECommerceAPI.mapper.UserMapper;
import io.github.arthur32p.ECommerceAPI.model.Role;
import io.github.arthur32p.ECommerceAPI.model.User;
import io.github.arthur32p.ECommerceAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional
    public UserResponseDto register(RegisterRequestDto dto){
        userRepository.findByEmail(dto.email()).ifPresent(u -> {
            throw  new IllegalArgumentException("Usuário já está criado");
        });

        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRole(Role.USER);

        User savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }

}
