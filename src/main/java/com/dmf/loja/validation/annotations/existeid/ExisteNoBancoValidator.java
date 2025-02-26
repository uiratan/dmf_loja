package com.dmf.loja.validation.annotations.existeid;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;

//1
public class ExisteNoBancoValidator implements ConstraintValidator<ExisteNoBanco, Object> {

    private String fieldName;
    private boolean isCaseSensitive;
    private Class<?> domainClass;


    @Autowired
    private EntityManager entityManager;

    @Override
    public void initialize(ExisteNoBanco constraintAnnotation) {
        this.fieldName = constraintAnnotation.fieldName();
        this.isCaseSensitive = constraintAnnotation.isCaseSensitive();
        this.domainClass = constraintAnnotation.domainClass();

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Aceita valores nulos
        }

        if (value instanceof String && ((String) value).trim().isEmpty()) {
            return true; // Retorna true para strings vazias
        }

        if (value instanceof String && !isCaseSensitive) {
            value = ((String) value).toLowerCase();
        }


        String query = String.format("SELECT 1 FROM %s WHERE %s = :value", domainClass.getSimpleName(), fieldName);

        List<?> result = entityManager.createQuery(query)
                .setParameter("value", value)
                .getResultList();

        return !result.isEmpty();
    }


}

