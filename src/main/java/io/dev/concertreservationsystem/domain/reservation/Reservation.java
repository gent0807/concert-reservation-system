package io.dev.concertreservationsystem.domain.reservation;

import io.dev.concertreservationsystem.domain.seat.SeatDTOParam;
import io.dev.concertreservationsystem.interfaces.common.exception.error.DomainModelParamInvalidException;
import io.dev.concertreservationsystem.interfaces.common.exception.error.ErrorCode;
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

    @Version
    private Integer version;

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
            log.error("userId is null or blank");
            throw new DomainModelParamInvalidException(ErrorCode.USER_ID_INVALID, "RESERVATION", "createReservation");
        }

        if(seatId == null || seatId < 0){
            log.error("seatId is null or less than 0");
            throw new DomainModelParamInvalidException(ErrorCode.SEAT_ID_INVALID, "RESERVATION", "createReservation");
        }

        if (paymentId == null || paymentId < 0){
            log.error("paymentId is less than 0");
            throw new DomainModelParamInvalidException(ErrorCode.RESERVATION_PAYMENT_ID_INVALID, "RESERVATION", "createReservation");
        }

        if(reservationStatus == null && !Arrays.stream(ReservationStatusType.values()).toList().contains(reservationStatus)){
            log.error("reservationStatus is null or invalid");
            throw new DomainModelParamInvalidException(ErrorCode.RESERVATION_STATUS_INVALID, "RESERVATION", "createReservation");
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

    public void checkTemp() {
        if(this.reservationStatus != ReservationStatusType.TEMP){
            throw new DomainModelParamInvalidException(ErrorCode.RESERVATION_STATUS_NOT_TEMP, "RESERVATION", "checkTemp");
        }
    }
}
