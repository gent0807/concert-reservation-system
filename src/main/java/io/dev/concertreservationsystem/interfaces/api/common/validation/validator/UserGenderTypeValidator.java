package io.dev.concertreservationsystem.interfaces.api.common.validation.validator;


import io.dev.concertreservationsystem.interfaces.api.common.validation.annotation.NotInvalidUserGenderType;
import io.dev.concertreservationsystem.domain.user.UserGenderType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class UserGenderTypeValidator implements ConstraintValidator<NotInvalidUserGenderType, UserGenderType> {
    @Override
    public boolean isValid(UserGenderType value, ConstraintValidatorContext constraintValidatorContext) {

        if(Arrays.stream(UserGenderType.values()).toList().contains(value)){
            return true;
        }

        return false;
    }
}
