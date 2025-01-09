package io.dev.concertreservationsystem.domain.seat;

import io.dev.concertreservationsystem.application.reservation.concert.ConcertReserveAdminDTOResult;

import java.util.List;

public record SeatDTOResult() {
    public static List<ConcertReserveAdminDTOResult> convertToConcertReserveAdminDTOResultList(List<SeatDTOResult> seatDTOResultList) {
        return null;
    }
}
