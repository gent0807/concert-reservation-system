package io.dev.concertreservationsystem.interfaces.api.common.exception.handler;

import io.dev.concertreservationsystem.domain.user.User;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.TokenInvalidException;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.TokenNotFoundException;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.UserInvalidException;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class APICustomExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = UserInvalidException.class)
    public ResponseEntity<ErrorResponse> handleUserInvalidException(UserInvalidException e) {
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(value = TokenInvalidException.class)
    public ResponseEntity<ErrorResponse> handleTokenInvalidException(TokenInvalidException e) {
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(value = TokenNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTokenNotFoundException(TokenNotFoundException e) {
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }


}
