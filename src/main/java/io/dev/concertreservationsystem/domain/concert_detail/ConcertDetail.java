package io.dev.concertreservationsystem.domain.concert_detail;

import io.dev.concertreservationsystem.interfaces.api.common.exception.error.ConcertDetailInvalidException;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.ErrorCode;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Arrays;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class ConcertDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "concert_detail_id", nullable = false)
    private Long concertDetailId;

    @Column(name = "cocert_basic_id", nullable = false)
    private Long concertBasicId;

    @Column(name = "concert_detail_status", nullable = false, columnDefinition = "ENUM('RESERVABLE')")
    private ConcertDetailStatusType concertDetailStatus;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime deletedAt;

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

    private void checkConcertDetailValidation() {
        checkConcertDetailIdValidation();

        checkConcertDetailStatusValidation();

        checkConcertBasicIdValidation();

        checkStartTimeValidation();

        checkEndTimeValidation();

        checkCreatedAtValidation();

        checkUpdatedAtInvalid();
    }

    public void checkUpdatedAtInvalid() {
        if(this.updatedAt == null || this.updatedAt.isAfter(LocalDateTime.now())){
            log.debug("updatedAt is null or after now");
            throw new ConcertDetailInvalidException(ErrorCode.CONCERT_DETAIL_UPDATED_AT_INVALID);
        }
    }

    public void checkCreatedAtValidation() {
        if(this.createdAt == null || this.createdAt.isAfter(LocalDateTime.now())){
            log.debug("createAt is null or after now");
            throw new ConcertDetailInvalidException(ErrorCode.CONCERT_DETAIL_CREATED_AT_INVALID);
        }
    }

    public void checkEndTimeValidation() {
        if(this.endTime.isBefore(this.startTime) || this.endTime.isBefore(LocalDateTime.now()) || this.endTime == null){
            log.debug("endTime is null or before now");
            throw new ConcertDetailInvalidException(ErrorCode.CONCERT_DETAIL_END_TIME_INVALID);
        }
    }

    public void checkStartTimeValidation() {
        if(this.startTime.isAfter(this.endTime) || this.startTime.isBefore(LocalDateTime.now()) || startTime == null){
            log.debug("startTime is null or after or before now");
            throw new ConcertDetailInvalidException(ErrorCode.CONCERT_DETAIL_START_TIME_INVALID);
        }
    }

    public void checkConcertBasicIdValidation() {
        if (this.concertBasicId == null || this.concertBasicId < 0){
            log.debug("concertBasicId is null or less than 0");
            throw new ConcertDetailInvalidException(ErrorCode.CONCERT_BASIC_ID_INVALID);
        }
    }

    public void checkConcertDetailStatusValidation() {
        if(this.concertDetailStatus == null || !Arrays.stream(ConcertDetailStatusType.values()).toList().contains(this.concertDetailStatus)){
            log.debug("concertDetailStatus is null or not valid");
            throw new ConcertDetailInvalidException(ErrorCode.CONCERT_DETAIL_STATUS_INVALID);
        }
    }

    public void checkConcertDetailIdValidation() {
        if(this.concertDetailId == null || this.concertDetailId < 0){
            log.debug("concertDetailId is null or less than 0");
            throw new ConcertDetailInvalidException(ErrorCode.CONCERT_DETAIL_ID_INVALID);
        }
    }
    
    

}
