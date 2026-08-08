package io.github.arthur32p.ECommerceAPI.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ErrorResponse(int status, String message, List<FieldErrorDto> errorList) {
    public static ErrorResponse conflict(String message){
        return new ErrorResponse(HttpStatus.CONFLICT.value(), message, List.of());
    }

    public static ErrorResponse notFound(String message){
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), message, List.of());
    }

    public static ErrorResponse badRequest(String message){
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message, List.of());
    }

    public static ErrorResponse unauthorized(String message){
        return new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), message, List.of());
    }

    public static ErrorResponse forbidden(String message){
        return new ErrorResponse(HttpStatus.FORBIDDEN.value(), message, List.of());
    }

    public static ErrorResponse paymentRequired(String message){
        return new ErrorResponse(HttpStatus.PAYMENT_REQUIRED.value(), message, List.of());
    }

    public static ErrorResponse methodNotAllowed(String message){
        return new ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED.value(), message, List.of());
    }

    public static ErrorResponse internalError(String message){
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, List.of());
    }
}
