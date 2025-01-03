package io.dev.concertreservationsystem.infrastructure.concert_basic;

import io.dev.concertreservationsystem.domain.concert_basic.ConcertBasic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertBasicJPARepository extends JpaRepository<ConcertBasic, Long> {
}
