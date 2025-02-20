package io.dev.concertreservationsystem.domain.outbox;

import java.util.List;
import java.util.Optional;

public interface OutboxRepository {
    void save(Outbox outbox);

    Optional<Outbox> findOutboxByOutbox(Outbox outbox);

    List<Outbox> findOutboxToRepublish(OutboxStatusType outboxStatusType, int interval);

    void removeExpiredOutbox(OutboxStatusType outboxStatusType, int interval);
}
