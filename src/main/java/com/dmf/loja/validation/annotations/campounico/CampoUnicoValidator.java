package com.dmf.loja.validation.annotations.campounico;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

//1
public class CampoUnicoValidator implements ConstraintValidator<CampoUnico, String> {

    private String fieldName;
    private Class<?> domainClass;

    @Autowired
    private EntityManager entityManager;

    @Override
    public void initialize(CampoUnico constraintAnnotation) {
        this.fieldName = constraintAnnotation.fieldName();
        this.domainClass = constraintAnnotation.domainClass();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value.isEmpty()) {
            return true;
        }

        String query = String.format("SELECT 1 FROM %s WHERE %s = :value", domainClass.getSimpleName(), fieldName);

        List<?> result = entityManager.createQuery(query)
                .setParameter("value", value)
                .getResultList();

        return result.isEmpty();
    }


}

