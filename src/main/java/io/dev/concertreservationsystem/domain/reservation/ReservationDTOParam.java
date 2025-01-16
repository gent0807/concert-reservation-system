package io.dev.concertreservationsystem.domain.reservation;

import io.dev.concertreservationsystem.interfaces.common.validation.interfaces.CreateReservations;
import io.dev.concertreservationsystem.interfaces.common.validation.interfaces.ProcessPayment;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record ReservationDTOParam(
        Long reservationId,
        Long concertDetailId,

        @NotBlank(groups = {CreateReservations.class, ProcessPayment.class})
        @Min(value = 0, groups = {CreateReservations.class, ProcessPayment.class})
        String userId,

        @NotBlank(groups = {CreateReservations.class})
        @Min(value = 0, groups = {CreateReservations.class})
        Long seatId,

        @NotBlank(groups = {CreateReservations.class, ProcessPayment.class})
        @Min(value = 0, groups = {CreateReservations.class, ProcessPayment.class})
        Long paymentId
){
}
