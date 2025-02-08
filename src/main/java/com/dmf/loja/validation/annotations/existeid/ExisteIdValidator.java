package com.dmf.loja.validation.annotations.existeid;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

//1
public class ExisteIdValidator implements ConstraintValidator<ExisteId, Object> {

    private String fieldName;
    private boolean isCaseSensitive;
    private Class<?> domainClass;


    @Autowired
    private EntityManager entityManager;

    @Override
    public void initialize(ExisteId constraintAnnotation) {
        this.fieldName = constraintAnnotation.fieldName();
        this.isCaseSensitive = constraintAnnotation.isCaseSensitive();
        this.domainClass = constraintAnnotation.domainClass();

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        if (!isCaseSensitive) {
            value = value.toString().toLowerCase();
        }
        String query = String.format("SELECT 1 FROM %s WHERE %s = :value", domainClass.getSimpleName(), fieldName);

        List<?> result = entityManager.createQuery(query)
                .setParameter("value", value)
                .getResultList();

        return !result.isEmpty();
    }


}

