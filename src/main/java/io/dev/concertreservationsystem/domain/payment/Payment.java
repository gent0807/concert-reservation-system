package io.dev.concertreservationsystem.domain.payment;

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

    public Payment(Integer totalPrice, PaymentStatusType paymentStatus){
        this.totalPrice = totalPrice;
        this.paymentStatus = paymentStatus;
    }

    public static Payment createPayment(Integer reduce, PaymentStatusType paymentStatusType) {

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
}
