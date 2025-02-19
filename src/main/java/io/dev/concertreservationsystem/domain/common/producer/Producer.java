package io.dev.concertreservationsystem.domain.common.producer;

public interface Producer {

    public void produce(String topic, String key, String payload);
}
