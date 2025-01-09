package io.dev.concertreservationsystem.domain.reservation;

import lombok.Builder;

@Builder
public record ReservationDTOParam(
        Long reservationId,
        String userId,
        Long seatId,
        Long paymentId
){
}
