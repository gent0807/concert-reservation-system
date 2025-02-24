package io.dev.concertreservationsystem.domain.outbox;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OutboxService {

    private final OutboxRepository outboxRepository;

    public List<Outbox> findOutboxToRepublish(OutboxStatusType outboxStatusType, int interval) {
        return outboxRepository.findOutboxToRepublish(outboxStatusType, interval);
    }

    public void saveOutbox(OutboxDTOParam outbox) {

        outboxRepository.save(Outbox.builder()
                                .aggregateType(outbox.aggregateType())
                                .aggregateId(outbox.aggregateId())
                                .eventType(outbox.eventType())
                                .status(outbox.status())
                                .payload(outbox.payload())
                                .build());
    }



    public void removeExpiredOutbox(OutboxStatusType outboxStatusType, int interval) {
        outboxRepository.removeExpiredOutbox(outboxStatusType, interval);
    }
}
