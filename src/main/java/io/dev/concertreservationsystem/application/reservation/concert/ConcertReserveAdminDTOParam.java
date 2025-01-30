package io.dev.concertreservationsystem.application.reservation.concert;

import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetailDTOParam;
import io.dev.concertreservationsystem.domain.payment.PaymentDTOParam;
import io.dev.concertreservationsystem.domain.payment.PaymentDTOResult;
import io.dev.concertreservationsystem.domain.point_history.PointHistoryDTOParam;
import io.dev.concertreservationsystem.domain.reservation.ReservationDTOParam;
import io.dev.concertreservationsystem.domain.seat.SeatDTOParam;
import io.dev.concertreservationsystem.domain.user.UserDTOParam;
import io.dev.concertreservationsystem.common.validation.interfaces.CreateReservations;
import io.dev.concertreservationsystem.common.validation.interfaces.ProcessPayment;
import io.dev.concertreservationsystem.common.validation.interfaces.SearchReservableConcertDetail;
import io.dev.concertreservationsystem.common.validation.interfaces.SearchReservableSeat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Slf4j
public record ConcertReserveAdminDTOParam(

        @NotNull(groups = {CreateReservations.class, ProcessPayment.class})
        @Min(value = 0, groups = {CreateReservations.class, ProcessPayment.class})
        String userId,


        @NotNull(groups = SearchReservableConcertDetail.class)
        @Min(value = 0, groups = SearchReservableConcertDetail.class)
        Long concertBasicId,

        @NotNull(groups = {SearchReservableConcertDetail.class,  SearchReservableSeat.class})
        @Min(value = 0, groups = {SearchReservableConcertDetail.class,  SearchReservableSeat.class})
        Long concertDetailId,

        @NotNull(groups = CreateReservations.class)
        @Min(value = 0, groups = CreateReservations.class)
        Long seatId,

        @NotNull
        @Min(value = 0)
        Long reservationId,

        @NotNull(groups = ProcessPayment.class)
        @Min(value = 0, groups = ProcessPayment.class)
        Long paymentId

){


    //@Validated(CreateReservations.class)
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

    //@Validated(CreateReservations.class)
    public static List<SeatDTOParam> convertToSeatDTOParamList(List<ConcertReserveAdminDTOParam> concertReserveAdminDTOParamList) {

        return concertReserveAdminDTOParamList.stream().map(ConcertReserveAdminDTOParam::convertToSeatDTOParam).collect(Collectors.toList());
    }


    public UserDTOParam convertToUserDTOParam() {
        return UserDTOParam.builder()
                .userId(this.userId)
                .paymentId(this.paymentId)
                .build();
    }

    public PointHistoryDTOParam convertToPointHistoryDTOParam() {
        return PointHistoryDTOParam.builder()
                .userId(this.userId)
                .paymentId(this.paymentId)
                .build();
    }

    public ConcertDetailDTOParam convertToConcertDetailDTOParam() {
        return ConcertDetailDTOParam.builder()
                .concertBasicId(this.concertBasicId)
                .concertDetailId(this.concertDetailId)
                .build();

    }

    private SeatDTOParam convertToSeatDTOParam() {

        return SeatDTOParam.builder()
                .seatId(this.seatId)
                .build();
    }



    public PaymentDTOParam convertToPaymentDTOParam() {
        return PaymentDTOParam.builder()
                .userId(this.userId)
                .paymentId(this.paymentId)
                .build();
    }


    public ReservationDTOParam convertToReservationDTOParam() {
        return ReservationDTOParam.builder()
                .userId(this.userId)
                .seatId(this.seatId)
                .paymentId(this.paymentId)
                .build();
    }

}
