package github.ryz.swiftapi.controller;

import github.ryz.swiftapi.dto.ErrorResponseDto;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(Ordered.LOWEST_PRECEDENCE)
@RestControllerAdvice
public class GenericExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception e) {
        ResponseStatus responseStatus = e.getClass().getAnnotation(ResponseStatus.class);

        ErrorResponseDto err = new ErrorResponseDto(
                e.getClass().getSimpleName(),
                responseStatus != null ? responseStatus.value() : HttpStatus.INTERNAL_SERVER_ERROR,
                e.getMessage()
        );

        if (responseStatus != null) {
            return ResponseEntity.status(responseStatus.value()).body(err);
        }

        return ResponseEntity.internalServerError().body(err);
    }
}
