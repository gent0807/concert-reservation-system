package io.dev.concertreservationsystem.domain.pointHistory.factory;

import io.dev.concertreservationsystem.domain.pointHistory.PointHistory;
import io.dev.concertreservationsystem.interfaces.api.pointHistory.PointTransactionType;
import org.springframework.stereotype.Component;

@Component
public class SimplePointHistoryFactory extends PointHistoryFactory {
    @Override
    public PointHistory createPointHistory(String userId, PointTransactionType type, Long amount, Long resultPoint){
        return PointHistory.builder()
                .userId(userId)
                .type(type)
                .amount(amount)
                .resultPoint(resultPoint)
                .build();
    }

}
