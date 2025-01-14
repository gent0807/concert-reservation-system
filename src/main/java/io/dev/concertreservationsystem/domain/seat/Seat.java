package io.dev.concertreservationsystem.domain.seat;

import io.dev.concertreservationsystem.interfaces.api.common.exception.error.ErrorCode;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.ReservationInvalidException;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.SeatInvalidException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
public class Seat {

    @Id
    @Column(name = "seat_id")
    @Positive
    @Min(0)
    private Long seatId;

    @Column(name = "concert_detail_id", nullable = false, columnDefinition = "bigint unsigned")
    @Positive
    @Min(0)
    private Long concertDetailId;

    @Column(name = "seat_number", nullable = false, columnDefinition = "int unsigned")
    @Size(min = 1, max = 50)
    private Integer seatNumber;

    @Column(name = "price", nullable = false, columnDefinition = "int unsigned default 50000")
    @Positive
    @Min(30000)
    private Integer price;

    @Column(name = "seat_status", nullable = false, columnDefinition = "varchar(20) default 'RESERVABLE'")
    private SeatStatusType seatStatus;

    @Column(name = "expired_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime expiredAt;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime deletedAt;

    public Seat(Long concertDetailId, SeatStatusType seatStatus){
        this.concertDetailId = concertDetailId;
        this.seatStatus = seatStatus;
    }

    public static Seat createSeat(Long concertDetailId, SeatStatusType seatStatus) {

        if(concertDetailId == null || concertDetailId < 0){
            log.debug("concertDetailId is null or less than 0");
            throw new SeatInvalidException(ErrorCode.CONCERT_DETAIL_ID_INVALID);
        }

        if(seatStatus == null || !Arrays.stream(SeatStatusType.values()).toList().contains(seatStatus)){
            log.debug("seatStatus is null or not valid");
            throw new SeatInvalidException(ErrorCode.SEAT_STATUS_INVALID);
        }

        return new Seat(concertDetailId, seatStatus);
    }

    public SeatDTOResult convertToSeatDTOResult() {

        checkSeatValidation();


        return SeatDTOResult.builder()
                .seatId(this.seatId)
                .concertDetailId(this.concertDetailId)
                .seatNumber(this.seatNumber)
                .price(this.price)
                .seatStatus(this.seatStatus)
                .expiredAt(this.expiredAt)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();

    }

    public void checkSeatValidation() {

        checkSeatIdValidation();

        checkConcertDetailIdValidation();

        checkSeatStatusValidation();

        checkSeatNumberValidation();

        checkSeatPriceValidation();

        checkSeatExpiredAtValidation();

        checkSeatCreatedAtValidation();

        checkSeatUpdatedAtValidation();

    }

    private void checkSeatUpdatedAtValidation() {
        if (this.updatedAt == null || this.updatedAt.isAfter(LocalDateTime.now())){
            log.debug("updatedAt is null or after now");
            throw new SeatInvalidException(ErrorCode.SEAT_UPDATED_AT_INVALID);
        }
    }

    private void checkSeatCreatedAtValidation() {
        if(this.createdAt == null || this.createdAt.isAfter(LocalDateTime.now())){
            log.debug("createdAt null or after now");
            throw new SeatInvalidException(ErrorCode.SEAT_CREATED_AT_INVALID);
        }
    }

    private void checkSeatExpiredAtValidation() {
        if ((this.seatStatus == SeatStatusType.OCCUPIED) && (this.expiredAt == null || this.expiredAt.isBefore(LocalDateTime.now()))){
            log.debug("invalid expiredAt");
            throw new SeatInvalidException(ErrorCode.SEAT_EXPIRED_AT_INVALID);
        }
    }

    public void checkSeatPriceValidation() {
        if(this.price == null || this.price < 0){
            log.debug("price is null or invalid");
            throw new SeatInvalidException(ErrorCode.SEAT_PRICE_INVALID);
        }
    }

    public void checkSeatNumberValidation() {
        if(this.seatNumber == null || this.seatNumber < 1 || this.seatNumber > 50){
            log.debug("seatNumber is null or less than 1 or greater than 50");
            throw new SeatInvalidException(ErrorCode.SEAT_NUMBER_INVALID);

        }
    }

    public void checkSeatStatusValidation() {
        if(this.seatStatus == null || !Arrays.stream(SeatStatusType.values()).toList().contains(this.seatStatus)){
            log.debug("seatStatus null or not valid");
            throw new SeatInvalidException(ErrorCode.SEAT_STATUS_INVALID);
        }
    }

    public void checkConcertDetailIdValidation() {
        if(concertDetailId == null || concertDetailId < 0){
            log.debug("concertDetailId is null or less than 0");
            throw new SeatInvalidException(ErrorCode.CONCERT_DETAIL_ID_INVALID);
        }
    }

    public void checkSeatIdValidation() {
        if(this.seatId == null || this.seatId < 0){
            log.debug("seatId is null or less than 1");
            throw new SeatInvalidException(ErrorCode.SEAT_ID_INVALID);
        }
    }

    public void checkReservable() {
        if(this.seatStatus != SeatStatusType.RESERVABLE){
            log.debug("this seat is not reservable");
            throw new ReservationInvalidException(ErrorCode.RESERVATION_NOT_RESERVABLE_SEAT);
        }
    }

    public void updateSeatStatus(SeatStatusType seatStatus) {
        this.setSeatStatus(seatStatus);
    }

    public void updateExpiredAt(LocalDateTime expiredTime) {
        this.setExpiredAt(expiredTime);
    }
}
