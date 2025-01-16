package io.dev.concertreservationsystem.interfaces.common.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PointHistoryInvalidException extends IllegalArgumentException {
    ErrorCode errorCode;
}
