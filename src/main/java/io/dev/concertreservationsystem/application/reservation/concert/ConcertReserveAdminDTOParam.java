package io.dev.concertreservationsystem.application.reservation.concert;

import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetailDTOParam;
import io.dev.concertreservationsystem.domain.payment.PaymentDTOParam;
import io.dev.concertreservationsystem.domain.payment.PaymentDTOResult;
import io.dev.concertreservationsystem.domain.point_history.PointHistoryDTOParam;
import io.dev.concertreservationsystem.domain.reservation.ReservationDTOParam;
import io.dev.concertreservationsystem.domain.seat.SeatDTOParam;
import io.dev.concertreservationsystem.domain.user.UserDTOParam;
import io.dev.concertreservationsystem.interfaces.common.validation.interfaces.CreateReservations;
import io.dev.concertreservationsystem.interfaces.common.validation.interfaces.ProcessPayment;
import io.dev.concertreservationsystem.interfaces.common.validation.interfaces.SearchReservableConcertDetail;
import io.dev.concertreservationsystem.interfaces.common.validation.interfaces.SearchReservableSeat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record ConcertReserveAdminDTOParam(

        @NotBlank(groups = {CreateReservations.class, ProcessPayment.class})
        @Min(value = 0, groups = {CreateReservations.class, ProcessPayment.class})
        String userId,


        @NotBlank(groups = SearchReservableConcertDetail.class)
        @Min(value = 0, groups = SearchReservableConcertDetail.class)
        Long concertBasicId,

        @NotBlank(groups = {SearchReservableConcertDetail.class,  SearchReservableSeat.class})
        @Min(value = 0, groups = {SearchReservableConcertDetail.class,  SearchReservableSeat.class})
        Long concertDetailId,

        @NotBlank(groups = CreateReservations.class)
        @Min(value = 0, groups = CreateReservations.class)
        Long seatId,

        @NotBlank
        @Min(value = 0)
        Long reservationId,

        @NotBlank(groups = ProcessPayment.class)
        @Min(value = 0, groups = ProcessPayment.class)
        Long paymentId

){



    public static List<ReservationDTOParam> convertToReservationDTOParamList(List<ConcertReserveAdminDTOParam> concertReserveAdminDTOParamList) {
        return concertReserveAdminDTOParamList.stream().map(ConcertReserveAdminDTOParam::convertToReservationDTOParam).collect(Collectors.toList());
    }

    @Validated(CreateReservations.class)
    public static List<ReservationDTOParam> convertToReservationDTOParamList(List<@Valid ConcertReserveAdminDTOParam> concertReserveAdminDTOParamList, PaymentDTOResult paymentDTOResult) {
        return concertReserveAdminDTOParamList.stream().map(ConcertReserveAdminDTOParam::convertToReservationDTOParam)
                            .map(reservationDTOParam -> {
                                        return ReservationDTOParam.builder()
                                                .userId(reservationDTOParam.userId())
                                                .seatId(reservationDTOParam.seatId())
                                                .paymentId(paymentDTOResult.paymentId())
                                                .build();
                                        }).collect(Collectors.toList());
    }






    public ReservationDTOParam convertToReservationDTOParam() {
        return ReservationDTOParam.builder()
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


    public PaymentDTOParam convertToPaymentDTOParam() {
        return PaymentDTOParam.builder()
                .userId(this.userId)
                .paymentId(this.paymentId)
                .build();
    }
}
