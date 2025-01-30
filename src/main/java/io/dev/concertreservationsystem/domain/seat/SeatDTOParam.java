package io.dev.concertreservationsystem.domain.seat;

import io.dev.concertreservationsystem.common.validation.interfaces.CreateReservations;
import io.dev.concertreservationsystem.common.validation.interfaces.ProcessPayment;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record SeatDTOParam(
        @NotNull(groups = {CreateReservations.class, ProcessPayment.class})
        @Min(value = 0, groups = {CreateReservations.class, ProcessPayment.class})
        Long seatId
) {
}
