package io.dev.concertreservationsystem.infrastructure.pointHistory;

import io.dev.concertreservationsystem.domain.pointHistory.PointHistory;
import io.dev.concertreservationsystem.domain.pointHistory.PointHistoryRepository;
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
