package io.dev.concertreservationsystem.domain.concert_detail;

import io.dev.concertreservationsystem.interfaces.api.common.validation.interfaces.SearchReservableConcertDetail;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ConcertDetailDTOParam(

        @NotNull(groups = SearchReservableConcertDetail.class)
        @NotBlank(groups = SearchReservableConcertDetail.class)
        @Min(value = 0, groups = SearchReservableConcertDetail.class)
        Long concertBasicId,

        @NotNull(groups = SearchReservableConcertDetail.class)
        @NotBlank(groups = SearchReservableConcertDetail.class)
        @Min(value = 0, groups = SearchReservableConcertDetail.class)
        Long concertDetailId
) {
}
