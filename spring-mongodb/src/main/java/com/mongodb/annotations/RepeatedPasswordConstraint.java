package com.mongodb.annotations;


import com.mongodb.validators.RepeatedPasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RepeatedPasswordValidator.class)
@Documented
public @interface RepeatedPasswordConstraint {

  String message() default "Repeated password does not match!";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
