package io.dev.concertreservationsystem.infrastructure.concert_detail;

import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetail;
import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetailStatusType;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConcertDetailJPARepository extends JpaRepository<ConcertDetail, Long> {

    Optional<List<ConcertDetail>> findConcertDetailsByConcertBasicIdAndConcertDetailStatus(@Param("concertBasicId") Long concertBasicId, @Param("concertDetailStatusType") ConcertDetailStatusType concertDetailStatus);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT cd from ConcertDetail cd WHERE cd.concertDetailId = :concertDetailId")
    Optional<ConcertDetail> findConcertDetailByConcertDetailIdForUpdate(@Param("concertDetailId") Long concertDetailId);
}
