package io.github.arthur32p.ECommerceAPI.dto;

public record RegisterRequestDto(
        String name,
        String email,
        String password
) {
}
