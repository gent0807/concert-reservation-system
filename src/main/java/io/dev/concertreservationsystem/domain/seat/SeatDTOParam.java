package io.dev.concertreservationsystem.domain.seat;

import lombok.Builder;

@Builder
public record SeatDTOParam(
        Long seatId,
        String userId,
        Long paymentId
) {
}
