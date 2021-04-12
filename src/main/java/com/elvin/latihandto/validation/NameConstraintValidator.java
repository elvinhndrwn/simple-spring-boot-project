package com.elvin.latihandto.validation;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Documented;

@Component
public class NameConstraintValidator implements ConstraintValidator<NamePrefix, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // TODO Auto-generated method stub

        return value.startsWith("PRD");
    }
}
