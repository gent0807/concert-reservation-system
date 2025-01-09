package io.dev.concertreservationsystem.interfaces.api.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dev.concertreservationsystem.application.reservation.concert.ConcertReserveAdminDTOParam;
import io.dev.concertreservationsystem.application.reservation.concert.ConcertReserveAdminDTOResult;

import java.time.LocalDateTime;
import java.util.List;

public record ReservationRequestDTO(
        @JsonProperty("reservationId") Long reservationId,
        @JsonProperty("seatId") Long seatId,
        @JsonProperty("userId") String userId,
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
