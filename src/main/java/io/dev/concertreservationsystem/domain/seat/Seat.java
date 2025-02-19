package io.dev.concertreservationsystem.domain.seat;

import io.dev.concertreservationsystem.interfaces.common.exception.error.DomainModelParamInvalidException;
import io.dev.concertreservationsystem.interfaces.common.exception.error.ErrorCode;
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
            log.error("concertDetailId is null or less than 0");
            throw new DomainModelParamInvalidException(ErrorCode.CONCERT_DETAIL_ID_INVALID, "SEAT", "createSeat");
        }

        if(seatStatus == null || !Arrays.stream(SeatStatusType.values()).toList().contains(seatStatus)){
            log.error("seatStatus is null or not valid");
            throw new DomainModelParamInvalidException(ErrorCode.SEAT_STATUS_INVALID, "SEAT", "createSeat");
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
            log.error("updatedAt is null or after now");
            throw new DomainModelParamInvalidException(ErrorCode.SEAT_UPDATED_AT_INVALID, "SEAT", "checkSeatUpdatedAtValidation");
        }
    }

    private void checkSeatCreatedAtValidation() {
        if(this.createdAt == null || this.createdAt.isAfter(LocalDateTime.now())){
            log.error("createdAt null or after now");
            throw new DomainModelParamInvalidException(ErrorCode.SEAT_CREATED_AT_INVALID, "SEAT", "checkSeatCreatedAtValidation");
        }
    }

    private void checkSeatExpiredAtValidation() {
        if ((this.seatStatus == SeatStatusType.OCCUPIED) && (this.expiredAt == null || this.expiredAt.isBefore(LocalDateTime.now()))){
            log.error("invalid expiredAt");
            throw new DomainModelParamInvalidException(ErrorCode.SEAT_EXPIRED_AT_INVALID, "SEAT", "checkSeatExpiredAtValidation");
        }
    }

    public void checkSeatPriceValidation() {
        if(this.price == null || this.price < 0){
            log.error("price is null or invalid");
            throw new DomainModelParamInvalidException(ErrorCode.SEAT_PRICE_INVALID, "SEAT", "checkSeatPriceValidation");
        }
    }

    public void checkSeatNumberValidation() {
        if(this.seatNumber == null || this.seatNumber < 1 || this.seatNumber > 50){
            log.error("seatNumber is null or less than 1 or greater than 50");
            throw new DomainModelParamInvalidException(ErrorCode.SEAT_NUMBER_INVALID, "SEAT", "checkSeatNumberValidation");

        }
    }

    public void checkSeatStatusValidation() {
        if(this.seatStatus == null || !Arrays.stream(SeatStatusType.values()).toList().contains(this.seatStatus)){
            log.error("seatStatus null or not valid");
            throw new DomainModelParamInvalidException(ErrorCode.SEAT_STATUS_INVALID, "SEAT", "checkSeatStatusValidation");
        }
    }

    public void checkConcertDetailIdValidation() {
        if(concertDetailId == null || concertDetailId < 0){
            log.error("concertDetailId is null or less than 0");
            throw new DomainModelParamInvalidException(ErrorCode.CONCERT_DETAIL_ID_INVALID, "SEAT", "checkConcertDetailIdValidation");
        }
    }

    public void checkSeatIdValidation() {
        if(this.seatId == null || this.seatId < 0){
            log.error("seatId is null or less than 1");
            throw new DomainModelParamInvalidException(ErrorCode.SEAT_ID_INVALID, "SEAT", "checkSeatIdValidation");
        }
    }

    public void checkReservable() {
        if(this.seatStatus != SeatStatusType.RESERVABLE){
            log.error("this seat is not reservable");
            throw new DomainModelParamInvalidException(ErrorCode.RESERVATION_NOT_RESERVABLE_SEAT, "SEAT", "checkReservable");
        }
    }

    public void updateSeatStatus(SeatStatusType seatStatus) {
        this.setSeatStatus(seatStatus);
        this.setUpdatedAt(LocalDateTime.now());
    }

    public void updateExpiredAt(LocalDateTime expiredTime) {
        this.setExpiredAt(expiredTime);
    }
}
