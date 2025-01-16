package io.dev.concertreservationsystem.infrastructure.point_history;

import io.dev.concertreservationsystem.domain.point_history.PointHistory;
import io.dev.concertreservationsystem.domain.point_history.PointHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PointHistoryRepositoryImpl implements PointHistoryRepository {
    private final PointHistoryJPARepository pointHistoryJPARepository;

    @Override
    public void savePointHistory(PointHistory pointHistory){
        pointHistoryJPARepository.save(pointHistory);
    }

    @Override
    public Optional<List<PointHistory>> findPointHistoriesByUserId(String userId){
        return pointHistoryJPARepository.findPointHistoriesByUserId(userId);
    }
}
