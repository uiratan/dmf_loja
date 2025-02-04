package com.dmf.loja.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.function.Function;

public class CampoUnicoValidator<T> implements Validator {

    private final Function<T, String> campoExtractor;
    private final Function<String, Boolean> existeNoBanco;
    private final String campoNome;
    private final Class<T> targetClass;

    public CampoUnicoValidator(Function<T, String> campoExtractor,
                               Function<String, Boolean> existeNoBanco,
                               String campoNome,
                               Class<T> targetClass) {
        this.campoExtractor = campoExtractor;
        this.existeNoBanco = existeNoBanco;
        this.campoNome = campoNome;
        this.targetClass = targetClass;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return targetClass.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        @SuppressWarnings("unchecked")
        T request = (T) target;
        String valor = campoExtractor.apply(request);

        if (existeNoBanco.apply(valor)) {
            errors.rejectValue(campoNome, null, "Já existe um registro com este " + campoNome + ": " + valor);
        }
    }
}
