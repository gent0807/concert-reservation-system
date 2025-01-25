package io.dev.concertreservationsystem.infrastructure.concert_detail;

import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetail;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OptimisticConcertDetailRepository extends ConcertDetailJPARepository{
    @Lock(LockModeType.OPTIMISTIC)
    @Query(value = "SELECT cd from ConcertDetail cd WHERE cd.concertDetailId = :concertDetailId")
    Optional<ConcertDetail> findConcertDetailByConcertDetailIdForUpdate(@Param("concertDetailId") Long concertDetailId);
}
