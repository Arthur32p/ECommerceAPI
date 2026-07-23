package io.github.arthur32p.ECommerceAPI.mapper;

import io.github.arthur32p.ECommerceAPI.dto.RegisterRequestDto;
import io.github.arthur32p.ECommerceAPI.dto.UserResponseDto;
import io.github.arthur32p.ECommerceAPI.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    public abstract User toEntity(RegisterRequestDto dto);

    public abstract RegisterRequestDto toDto(User user);

    public abstract UserResponseDto toResponse(User user);

}
