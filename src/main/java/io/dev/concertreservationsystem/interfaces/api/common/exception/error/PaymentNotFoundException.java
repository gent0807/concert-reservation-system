package io.dev.concertreservationsystem.interfaces.api.common.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.NoSuchElementException;

@Getter
@Setter
@AllArgsConstructor
public class PaymentNotFoundException extends NoSuchElementException {
    ErrorCode errorCode;
}
