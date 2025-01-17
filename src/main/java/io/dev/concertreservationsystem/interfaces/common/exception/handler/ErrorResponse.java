package io.dev.concertreservationsystem.interfaces.common.exception.handler;

import io.dev.concertreservationsystem.interfaces.common.exception.error.ErrorCode;
import lombok.Builder;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Builder
public class ErrorResponse {
    private int status;
    private String name;
    private String code;
    private String message;
    private Map<String,String> errorMap;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode e){
        return ResponseEntity.status(e.getHttpStatus())
                .body(ErrorResponse.builder()
                        .status(e.getHttpStatus().value())
                        .name(e.name())
                        .code(e.getCode())
                        .message(e.getMessage())
                        .build());

    }

}
