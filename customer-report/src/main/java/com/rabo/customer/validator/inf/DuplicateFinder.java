package com.rabo.customer.validator.inf;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.rabo.customer.validator.impl.DuplicateFinderImpl;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DuplicateFinderImpl.class)
public @interface DuplicateFinder {
    String message() default "DUPLICATE_REFERENCE";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
