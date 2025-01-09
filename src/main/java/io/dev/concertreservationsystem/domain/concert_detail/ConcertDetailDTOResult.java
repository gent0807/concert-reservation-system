package io.dev.concertreservationsystem.domain.concert_detail;

import io.dev.concertreservationsystem.application.reservation.concert.ConcertReserveAdminDTOResult;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public record ConcertDetailDTOResult(

        Long concertDetailId,

        Long concertBasicId,

        ConcertDetailStatus concertDetailStatus,

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
                .concertDetailId(this.concertDetailId)
                .concertBasicId(this.concertBasicId)
                .concertDetailStatus(this.concertDetailStatus)
                .startTime(this.startTime)
                .endTime(this.endTime)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }
}
