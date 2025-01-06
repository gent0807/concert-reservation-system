package io.dev.concertreservationsystem.domain.concert_detail;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ConcertDetail {

    @Id
    private Long concertDetailId;
}
