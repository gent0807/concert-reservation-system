package io.dev.concertreservationsystem.domain.concert_detail;

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
public class ConcertDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "concert_detail_id", nullable = false)
    private Long concertDetailId;

    @Column(name = "cocert_basic_id", nullable = false)
    private Long concertBasicId;

    @Column(name = "concert_detail_status", nullable = false, columnDefinition = "ENUM('RESERVABLE')")
    private ConcertDetailStatus concertDetailStatus;

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

}
