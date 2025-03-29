package github.ryz.swiftapi.controller;

import github.ryz.swiftapi.dto.ErrorResponseDto;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleException(MethodArgumentNotValidException e) {
        var error = e.getBindingResult().getFieldError();

        ErrorResponseDto err = new ErrorResponseDto(
                e.getClass().getSimpleName(),
                HttpStatus.BAD_REQUEST,
                error != null ? error.getDefaultMessage() : e.getMessage()
        );

        return ResponseEntity.badRequest().body(err);
    }
}
