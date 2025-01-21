package io.dev.concertreservationsystem.domain.concert_detail;

import java.util.List;
import java.util.Optional;

public interface ConcertDetailRepository {
    Optional<List<ConcertDetail>> findConcertDetailsByConcertBasicIdAndConcertDetailStatus(Long concertBasicId, ConcertDetailStatusType concertDetailStatus);

    Optional<ConcertDetail> findConcertDetailByConcertDetailIdWithLock(Long concertDetailId);

    ConcertDetail save(ConcertDetail concertDetail);
}
