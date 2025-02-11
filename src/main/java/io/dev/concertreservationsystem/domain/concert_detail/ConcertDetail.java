package io.dev.concertreservationsystem.domain.concert_detail;

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
@Table(name = "concert_detail", indexes = @Index(columnList = "concertDetailStatus", unique = true))
@Slf4j
public class ConcertDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "concert_detail_id")
    private Long concertDetailId;

    @Column(name = "concert_basic_id", nullable = false)
    private Long concertBasicId;

    @Version
    private Integer version;

    @Column(name = "concert_detail_status", nullable = false)
    private ConcertDetailStatusType concertDetailStatus;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @CreatedDate
    @Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime deletedAt;

    public ConcertDetail(Long concertBasicId, ConcertDetailStatusType concertDetailStatus){
        this.concertBasicId = concertBasicId;
        this.concertDetailStatus = concertDetailStatus;
    }

    public static ConcertDetail createConcertDetail(Long concertBasicId, ConcertDetailStatusType concertDetailStatus)  {
        if(concertBasicId == null || concertBasicId < 0){
            throw new DomainModelParamInvalidException(ErrorCode.CONCERT_BASIC_ID_INVALID, "CONCERT_DETAIL", "createConcertDetail");
        }

        if(concertDetailStatus == null || !Arrays.stream(ConcertDetailStatusType.values()).toList().contains(concertDetailStatus)){
            throw new DomainModelParamInvalidException(ErrorCode.CONCERT_DETAIL_STATUS_INVALID, "CONCERT_DETAIL", "createConcertDetail");
        }

        return new ConcertDetail(concertBasicId, concertDetailStatus);
    }

    public ConcertDetailDTOResult convertToConcertDetailDTOResult() {

        checkConcertDetailValidation();

        return ConcertDetailDTOResult.builder()
                .concertDetailId(this.concertDetailId)
                .concertBasicId(this.concertBasicId)
                .concertDetailStatus(this.concertDetailStatus)
                .startTime(this.startTime)
                .endTime(this.endTime)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();

    }

    public void checkConcertDetailValidation() {
        checkConcertDetailIdValidation();

        checkConcertDetailStatusValidation();

        checkConcertBasicIdValidation();

        checkStartTimeValidation();

        checkEndTimeValidation();

        checkCreatedAtValidation();

        checkUpdatedAtInvalid();
    }

    public void checkCreatedAtValidation() {
        if(this.createdAt == null || (this.createdAt.getMinute() > LocalDateTime.now().getMinute())){
            throw new DomainModelParamInvalidException(ErrorCode.CONCERT_DETAIL_CREATED_AT_INVALID, "CONCERT_DETAIL", "checkCreatedAtValidation");
        }
    }


    public void checkUpdatedAtInvalid() {
        if(this.updatedAt == null || (this.updatedAt.getMinute() > LocalDateTime.now().getMinute())){
            throw new DomainModelParamInvalidException(ErrorCode.CONCERT_DETAIL_UPDATED_AT_INVALID, "CONCERT_DETAIL", "checkUpdatedAtInvalid");
        }
    }

    public void checkEndTimeValidation(){
        if(this.endTime.isBefore(this.startTime) || this.endTime.isBefore(LocalDateTime.now()) || this.endTime == null){
            throw new DomainModelParamInvalidException(ErrorCode.CONCERT_DETAIL_END_TIME_INVALID, "CONCERT_DETAIL", "checkEndTimeValidation");
        }
    }

    public void checkStartTimeValidation(){
        if(this.startTime.isAfter(this.endTime) || this.startTime.isBefore(LocalDateTime.now()) || startTime == null){
            throw new DomainModelParamInvalidException(ErrorCode.CONCERT_DETAIL_START_TIME_INVALID, "CONCERT_DETAIL", "checkStartTimeValidation");
        }
    }

    public void checkConcertBasicIdValidation(){
        if (this.concertBasicId == null || this.concertBasicId < 0){
            throw new DomainModelParamInvalidException(ErrorCode.CONCERT_BASIC_ID_INVALID, "CONCERT_DETAIL", "checkConcertBasicIdValidation");
        }
    }

    public void checkConcertDetailStatusValidation() {
        if(this.concertDetailStatus == null || !Arrays.stream(ConcertDetailStatusType.values()).toList().contains(this.concertDetailStatus)){
            throw new DomainModelParamInvalidException(ErrorCode.CONCERT_DETAIL_STATUS_INVALID, "CONCERT_DETAIL", "checkConcertDetailStatusValidation");
        }
    }

    public void checkConcertDetailIdValidation() {
        if(this.concertDetailId == null || this.concertDetailId < 0){
            throw new DomainModelParamInvalidException(ErrorCode.CONCERT_DETAIL_ID_INVALID, "CONCERT_DETAIL", "checkConcertDetailIdValidation");
        }
    }


    public void checkReservable() {
        if(this.concertDetailStatus != ConcertDetailStatusType.RESERVABLE){
            throw new DomainModelParamInvalidException(ErrorCode.RESERVATION_NOT_RESERVABLE_CONCERT_DETAIL, "CONCERT_DETAIL", "checkReservable");
        }
    }

}
