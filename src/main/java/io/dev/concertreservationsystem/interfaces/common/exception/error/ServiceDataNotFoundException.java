package io.dev.concertreservationsystem.interfaces.common.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.NoSuchElementException;

@Getter
@Setter
@AllArgsConstructor
public class ServiceDataNotFoundException extends NoSuchElementException {
    ErrorCode errorCode;
    String serviceName;
    String methodName;
}
