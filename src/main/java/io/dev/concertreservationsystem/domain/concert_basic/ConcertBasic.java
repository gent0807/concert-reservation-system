package io.dev.concertreservationsystem.domain.concert_basic;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ConcertBasic {

    @Id
    private Long concertBasicId;
}
