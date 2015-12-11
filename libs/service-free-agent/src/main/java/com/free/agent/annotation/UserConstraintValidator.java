package com.free.agent.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by antonPC on 11.07.15.
 */
public class UserConstraintValidator implements ConstraintValidator<User, String> {

    @Override
    public void initialize(User constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.matches("^[a-z0-9_-]{3,15}$");
    }
}
