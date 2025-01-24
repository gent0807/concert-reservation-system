package io.dev.concertreservationsystem.domain.point_history;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("pessimistic-lock")
public class PessimisticLockPointHistoryConcurrencyTest extends PointHistoryConcurrencyTest {
}
