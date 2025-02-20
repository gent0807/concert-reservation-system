package io.dev.concertreservationsystem.domain.outbox;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
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
    private Long id;

    // 이벤트가 발생한 도메인(예: Order)
    private String aggregateType;

    // 도메인 엔티티의 식별자
    private Long aggregateId;

    // 이벤트 유형 (예: OrderCreated, OrderUpdated 등)
    private String eventType;

    // 이벤트 데이터(예: JSON 형식)
    @Column(columnDefinition = "TEXT")
    private String payload;

    // 메시지 발행 여부
    private OutboxStatusType status;

    @CreatedDate
    // 이벤트 생성 시간
    private LocalDateTime createdAt;

}
