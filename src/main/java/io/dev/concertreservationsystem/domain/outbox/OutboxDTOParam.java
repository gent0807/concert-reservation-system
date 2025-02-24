package io.dev.concertreservationsystem.domain.outbox;

import lombok.*;

@Builder
public record OutboxDTOParam (
        Long id,
        String aggregateType,
        Long aggregateId,
        String eventType,
        OutboxStatusType status,
        String payload
){
}
