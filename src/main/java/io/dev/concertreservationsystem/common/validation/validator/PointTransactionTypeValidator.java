package io.dev.concertreservationsystem.common.validation.validator;

import io.dev.concertreservationsystem.common.validation.annotation.NotInvalidPointTransactionType;
import io.dev.concertreservationsystem.interfaces.api.point_history.PointTransactionType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class PointTransactionTypeValidator implements ConstraintValidator<NotInvalidPointTransactionType, PointTransactionType> {


    @Override
    public boolean isValid(PointTransactionType type, ConstraintValidatorContext constraintValidatorContext) {

        if(Arrays.stream(PointTransactionType.values()).toList().contains(type)){
                return true;
        }

        return false;
    }
}
