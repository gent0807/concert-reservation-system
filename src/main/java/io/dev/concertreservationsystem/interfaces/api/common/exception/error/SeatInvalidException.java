package io.dev.concertreservationsystem.interfaces.api.common.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SeatInvalidException extends IllegalArgumentException {
   ErrorCode errorCode;
}
