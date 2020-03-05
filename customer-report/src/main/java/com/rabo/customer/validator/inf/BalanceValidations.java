package com.rabo.customer.validator.inf;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.rabo.customer.validator.impl.BalanceValidationsImpl;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BalanceValidationsImpl.class)
public @interface BalanceValidations {
    String message() default "INCORRECT_END_BALANCE";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
