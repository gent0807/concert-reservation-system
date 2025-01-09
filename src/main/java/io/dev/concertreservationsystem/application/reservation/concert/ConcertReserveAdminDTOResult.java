package io.dev.concertreservationsystem.application.reservation.concert;

import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetailDTOResult;
import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetailStatusType;
import io.dev.concertreservationsystem.domain.payment.PaymentDTOResult;
import io.dev.concertreservationsystem.domain.reservation.ReservationDTOResult;
import io.dev.concertreservationsystem.domain.seat.Seat;
import io.dev.concertreservationsystem.domain.seat.SeatDTOResult;
import io.dev.concertreservationsystem.interfaces.api.concert_detail.ConcertDetailResponseDTO;
import io.dev.concertreservationsystem.interfaces.api.reservation.ReservationResponseDTO;
import io.dev.concertreservationsystem.interfaces.api.seat.SeatResponseDTO;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public record ConcertReserveAdminDTOResult(

        ConcertDetailDTOResult concertDetailDTOResult,

        SeatDTOResult seatDTOResult,

        ReservationDTOResult reservationDTOResult,

        PaymentDTOResult paymentDTOResult
) {
    public static List<ConcertDetailResponseDTO> convertToConcertDetailResponseDTOList(List<ConcertReserveAdminDTOResult> concertReserveAdminDTOResultList) {
        return concertReserveAdminDTOResultList.stream().map(ConcertReserveAdminDTOResult::convertToConcertDetailResponseDTO).collect(Collectors.toList());
    }

    public static List<ReservationResponseDTO> convertToReservationResponseDTOList(List<ConcertReserveAdminDTOResult> concertReserveAdminDTOResultList) {
        return concertReserveAdminDTOResultList.stream().map(ConcertReserveAdminDTOResult::convertToReservationResponseDTO).collect(Collectors.toList());
    }

    private ReservationResponseDTO convertToReservationResponseDTO() {
        return ReservationResponseDTO.builder()
                .reservationId(this.reservationDTOResult.reservationId())
                .userId(this.reservationDTOResult.userId())
                .seatId(this.reservationDTOResult.seatId())
                .reservationStatus(this.reservationDTOResult.reservationStatus())
                .createdAt(this.reservationDTOResult.createdAt())
                .updatedAt(this.reservationDTOResult.updatedAt())
                .build();
    }

    private ConcertDetailResponseDTO convertToConcertDetailResponseDTO() {
        return ConcertDetailResponseDTO.builder()
                .concertDetailId(this.concertDetailDTOResult.concertDetailId())
                .concertBasicId(this.concertDetailDTOResult.concertBasicId())
                .concertDetailStatus(this.concertDetailDTOResult.concertDetailStatus())
                .startTime(this.concertDetailDTOResult.startTime())
                .endTime(this.concertDetailDTOResult.endTime())
                .createdAt(this.concertDetailDTOResult.createdAt())
                .updatedAt(this.concertDetailDTOResult.updatedAt())
                .build();

    }

    public static List<SeatResponseDTO> convertToSeatResponseDTOList(List<ConcertReserveAdminDTOResult> concertReserveAdminDTOResultList) {
        return concertReserveAdminDTOResultList.stream().map(ConcertReserveAdminDTOResult::convertToSeatResponseDTO).collect(Collectors.toList());
    }

    private SeatResponseDTO convertToSeatResponseDTO() {
        return SeatResponseDTO.builder()
                .seatId(this.seatDTOResult.seatId())
                .concertDetailId(this.seatDTOResult.concertDetailId())
                .seatNumber(this.seatDTOResult.seatNumber())
                .seatStatus(this.seatDTOResult.seatStatus())
                .price(this.seatDTOResult.price())
                .expiredAt(this.seatDTOResult.expiredAt())
                .createdAt(this.seatDTOResult.createdAt())
                .updatedAt(this.seatDTOResult.updatedAt())
                .build();
    }
}
