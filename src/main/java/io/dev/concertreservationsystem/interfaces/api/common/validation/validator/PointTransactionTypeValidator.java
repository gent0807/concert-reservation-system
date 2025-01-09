package io.dev.concertreservationsystem.interfaces.api.common.validation.validator;

import io.dev.concertreservationsystem.domain.user.UserGenderType;
import io.dev.concertreservationsystem.interfaces.api.common.validation.annotation.NotInvalidPointTransactionType;
import io.dev.concertreservationsystem.interfaces.api.common.validation.annotation.NotInvalidUserGenderType;
import io.dev.concertreservationsystem.interfaces.api.pointHistory.PointTransactionType;
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
