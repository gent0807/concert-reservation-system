package io.dev.concertreservationsystem.application.reservation.concert;

import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetailStatus;
import io.dev.concertreservationsystem.interfaces.api.concert_detail.ConcertDetailResponseDTO;
import io.dev.concertreservationsystem.interfaces.api.payment.PaymentResponseDTO;
import io.dev.concertreservationsystem.interfaces.api.seat.SeatResponseDTO;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public record ConcertReserveAdminDTOResult(
        Long concertDetailId,

        Long concertBasicId,

        ConcertDetailStatus concertDetailStatus,

        LocalDateTime startTime,

        LocalDateTime endTime,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
    public static List<ConcertDetailResponseDTO> convertToConcertDetailResponseDTOList(List<ConcertReserveAdminDTOResult> concertReserveAdminDTOResultList) {
        return concertReserveAdminDTOResultList.stream().map(ConcertReserveAdminDTOResult::convertToConcertDetailResponseDTO).collect(Collectors.toList());
    }

    private ConcertDetailResponseDTO convertToConcertDetailResponseDTO() {
        return ConcertDetailResponseDTO.builder()
                .concertDetailId(this.concertDetailId)
                .concertBasicId(this.concertBasicId)
                .concertDetailStatus(this.concertDetailStatus)
                .startTime(this.startTime)
                .endTime(this.endTime)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();

    }

    public static List<SeatResponseDTO> convertToSeatResponseDTOList(List<ConcertReserveAdminDTOResult> concertReserveAdminDTOResultList) {
        return concertReserveAdminDTOResultList.stream().map(ConcertReserveAdminDTOResult::convertToSeatResponseDTO).collect(Collectors.toList());
    }

    private SeatResponseDTO convertToSeatResponseDTO() {
        return SeatResponseDTO.builder()
                .build();
    }
}
