package io.dev.concertreservationsystem.infrastructure.pointHistory;

import io.dev.concertreservationsystem.domain.pointHistory.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PointHistoryJPARepository extends JpaRepository<PointHistory, Long> {
    Optional<List<PointHistory>> findPointHistoriesByUserId(String userId);
}
