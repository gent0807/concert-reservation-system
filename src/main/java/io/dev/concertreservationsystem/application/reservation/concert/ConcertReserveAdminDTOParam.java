package io.dev.concertreservationsystem.application.reservation.concert;

import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetailDTOParam;
import io.dev.concertreservationsystem.interfaces.api.common.validation.interfaces.SearchReservableConcertDetail;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ConcertReserveAdminDTOParam(
        @NotNull(groups = SearchReservableConcertDetail.class)
        @NotBlank(groups = SearchReservableConcertDetail.class)
        @Min(value = 0, groups = SearchReservableConcertDetail.class)
        Long concertBasicId,

        @NotNull(groups = SearchReservableConcertDetail.class)
        @NotBlank(groups = SearchReservableConcertDetail.class)
        @Min(value = 0, groups = SearchReservableConcertDetail.class)
        Long concertDetailId
){

    public ConcertDetailDTOParam convertToConcertDetailDTOParam() {
        return ConcertDetailDTOParam.builder()
                .concertBasicId(this.concertBasicId)
                .concertDetailId(this.concertDetailId)
                .build();

    }
}
