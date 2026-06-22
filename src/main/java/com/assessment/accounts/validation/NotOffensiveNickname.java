package com.assessment.accounts.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = NotOffensiveNicknameValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface NotOffensiveNickname {

    String message() default "Account nick name contains an offensive word";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
