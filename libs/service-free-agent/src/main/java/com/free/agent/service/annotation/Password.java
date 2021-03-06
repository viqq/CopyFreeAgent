package com.free.agent.service.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by antonPC on 11.07.15.
 */
@Documented
@Constraint(validatedBy = PasswordConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    String message() default "{Password}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
