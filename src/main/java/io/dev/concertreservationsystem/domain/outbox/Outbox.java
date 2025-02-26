package io.dev.concertreservationsystem.domain.outbox;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "outbox")
public class Outbox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // 이벤트가 발생한 도메인(예: Order)
    @Column(name = "aggregateType", nullable = false)
    private String aggregateType;

    // 도메인 엔티티의 식별자
    @Column(name = "aggregateId", nullable = false)
    private Long aggregateId;

    // 이벤트 유형 (예: OrderCreated, OrderUpdated 등)
    @Column(name = "eventType", nullable = false)
    private String eventType;

    // 이벤트 데이터(예: JSON 형식)
    @Column(name="payload", nullable = false,  columnDefinition = "TEXT")
    private String payload;

    // 메시지 발행 여부
    @Column(name="status", nullable = false)
    private OutboxStatusType status;

    @CreatedDate
    @Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    // 이벤트 생성 시간
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    // 이벤트 최종 수정 시간
    private LocalDateTime updatedAt;

}
