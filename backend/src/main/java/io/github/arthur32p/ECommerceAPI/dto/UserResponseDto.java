package io.github.arthur32p.ECommerceAPI.dto;

import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String name,
        String email
) {
}
