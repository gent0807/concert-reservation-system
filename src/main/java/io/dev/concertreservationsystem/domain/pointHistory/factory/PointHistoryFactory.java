package io.dev.concertreservationsystem.domain.pointHistory.factory;

import io.dev.concertreservationsystem.domain.pointHistory.PointHistory;
import io.dev.concertreservationsystem.domain.token.Token;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.ErrorCode;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.PointHistoryInvalidException;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.TokenInvalidException;
import io.dev.concertreservationsystem.interfaces.api.pointHistory.PointTransactionType;

import java.awt.*;
import java.util.Arrays;

public abstract class PointHistoryFactory {
    public final PointHistory orderPointHistory(String userId, PointTransactionType type, Integer amount, Long resultPoint) {

        checkPointHistoryValidationOnBuild(userId, type, amount, resultPoint);

        PointHistory pointHistory = createPointHistory(userId, type, amount, resultPoint);

        return pointHistory;
    }

    public void checkPointHistoryValidationOnBuild(String userId, PointTransactionType type, Integer amount, Long resultPoint){

        checkUserIdValidation(userId);

        checkPointTransactionTypeValidation(type);

        checkAmountValidation(amount);

        checkResultPointValidation(resultPoint);

    }

    private void checkResultPointValidation(Long resultPoint) {
        if(resultPoint == null || resultPoint < 0){
            throw new PointHistoryInvalidException(ErrorCode.POINT_HISTORY_RESULT_POINT_INVALID);
        }
    }

    private void checkAmountValidation(Integer amount) {
        if(amount == null || amount < 0){
            throw new PointHistoryInvalidException(ErrorCode.POINT_HISTORY_AMOUNT_INVALID);
        }
    }

    private void checkPointTransactionTypeValidation(PointTransactionType type) {
        if(type == null || !Arrays.stream(PointTransactionType.values()).toList().contains(type)){
            throw new PointHistoryInvalidException(ErrorCode.POINT_HISTORY_POINT_TRANSACTION_TYPE_INVALID);
        }
    }

    public void checkUserIdValidation(String userId) {
        if(userId == null || userId.isBlank()){
            throw new PointHistoryInvalidException(ErrorCode.USER_ID_INVALID);
        }
    }


    protected abstract PointHistory createPointHistory(String userId, PointTransactionType type, Integer amount, Long resultPoint);
}
