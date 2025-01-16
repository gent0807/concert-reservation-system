package io.dev.concertreservationsystem.domain.reservation;

import io.dev.concertreservationsystem.application.reservation.concert.ConcertReserveAdminDTOResult;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public record ReservationDTOResult(
        Long reservationId,
        String userId,
        Long seatId,
        Long paymentId,
        Integer reservationStatus,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static List<ConcertReserveAdminDTOResult> convertToConcertReserveAdminDTOResultList(List<ReservationDTOResult> reservationDTOResultList) {
       return reservationDTOResultList.stream().map(ReservationDTOResult::converToConcertReserveAdminDTOResult).collect(Collectors.toList());
    }

    private ConcertReserveAdminDTOResult converToConcertReserveAdminDTOResult() {
        return ConcertReserveAdminDTOResult.builder()
                .reservationDTOResult(this)
                .build();
    }

}
