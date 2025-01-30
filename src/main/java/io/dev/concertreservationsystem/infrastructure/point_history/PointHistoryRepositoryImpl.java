package io.dev.concertreservationsystem.infrastructure.point_history;

import io.dev.concertreservationsystem.domain.point_history.PointHistory;
import io.dev.concertreservationsystem.domain.point_history.PointHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Profile(value = "default")
public class PointHistoryRepositoryImpl implements PointHistoryRepository {
    private final PointHistoryJPARepository pointHistoryJPARepository;

    @Override
    public void save(PointHistory pointHistory){
        pointHistoryJPARepository.save(pointHistory);
    }

    @Override
    public Optional<List<PointHistory>> findPointHistoriesByUserId(String userId){
        return pointHistoryJPARepository.findPointHistoriesByUserId(userId);
    }
}
