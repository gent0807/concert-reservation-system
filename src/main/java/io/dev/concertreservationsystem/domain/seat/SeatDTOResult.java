package io.dev.concertreservationsystem.domain.seat;

import io.dev.concertreservationsystem.application.reservation.concert.ConcertReserveAdminDTOResult;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public record SeatDTOResult(
        Long seatId,
        Long concertDetailId,
        Integer seatNumber,
        Long price,
        SeatStatusType seatStatus,
        LocalDateTime expiredAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static List<ConcertReserveAdminDTOResult> convertToConcertReserveAdminDTOResultList(List<SeatDTOResult> seatDTOResultList) {
        return seatDTOResultList.stream().map(SeatDTOResult::convertToConcertReserveAdminDTOResult).collect(Collectors.toList());
    }

    private ConcertReserveAdminDTOResult convertToConcertReserveAdminDTOResult() {
        return ConcertReserveAdminDTOResult.builder()
                .seatDTOResult(this)
                .build();
    }
}
