package com.dmf.loja.validation.annotations.documento;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@ConstraintComposition(CompositionType.OR)
@CPF
@CNPJ
//@Constraint(validatedBy = { })
//@ReportAsSingleViolation
public @interface Documento {

	String message() default "{com.deveficiente.casadocodigov2.compartilhado.Documento.message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
