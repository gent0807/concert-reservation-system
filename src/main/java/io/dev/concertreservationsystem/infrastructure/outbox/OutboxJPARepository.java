package io.dev.concertreservationsystem.infrastructure.outbox;

import io.dev.concertreservationsystem.domain.outbox.Outbox;
import io.dev.concertreservationsystem.domain.outbox.OutboxStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OutboxJPARepository extends JpaRepository<Outbox, Long> {
    Optional<Outbox> findByAggregateTypeAndAggregateIdAndEventTypeAndPayload(String aggregateType, Long aggregateId, String eventType, String payload);

    @Query(value = "SELECT * FROM outbox WHERE status = :outboxStatusType AND updatedAt < DATE_SUB(NOW(), INTERVAL :interval SECOND)", nativeQuery = true)
    List<Outbox> findOutboxToRepublish(@Param("outboxStatusType") OutboxStatusType outboxStatusType, @Param("interval") int interval);

    @Query(value = "DELETE FROM outbox WHERE status = :outboxStatusType AND createdAt < DATE_SUB(NOW(), INTERVAL :interval DAY)", nativeQuery = true)
    void removeExpiredOutbox(OutboxStatusType outboxStatusType, int interval);
}
