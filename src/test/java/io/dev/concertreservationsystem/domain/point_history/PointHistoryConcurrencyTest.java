package io.dev.concertreservationsystem.domain.point_history;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PointHistoryConcurrencyTest {
    @Autowired
    private PointHistoryService pointHistoryService;

}
