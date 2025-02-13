package io.dev.concertreservationsystem.domain.concert_detail;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ConcertDetailRepository {
    Optional<List<ConcertDetail>> findConcertDetailsByConcertBasicIdAndStartTimeAndEndTimeAndConcertDetailStatus(Long concertBasicId, LocalDateTime startTime, LocalDateTime endTime, ConcertDetailStatusType concertDetailStatus);

    Optional<ConcertDetail> findConcertDetailByConcertDetailIdWithLock(Long concertDetailId);

    ConcertDetail save(ConcertDetail concertDetail);
}
