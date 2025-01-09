package io.dev.concertreservationsystem.domain.pointHistory;

import io.dev.concertreservationsystem.interfaces.api.common.exception.error.ErrorCode;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.PointHistoryInvalidException;
import io.dev.concertreservationsystem.interfaces.api.pointHistory.PointTransactionType;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class PointHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_history_id", nullable = false)
    private Long pointHistoryId;

    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "type", nullable = false)
    private PointTransactionType type;

    @Column(name = "amount", nullable = false, columnDefinition = "BIGINT UNSIGNED DEFAULT 0")
    private Long amount;

    @Column(name = "result_point", nullable = false, columnDefinition = "BIGINT UNSIGNED DEFAULT 0")
    private Long resultPoint;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime deletedAt;

    public PointHistoryDTOResult convertToPointHistoryDTOResult() {

        checkPointHistoryValidation();

        return PointHistoryDTOResult.builder()
                .pointHistoryId(this.pointHistoryId)
                .paymentId(this.paymentId)
                .userId(this.userId)
                .type(this.type)
                .amount(this.amount)
                .resultPoint(this.resultPoint)
                .created_at(this.createdAt)
                .updated_at(this.updatedAt)
                .build();

    }

    private void checkPointHistoryValidation() {
        checkPointHistoryIdValidation();

        checkPointHistoryPointTransactionTypeValidation();

        checkPoinyHistoryAmountValidation();

        checkPointHistoryResultPointValidation();

        checkPointHistoryCreatedAtValidation();

        checkPointHistoryUpdatedAtValidation();
    }


    public void checkPointHistoryIdValidation() {
        if(this.pointHistoryId == null || this.pointHistoryId < 0){
            log.debug("pointHistoryId is null or less than 0");
            throw new PointHistoryInvalidException(ErrorCode.POINT_HISTORY_ID_INVALID);
        }
    }


    public void checkPointHistoryPointTransactionTypeValidation() {
        if (this.type == null){
            log.debug("type is null");
            throw new PointHistoryInvalidException(ErrorCode.POINT_HISTORY_POINT_TRANSACTION_TYPE_INVALID);
        }
    }

    public void checkPoinyHistoryAmountValidation() {
        if (this.amount == null || this.amount < 0){
            log.debug("amount is null or less than 0");
            throw new PointHistoryInvalidException(ErrorCode.POINT_HISTORY_AMOUNT_INVALID);
        }
    }

    public void checkPointHistoryResultPointValidation() {
        if (this.resultPoint == null || this.resultPoint < 0){
            log.debug("resultPoint is null or less than 0");
            throw new PointHistoryInvalidException(ErrorCode.POINT_HISTORY_RESULT_POINT_INVALID);
        }
    }

    public void checkPointHistoryCreatedAtValidation() {
        if (this.createdAt == null || this.createdAt.isAfter(LocalDateTime.now())){
            log.debug("createdAt is null");
            throw new PointHistoryInvalidException(ErrorCode.POINT_HISTORY_CREATED_AT_INVALID);
        }
    }


    public void checkPointHistoryUpdatedAtValidation() {
        if (this.updatedAt == null || this.updatedAt.isAfter(LocalDateTime.now())){
            log.debug("updatedAt is null");
            throw new PointHistoryInvalidException(ErrorCode.POINT_HISTORY_UPDATED_AT_INVALID);
        }
    }
}
