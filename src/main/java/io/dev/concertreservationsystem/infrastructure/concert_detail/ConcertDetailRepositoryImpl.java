package io.dev.concertreservationsystem.infrastructure.concert_detail;

import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetail;
import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetailDTOResult;
import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetailRepository;
import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetailStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ConcertDetailRepositoryImpl implements ConcertDetailRepository {
    private final ConcertDetailJPARepository concertDetailJPARepository;

    @Override
    public Optional<List<ConcertDetail>> findConcertDetailsByConcertBasicIdAndConcertDetailStatus(Long concertBasicId, ConcertDetailStatus concertDetailStatus){
        return concertDetailJPARepository.findConcertDetailsByConcertBasicIdAndConcertDetailStatus(concertBasicId, concertDetailStatus);
    }
}
