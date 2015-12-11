package com.free.agent.annotation;

import com.free.agent.FreeAgentAPI;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by antonPC on 02.12.15.
 */
@Documented
@Constraint(validatedBy = EmailConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Email {
    String message() default FreeAgentAPI.EMAIL_INVALID;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
