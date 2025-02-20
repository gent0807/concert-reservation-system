package io.dev.concertreservationsystem.infrastructure.outbox;

import io.dev.concertreservationsystem.domain.outbox.Outbox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OutboxJPARepository extends JpaRepository<Outbox, Long> {
    Optional<Outbox> findByAggregateTypeAndAggregateIdAndEventTypeAndPayload(String aggregateType, Long aggregateId, String eventType, String payload);
}
