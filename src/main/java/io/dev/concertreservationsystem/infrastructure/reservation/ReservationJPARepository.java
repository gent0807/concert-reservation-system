package io.dev.concertreservationsystem.infrastructure.reservation;

import io.dev.concertreservationsystem.domain.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationJPARepository extends JpaRepository<Reservation,Long> {
}
