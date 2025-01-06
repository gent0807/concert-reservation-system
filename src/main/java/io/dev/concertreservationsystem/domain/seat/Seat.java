package io.dev.concertreservationsystem.domain.seat;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Seat {

    @Id
    private Long seatId;
}
