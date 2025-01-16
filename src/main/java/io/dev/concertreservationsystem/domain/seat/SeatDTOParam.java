package io.dev.concertreservationsystem.domain.seat;

import io.dev.concertreservationsystem.interfaces.api.common.validation.interfaces.CreateReservations;
import io.dev.concertreservationsystem.interfaces.api.common.validation.interfaces.ProcessPayment;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record SeatDTOParam(
        @NotBlank(groups = {CreateReservations.class, ProcessPayment.class})
        @Min(value = 0, groups = {CreateReservations.class, ProcessPayment.class})
        Long seatId
) {
}
