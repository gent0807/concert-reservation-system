package io.dev.concertreservationsystem.domain.reservation;

import io.dev.concertreservationsystem.domain.outbox.OutboxDTOParam;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReservationSuccessEvent {
    private Long reservationId;

    private Long seatId;

    private String userId;

    private Long paymentId;

    private Integer reservationStatus;

}
