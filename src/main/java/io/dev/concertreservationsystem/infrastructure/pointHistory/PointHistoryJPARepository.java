package io.dev.concertreservationsystem.infrastructure.pointHistory;

import io.dev.concertreservationsystem.domain.pointHistory.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointHistoryJPARepository extends JpaRepository<PointHistory, Long> {
}
