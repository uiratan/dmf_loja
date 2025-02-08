package com.dmf.loja.validation.annotations.existeid;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ExisteIdValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExisteId {
    String message() default "este {fieldName} não existe no banco de dados";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String fieldName() default "id";
    Class<?> domainClass();
}
