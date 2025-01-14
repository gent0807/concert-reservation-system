package io.dev.concertreservationsystem.domain.reservation;

import io.dev.concertreservationsystem.domain.seat.SeatDTOParam;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.ErrorCode;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.ReservationInvalidException;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Arrays;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class Reservation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id", nullable = false)
    private Long reservationId;

    @Column(name = "seat_id", nullable = false)
    private Long seatId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "reservation_status", nullable = false)
    private ReservationStatusType reservationStatus;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime deletedAt;

    public Reservation(String userId, Long seatId, Long paymentId, ReservationStatusType reservationStatus){
        this.userId = userId;
        this.seatId = seatId;
        this.paymentId = paymentId;
        this.reservationStatus = reservationStatus;
    }

    public static Reservation createReservation(String userId, Long seatId, Long paymentId, ReservationStatusType reservationStatus){

        if(userId == null || userId.isBlank()){
            log.debug("userId is null or blank");
            throw new ReservationInvalidException(ErrorCode.USER_ID_INVALID);
        }

        if(seatId == null || seatId < 0){
            log.debug("seatId is null or less than 0");
            throw new ReservationInvalidException(ErrorCode.SEAT_ID_INVALID);
        }

        if (paymentId == null || paymentId < 0){
            log.debug("paymentId is less than 0");
            throw new ReservationInvalidException(ErrorCode.RESERVATION_PAYMENT_ID_INVALID);
        }

        if(reservationStatus == null && !Arrays.stream(ReservationStatusType.values()).toList().contains(reservationStatus)){
            log.debug("reservationStatus is null or invalid");
            throw new ReservationInvalidException(ErrorCode.RESERVATION_STATUS_INVALID);
        }

        return new Reservation(userId, seatId, paymentId, reservationStatus);
    }

    public ReservationDTOResult convertToReservationDTOResult() {
        return ReservationDTOResult.builder()
                .reservationId(reservationId)
                .userId(userId)
                .seatId(seatId)
                .paymentId(paymentId)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    public ReservationDTOParam convertToReservationDTOParam(){
        return ReservationDTOParam.builder()
                .userId(userId)
                .seatId(seatId)
                .paymentId(paymentId)
                .reservationId(reservationId)
                .build();
    }

    public SeatDTOParam convertToSeatDTOParam(){
        return SeatDTOParam.builder()
                .seatId(seatId)
                .build();
    }
}
