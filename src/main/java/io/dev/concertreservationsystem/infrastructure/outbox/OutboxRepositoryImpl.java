package io.dev.concertreservationsystem.infrastructure.outbox;

import io.dev.concertreservationsystem.common.config.kafka.KafkaTopicKey;
import io.dev.concertreservationsystem.domain.common.key.DomainType;
import io.dev.concertreservationsystem.domain.outbox.Outbox;
import io.dev.concertreservationsystem.domain.outbox.OutboxDTOParam;
import io.dev.concertreservationsystem.domain.outbox.OutboxRepository;
import io.dev.concertreservationsystem.domain.reservation.ReservationSuccessEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OutboxRepositoryImpl implements OutboxRepository {
    private final OutboxJPARepository outboxJPARepository;

    @Override
    public void save(Outbox outbox) {
        outboxJPARepository.save(outbox);
    }

    @Override
    public Optional<Outbox> findOutboxByOutbox(Outbox outbox) {
       return outboxJPARepository.findByAggregateTypeAndAggregateIdAndEventTypeAndPayload(outbox.getAggregateType(), outbox.getAggregateId(), outbox.getEventType(), outbox.getPayload());
    }
}
