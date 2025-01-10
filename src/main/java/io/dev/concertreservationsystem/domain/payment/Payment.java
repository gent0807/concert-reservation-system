package io.dev.concertreservationsystem.domain.payment;

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
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Column(name = "user_id", nullable = false)
    private PaymentStatusType paymentStatus;

    @Column(name = "total_price", nullable = false, columnDefinition = "INT UNSIGNED DEFAULT 0")
    private Integer totalPrice;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime deletedAt;

    public PaymentDTOResult convertToPaymentDTOResult() {
        return PaymentDTOResult.builder()
                .paymentId(this.paymentId)
                .paymentStatus(this.paymentStatus)
                .totalPrice(this.totalPrice)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }
}
