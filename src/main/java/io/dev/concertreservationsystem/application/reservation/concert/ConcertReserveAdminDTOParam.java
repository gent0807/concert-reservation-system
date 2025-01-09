package io.dev.concertreservationsystem.application.reservation.concert;

import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetailDTOParam;
import io.dev.concertreservationsystem.domain.payment.PaymentDTOResult;
import io.dev.concertreservationsystem.domain.reservation.ReservationDTOParam;
import io.dev.concertreservationsystem.domain.seat.SeatDTOParam;
import io.dev.concertreservationsystem.interfaces.api.common.validation.interfaces.SearchReservableConcertDetail;
import io.dev.concertreservationsystem.interfaces.api.common.validation.interfaces.SearchReservableSeat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record ConcertReserveAdminDTOParam(
        @NotNull(groups = SearchReservableConcertDetail.class)
        @NotBlank(groups = SearchReservableConcertDetail.class)
        @Min(value = 0, groups = SearchReservableConcertDetail.class)
        Long concertBasicId,

        @NotNull(groups = {SearchReservableConcertDetail.class,  SearchReservableSeat.class})
        @NotBlank(groups = {SearchReservableConcertDetail.class,  SearchReservableSeat.class})
        @Min(value = 0, groups = {SearchReservableConcertDetail.class,  SearchReservableSeat.class})
        Long concertDetailId,

        Long seatId,
        String userId,
        Long paymentId

){

    public static List<ReservationDTOParam> convertToReservationDTOParamList(List<ConcertReserveAdminDTOParam> concertReserveAdminDTOParamList) {
        return concertReserveAdminDTOParamList.stream().map(ConcertReserveAdminDTOParam::convertToReservationDTOParam).collect(Collectors.toList());
    }

    public static List<ReservationDTOParam> convertToReservationDTOParamList(List<ConcertReserveAdminDTOParam> concertReserveAdminDTOParamList, PaymentDTOResult paymentDTOResult) {
        return concertReserveAdminDTOParamList.stream().map(ConcertReserveAdminDTOParam::convertToReservationDTOParam)
                            .map(reservationDTOParam -> {
                                        return ReservationDTOParam.builder()
                                                .userId(reservationDTOParam.userId())
                                                .seatId(reservationDTOParam.seatId())
                                                .paymentId(paymentDTOResult.paymentId())
                                                .build();
                                        }).collect(Collectors.toList());
    }

    public static List<SeatDTOParam> convertToSeatDTOParamList(List<ConcertReserveAdminDTOParam> concertReserveAdminDTOParamList) {

        return concertReserveAdminDTOParamList.stream().map(ConcertReserveAdminDTOParam::convertToSeatDTOParam).collect(Collectors.toList());
    }

    public SeatDTOParam convertToSeatDTOParam() {
        return SeatDTOParam.builder()
                .seatId(this.seatId)
                .build();
    }


    public ReservationDTOParam convertToReservationDTOParam() {
        return ReservationDTOParam.builder()
                .userId(this.userId)
                .seatId(this.seatId)
                .paymentId(this.paymentId)
                .build();
    }

    public ConcertDetailDTOParam convertToConcertDetailDTOParam() {
        return ConcertDetailDTOParam.builder()
                .concertBasicId(this.concertBasicId)
                .concertDetailId(this.concertDetailId)
                .build();

    }
}
