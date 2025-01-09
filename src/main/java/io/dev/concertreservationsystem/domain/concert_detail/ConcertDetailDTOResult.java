package io.dev.concertreservationsystem.domain.concert_detail;

import io.dev.concertreservationsystem.application.reservation.concert.ConcertReserveAdminDTOResult;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public record ConcertDetailDTOResult(

        Long concertDetailId,

        Long concertBasicId,

        ConcertDetailStatusType concertDetailStatus,

        LocalDateTime startTime,

        LocalDateTime endTime,

        LocalDateTime createdAt,

        LocalDateTime updatedAt

) {
    public static List<ConcertReserveAdminDTOResult> convertToConcertReserveAdminDTOResultList(List<ConcertDetailDTOResult> concertDetailDTOResultList) {
        return concertDetailDTOResultList.stream().map(ConcertDetailDTOResult::convertToConcertReserveAdminDTOResult).collect(Collectors.toList());
    }

    private ConcertReserveAdminDTOResult convertToConcertReserveAdminDTOResult() {
        return ConcertReserveAdminDTOResult.builder()
                .concertDetailDTOResult(this)
                .build();
    }
}
