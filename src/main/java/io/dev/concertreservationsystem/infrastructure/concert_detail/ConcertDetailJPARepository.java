package io.dev.concertreservationsystem.infrastructure.concert_detail;

import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertDetailJPARepository extends JpaRepository<ConcertDetail, Long> {
}
