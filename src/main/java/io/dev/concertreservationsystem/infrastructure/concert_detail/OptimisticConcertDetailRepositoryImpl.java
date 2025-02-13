package io.dev.concertreservationsystem.infrastructure.concert_detail;

import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetail;
import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetailRepository;
import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetailStatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Profile(value = "optimistic-lock")
public class OptimisticConcertDetailRepositoryImpl implements ConcertDetailRepository {
    private final ConcertDetailJPARepository optimisticConcertDetailRepository;

    @Override
    public Optional<List<ConcertDetail>> findConcertDetailsByConcertBasicIdAndStartTimeAndEndTimeAndConcertDetailStatus(Long concertBasicId, LocalDateTime startTime, LocalDateTime endTime, ConcertDetailStatusType concertDetailStatus){
        return optimisticConcertDetailRepository.findConcertDetailsByConcertBasicIdAndConcertDetailStatus(concertBasicId, concertDetailStatus);
    }

    @Override
    public Optional<ConcertDetail> findConcertDetailByConcertDetailIdWithLock(Long concertDetailId){
        return optimisticConcertDetailRepository.findConcertDetailByConcertDetailIdForUpdateWithOptimisticLock(concertDetailId);
    }

    @Override
    public ConcertDetail save(ConcertDetail concertDetail){
        return optimisticConcertDetailRepository.save(concertDetail);
    }

}
