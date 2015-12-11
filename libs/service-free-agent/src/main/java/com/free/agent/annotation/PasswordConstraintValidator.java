package com.free.agent.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by antonPC on 11.07.15.
 */
public class PasswordConstraintValidator implements ConstraintValidator<Password, String> {

    @Override
    public void initialize(Password constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})");
    }
}
