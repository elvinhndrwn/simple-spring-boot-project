package com.elvin.latihandto.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NameConstraintValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)

public @interface NamePrefix {
    String message() default "Name must be prefixed with PRD";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
