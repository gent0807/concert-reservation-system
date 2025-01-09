package io.dev.concertreservationsystem.domain.reservation;

import jakarta.persistence.*;
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
}
