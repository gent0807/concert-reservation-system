package io.dev.concertreservationsystem.domain.seat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

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
    private Long seatId;

    @Column(name = "concert_detail_id", nullable = false, columnDefinition = "bigint unsigned")
    private Long concertDetailId;

    @Column(name = "seat_number", nullable = false, columnDefinition = "int unsigned")
    private Integer seatNumber;

    @Column(name = "price", nullable = false, columnDefinition = "int unsigned default 50000")
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

    public SeatDTOResult convertToSeatDTOResult() {
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
}
