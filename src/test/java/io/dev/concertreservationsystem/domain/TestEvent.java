package io.dev.concertreservationsystem.domain;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestEvent {
    private long id;

    public static TestEvent idOf(long l) {
        return new TestEvent(l);
    }
}
