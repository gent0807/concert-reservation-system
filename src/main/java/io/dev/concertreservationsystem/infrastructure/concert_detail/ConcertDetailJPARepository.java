package io.dev.concertreservationsystem.infrastructure.concert_detail;

import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetail;
import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetailDTOResult;
import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetailStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConcertDetailJPARepository extends JpaRepository<ConcertDetail, Long> {
    Optional<List<ConcertDetail>> findConcertDetailsByConcertBasicIdAndConcertDetailStatus(Long concertBasicId, ConcertDetailStatus concertDetailStatus);
}
