package io.dev.concertreservationsystem.common.exception.handler;

import io.dev.concertreservationsystem.common.exception.error.DomainModelParamInvalidException;
import io.dev.concertreservationsystem.common.exception.error.ServiceDataNotFoundException;
import io.dev.concertreservationsystem.interfaces.common.exception.error.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestControllerAdvice
@Slf4j
public class APICustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ServiceDataNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDataNotFoundException(ServiceDataNotFoundException e) {

        String uuid = "ERROR-" + UUID.randomUUID();

        log.error("UUID - [{}] | <<--------------------------------------------------------------------------",uuid);
        log.error("UUID - [{}] | ERROR TIME : {}", uuid, LocalDateTime.now());
        log.error("UUID - [{}] | SERVICE : {}", uuid, e.getServiceName());
        log.error("UUID - [{}] | METHOD : {}", uuid, e.getMethodName());
        log.error("UUID - [{}] | ERROR CODE: [{}]", uuid, e.getErrorCode().toString());
        log.error("UUID - [{}] | -------------------------------------------------------------------------->>",uuid);

        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(value = DomainModelParamInvalidException.class)
    public ResponseEntity<ErrorResponse> handleDataParamInvalidException(DomainModelParamInvalidException e) {

        String uuid = "ERROR-" + UUID.randomUUID();

        log.error("UUID - [{}] | <<--------------------------------------------------------------------------",uuid);
        log.error("UUID - [{}] | ERROR TIME : {}", uuid, LocalDateTime.now());
        log.error("UUID - [{}] | DOMAIN ENTITY : {}", uuid, e.getDomainName());
        log.error("UUID - [{}] | METHOD : {}", uuid, e.getMethodName());
        log.error("UUID - [{}] | ERROR CODE: [{}]", uuid, e.getErrorCode().toString());
        log.error("UUID - [{}] | -------------------------------------------------------------------------->>",uuid);


        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    // @Valid 예외 처리
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        // Collect validation errors
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        String uuid = "ERROR-" + UUID.randomUUID();

        log.error("UUID - [{}] | <<--------------------------------------------------------------------------",uuid);
        log.error("UUID - [{}] | ERROR TIME : {}", uuid, LocalDateTime.now());
        log.error("UUID - [{}] | VALIDATION ERROR  : [{}]", uuid, errors);
        log.error("UUID - [{}] | -------------------------------------------------------------------------->>",uuid);


        // Return response with validation error details
        return ResponseEntity.badRequest().body(ErrorResponse.builder()
                                                        .errorMap(errors)
                                                        .build());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {

        log.error("EXCEPTION : [{}]", e.getMessage());


        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder()
                                                        .message(e.getMessage())
                                                        .build());
    }




}
