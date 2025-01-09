package io.dev.concertreservationsystem.interfaces.api.common.validation.annotation;

import io.dev.concertreservationsystem.interfaces.api.common.validation.validator.UserGenderTypeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {UserGenderTypeValidator.class})
@Documented
public @interface NotInvalidUserGenderType {
    String message() default "올바르지 않은 성별 타입입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
