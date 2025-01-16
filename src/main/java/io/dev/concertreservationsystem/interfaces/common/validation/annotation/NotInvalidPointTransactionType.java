package io.dev.concertreservationsystem.interfaces.common.validation.annotation;

import io.dev.concertreservationsystem.interfaces.common.validation.validator.PointTransactionTypeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {PointTransactionTypeValidator.class})
@Documented
public @interface NotInvalidPointTransactionType {
    String message() default "올바르지 않은 포인트 충전/차감 타입입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
