package com.dmf.loja.validation.documento;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DocumentoValidatorProprio.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CPFCNPJ {
    String message() default "documento inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}