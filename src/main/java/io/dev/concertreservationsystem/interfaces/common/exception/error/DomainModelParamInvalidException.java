package io.dev.concertreservationsystem.interfaces.common.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.net.BindException;

@Getter
@Setter
@AllArgsConstructor
public class DomainModelParamInvalidException extends IllegalArgumentException {
    ErrorCode errorCode;
    String DomainName;
    String methodName;
}
