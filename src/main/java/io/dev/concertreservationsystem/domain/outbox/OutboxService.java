package io.dev.concertreservationsystem.domain.outbox;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OutboxService {

    private final OutboxRepository outboxRepository;

    public void saveOutbox(OutboxDTOParam outbox) {

        outboxRepository.save(Outbox.builder()
                                .aggregateType(outbox.aggregateType())
                                .aggregateId(outbox.aggregateId())
                                .eventType(outbox.eventType())
                                .payload(outbox.payload())
                                .build());
    }
}
