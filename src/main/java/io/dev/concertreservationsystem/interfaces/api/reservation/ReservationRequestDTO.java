package io.dev.concertreservationsystem.interfaces.api.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dev.concertreservationsystem.application.reservation.concert.ConcertReserveAdminDTOParam;
import io.dev.concertreservationsystem.application.reservation.concert.ConcertReserveAdminDTOResult;
import io.dev.concertreservationsystem.interfaces.api.common.validation.interfaces.CreateReservations;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record ReservationRequestDTO(
        @JsonProperty("reservationId")
        Long reservationId,

        @JsonProperty("concertDetailId")
        @NotBlank(groups = CreateReservations.class)
        @Min(value = 0, groups = CreateReservations.class)
        Long concertDetailId,

        @JsonProperty("seatId")
        @NotBlank(groups = CreateReservations.class)
        @Min(value = 0, groups = CreateReservations.class)
        Long seatId,

        @JsonProperty("userId")
        @NotBlank(groups = CreateReservations.class)
        String userId,

        @NotBlank
        @Min(value = 0)
        @JsonProperty("paymentId") Long paymentId,

        @JsonProperty("reservationStatus") Integer reservationStatus,
        @JsonProperty("createdAt") LocalDateTime createdAt,
        @JsonProperty("updatedAt") LocalDateTime updatedAt
) {
    public static List<ConcertReserveAdminDTOParam> convertToConcertReserveAdminDTOParamList(List<ReservationRequestDTO> reservationRequestDTOList) {
        return reservationRequestDTOList.stream().map(ReservationRequestDTO::convertToConcertReserveAdminDTOParam).toList();
    }

    private ConcertReserveAdminDTOParam convertToConcertReserveAdminDTOParam() {
        return ConcertReserveAdminDTOParam.builder()
                .seatId(this.seatId)
                .userId(this.userId)
                .paymentId(this.paymentId)
                .build();
    }
}
