package io.dev.concertreservationsystem.domain.outbox;

import io.dev.concertreservationsystem.domain.reservation.ReservationSuccessEvent;

import java.util.Optional;

public interface OutboxRepository {
    void save(Outbox outbox);

    Optional<Outbox> findOutboxByOutbox(Outbox outbox);
}
