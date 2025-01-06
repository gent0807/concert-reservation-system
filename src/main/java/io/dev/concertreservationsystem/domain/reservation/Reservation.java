package io.dev.concertreservationsystem.domain.reservation;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Reservation {
    
    @Id
    private Long reservationId;
}
