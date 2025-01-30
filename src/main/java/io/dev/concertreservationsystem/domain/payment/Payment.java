package io.dev.concertreservationsystem.domain.payment;

import io.dev.concertreservationsystem.common.exception.error.DomainModelParamInvalidException;
import io.dev.concertreservationsystem.common.exception.error.ErrorCode;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Arrays;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "payment")
@Slf4j
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Column(name = "payment_status", nullable = false)
    private PaymentStatusType paymentStatus;

    @Column(name = "total_price", nullable = false, columnDefinition = "INT UNSIGNED DEFAULT 0")
    private Long totalPrice;

    @Version
    private Integer version;

    @CreatedDate
    @Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime deletedAt;

    public Payment(Long totalPrice, PaymentStatusType paymentStatus){
        this.totalPrice = totalPrice;
        this.paymentStatus = paymentStatus;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", paymentStatus=" + paymentStatus +
                ", totalPrice=" + totalPrice +
                ", version=" + version +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + deletedAt +
                '}';
    }

    public static Payment createPayment(Long reduce, PaymentStatusType paymentStatusType) {

        if(reduce == null || reduce < 0){
            throw new DomainModelParamInvalidException(ErrorCode.PAYMENT_TOTAL_PRICE_INVALID, "PAYMENT", "createPayment");
        }

        if(paymentStatusType == null || !Arrays.stream(PaymentStatusType.values()).toList().contains(paymentStatusType)) {
            throw new DomainModelParamInvalidException(ErrorCode.PAYMENT_STATUS_INVALID, "PAYMENT", "createPayment");
        }

        return new Payment(reduce, paymentStatusType);
    }

    public PaymentDTOResult convertToPaymentDTOResult() {
        return PaymentDTOResult.builder()
                .paymentId(this.paymentId)
                .paymentStatus(this.paymentStatus)
                .totalPrice(this.totalPrice)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }

    public void checkPublished() {
        if(this.paymentStatus != PaymentStatusType.PUBLISHED){
            throw new DomainModelParamInvalidException(ErrorCode.PAYMENT_STATUS_NOT_PUBLISH, "PAYMENT", "checkNotPaid");
        }
    }
}
