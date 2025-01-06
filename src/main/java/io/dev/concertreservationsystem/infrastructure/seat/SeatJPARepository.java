package io.dev.concertreservationsystem.infrastructure.seat;

import io.dev.concertreservationsystem.domain.seat.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatJPARepository extends JpaRepository<Seat, Long> {
}
