package io.dev.concertreservationsystem.domain.pointHistory;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PointHistoryRepository {
    void savePointHistory(PointHistory pointHistory);

    Optional<List<PointHistory>> findPointHistoriesByUserId(String userId);
}
