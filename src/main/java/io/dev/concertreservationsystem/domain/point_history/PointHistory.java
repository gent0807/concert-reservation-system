package io.dev.concertreservationsystem.domain.point_history;

import io.dev.concertreservationsystem.interfaces.common.exception.error.DomainModelParamInvalidException;
import io.dev.concertreservationsystem.interfaces.common.exception.error.ErrorCode;
import io.dev.concertreservationsystem.interfaces.api.point_history.PointTransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
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
@Slf4j
public class PointHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_history_id", nullable = false)
    @Positive
    @Min(0)
    private Long pointHistoryId;

    @Column(name = "payment_id")
    @Positive
    @Min(0)
    private Long paymentId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "type", nullable = false)
    private PointTransactionType type;

    @Column(name = "amount", nullable = false, columnDefinition = "BIGINT UNSIGNED DEFAULT 0")
    @Positive
    @Min(0)
    private Long amount;

    @Column(name = "result_point", nullable = false, columnDefinition = "BIGINT UNSIGNED DEFAULT 0")
    @Positive
    @Min(0)
    private Long resultPoint;

    @CreatedDate
    @Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime deletedAt;


    public PointHistory(String userId, PointTransactionType type, Long amount, Long resultPoint){
        this.userId = userId;
        this.type = type;
        this.amount = amount;
        this.resultPoint = resultPoint;
    }

    public static PointHistory createPointHistory(String userId, PointTransactionType type, Long amount, Long resultPoint){

        if(userId == null || userId.isBlank()){
            log.error("userId is null or blank");
            throw new DomainModelParamInvalidException(ErrorCode.USER_ID_INVALID, "POINT_HISTORY", "createPointHistory");
        }

        if(type == null && !Arrays.stream(PointTransactionType.values()).toList().contains(type)){
            log.error("type is null or invalid");
            throw new DomainModelParamInvalidException(ErrorCode.POINT_HISTORY_POINT_TRANSACTION_TYPE_INVALID, "POINT_HISTORY", "createPointHistory");
        }

        if(amount == null || amount < 0){
            log.error("amount is null or less than 0");
            throw new DomainModelParamInvalidException(ErrorCode.POINT_HISTORY_AMOUNT_INVALID, "POINT_HISTORY", "createPointHistory");
        }

        if(resultPoint == null || resultPoint < 0){
            log.error("resultPoint is null or less than 0");
            throw new DomainModelParamInvalidException(ErrorCode.POINT_HISTORY_RESULT_POINT_INVALID, "POINT_HISTORY", "createPointHistory");
        }

        return new PointHistory(userId, type, amount, resultPoint);
    }

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

    public void checkPointHistoryValidation() {
        checkPointHistoryIdValidation();

        checkPointHistoryPointTransactionTypeValidation();

        checkPointHistoryAmountValidation();

        checkPointHistoryResultPointValidation();

        checkPointHistoryCreatedAtValidation();

        checkPointHistoryUpdatedAtValidation();
    }


    public void checkPointHistoryIdValidation() {
        if(this.pointHistoryId == null || this.pointHistoryId < 0){
            throw new DomainModelParamInvalidException(ErrorCode.POINT_HISTORY_ID_INVALID, "POINT_HISTORY", "checkPointHistoryIdValidation");
        }
    }


    public void checkPointHistoryPointTransactionTypeValidation() {
        if (this.type == null){
            throw new DomainModelParamInvalidException(ErrorCode.POINT_HISTORY_POINT_TRANSACTION_TYPE_INVALID, "POINT_HISTORY", "checkPointHistoryPointTransactionTypeValidation");
        }
    }

    public void checkPointHistoryAmountValidation() {
        if (this.amount == null || this.amount < 0){
            throw new DomainModelParamInvalidException(ErrorCode.POINT_HISTORY_AMOUNT_INVALID, "POINT_HISTORY", "checkPoinyHistoryAmountValidation");
        }
    }

    public void checkPointHistoryResultPointValidation() {
        if (this.resultPoint == null || this.resultPoint < 0){
            throw new DomainModelParamInvalidException(ErrorCode.POINT_HISTORY_RESULT_POINT_INVALID, "POINT_HISTORY", "checkPointHistoryResultPointValidation");
        }
    }

    public void checkPointHistoryCreatedAtValidation() {

        if(this.createdAt == null){
            throw new DomainModelParamInvalidException(ErrorCode.POINT_HISTORY_CREATED_AT_NULL, "POINT_HISTORY", "checkPointHistoryCreatedAtValidation");
        }

        if (this.createdAt.getMinute() > LocalDateTime.now().getMinute()){

            throw new DomainModelParamInvalidException(ErrorCode.POINT_HISTORY_CREATED_AT_AFTER_NOW,"POINT_HISTORY", "checkPointHistoryCreatedAtValidation");
        }
    }


    public void checkPointHistoryUpdatedAtValidation() {

        if(this.updatedAt == null){
            throw new DomainModelParamInvalidException(ErrorCode.POINT_HISTORY_UPDATED_AT_NULL, "POINT_HISTORY", "checkPointHistoryCreatedAtValidation");
        }

        if (this.updatedAt.getMinute() > LocalDateTime.now().getMinute()){
            throw new DomainModelParamInvalidException(ErrorCode.POINT_HISTORY_UPDATED_AT_AFTER_NOW, "POINT_HISTORY", "checkPointHistoryUpdatedAtValidation");
        }
    }
}
