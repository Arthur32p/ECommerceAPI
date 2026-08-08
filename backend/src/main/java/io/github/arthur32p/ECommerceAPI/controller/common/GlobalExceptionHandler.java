package io.github.arthur32p.ECommerceAPI.controller.common;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import io.github.arthur32p.ECommerceAPI.dto.ErrorResponse;
import io.github.arthur32p.ECommerceAPI.dto.FieldErrorDto;
import io.github.arthur32p.ECommerceAPI.exceptions.CarrinhoVazioException;
import io.github.arthur32p.ECommerceAPI.exceptions.EstoqueInsuficienteException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<FieldErrorDto> errorList = fieldErrors
                .stream()
                .map(fe -> new FieldErrorDto(fe.getField(), fe.getDefaultMessage()))
                .toList();

        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Erro de validação", errorList);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerEntityNotFoundException(EntityNotFoundException e) {
        return ErrorResponse.notFound(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerIllegalArgumentException(IllegalArgumentException e) {
        return ErrorResponse.badRequest(e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return ErrorResponse.badRequest("O corpo da requisição está malformado ou contém dados inválidos.");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handlerDataIntegrityViolationException(DataIntegrityViolationException e) {
        return ErrorResponse.conflict("Registro já cadastrado ou em uso no sistema.");
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handlerBadCredentialsException(BadCredentialsException e) {
        return ErrorResponse.unauthorized("Credenciais inválidas.");
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handlerAccessDeniedException(AccessDeniedException e) {
        return ErrorResponse.forbidden("Acesso negado para este recurso.");
    }

    @ExceptionHandler(StripeException.class)
    @ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
    public ErrorResponse handlerStripeException(StripeException e) {
        return ErrorResponse.paymentRequired("Erro no processamento do pagamento: " + e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorResponse handlerHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return ErrorResponse.methodNotAllowed("O método " + e.getMethod() + " não é suportado para esta rota.");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handlerException(Exception e) {
        return ErrorResponse.internalError("Ocorreu um erro interno inesperado no servidor.");
    }

    @ExceptionHandler(CarrinhoVazioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerCarrinhoVazioException(CarrinhoVazioException e) {
        return ErrorResponse.badRequest(e.getMessage());
    }

    @ExceptionHandler(EstoqueInsuficienteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerEstoqueInsuficienteException(EstoqueInsuficienteException e) {
        return ErrorResponse.badRequest(e.getMessage());
    }

    @ExceptionHandler(SignatureVerificationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerSignatureVerificationException(SignatureVerificationException e) {
        return ErrorResponse.badRequest("Assinatura do Webhook inválida.");
    }
}
