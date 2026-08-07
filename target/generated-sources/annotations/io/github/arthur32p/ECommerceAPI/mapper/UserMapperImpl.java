package io.github.arthur32p.ECommerceAPI.mapper;

import io.github.arthur32p.ECommerceAPI.dto.RegisterRequestDto;
import io.github.arthur32p.ECommerceAPI.dto.UserResponseDto;
import io.github.arthur32p.ECommerceAPI.model.User;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-07T14:31:22-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.12 (Arch Linux)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(RegisterRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setName( dto.name() );
        user.setEmail( dto.email() );

        return user;
    }

    @Override
    public RegisterRequestDto toDto(User user) {
        if ( user == null ) {
            return null;
        }

        String name = null;
        String email = null;
        String password = null;

        name = user.getName();
        email = user.getEmail();
        password = user.getPassword();

        RegisterRequestDto registerRequestDto = new RegisterRequestDto( name, email, password );

        return registerRequestDto;
    }

    @Override
    public UserResponseDto toResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UUID id = null;
        String name = null;
        String email = null;

        id = user.getId();
        name = user.getName();
        email = user.getEmail();

        UserResponseDto userResponseDto = new UserResponseDto( id, name, email );

        return userResponseDto;
    }
}
