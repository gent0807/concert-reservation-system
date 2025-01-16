package io.dev.concertreservationsystem.domain.concert_detail;

import io.dev.concertreservationsystem.interfaces.api.common.validation.interfaces.CreateReservations;
import io.dev.concertreservationsystem.interfaces.api.common.validation.interfaces.ProcessPayment;
import io.dev.concertreservationsystem.interfaces.api.common.validation.interfaces.SearchReservableConcertDetail;
import io.dev.concertreservationsystem.interfaces.api.common.validation.interfaces.SearchReservableSeat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ConcertDetailDTOParam(

        @NotBlank(groups = SearchReservableConcertDetail.class)
        @Min(value = 0, groups = SearchReservableConcertDetail.class)
        Long concertBasicId,

        @NotBlank(groups = {SearchReservableConcertDetail.class, SearchReservableSeat.class, CreateReservations.class, ProcessPayment.class})
        @Min(value = 0, groups = {SearchReservableConcertDetail.class, SearchReservableSeat.class, CreateReservations.class, ProcessPayment.class })
        Long concertDetailId
) {
}
