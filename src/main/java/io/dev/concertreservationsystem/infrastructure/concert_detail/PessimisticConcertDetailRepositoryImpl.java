package io.dev.concertreservationsystem.infrastructure.concert_detail;

import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetail;
import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetailRepository;
import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetailStatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Profile(value = "pessimistic-lock")
public class PessimisticConcertDetailRepositoryImpl implements ConcertDetailRepository {
    private final ConcertDetailJPARepository pessimisticConcertDetailRepository;

    @Override
    public Optional<List<ConcertDetail>> findConcertDetailsByConcertBasicIdAndConcertDetailStatus(Long concertBasicId, ConcertDetailStatusType concertDetailStatus){
        return pessimisticConcertDetailRepository.findConcertDetailsByConcertBasicIdAndConcertDetailStatus(concertBasicId, concertDetailStatus);
    }

    @Override
    public Optional<ConcertDetail> findConcertDetailByConcertDetailIdWithLock(Long concertDetailId){
        return pessimisticConcertDetailRepository.findConcertDetailByConcertDetailIdForUpdateWithPessimisticLock(concertDetailId);
    }

    @Override
    public ConcertDetail save(ConcertDetail concertDetail){
        return pessimisticConcertDetailRepository.save(concertDetail);
    }
}
