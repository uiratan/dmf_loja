package com.dmf.loja.validation.advice;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ValidationErrorsResponse {
    private final String message;

    //1
    private final List<FieldErrorsResponse> errors;

    public ValidationErrorsResponse(String message) {
        this.message = message;
        this.errors = List.of();
    }

    //1
    public ValidationErrorsResponse(List<ObjectError> globalErrors, List<FieldError> fieldErrors, MessageService messageService) {
        this.message = "Erro de validação";
        List<FieldErrorsResponse> combinedErrors = new ArrayList<>();

        combinedErrors.addAll(mapGlobalErrors(globalErrors));
        combinedErrors.addAll(mapFieldErrors(fieldErrors, messageService));
        this.errors = Collections.unmodifiableList(combinedErrors);
    }

    public String getMessage() {
        return message;
    }

    public List<FieldErrorsResponse> getErrors() {
        return errors;
    }

    private List<FieldErrorsResponse> mapFieldErrors(List<FieldError> fieldErrors, MessageService messageService) {
        return fieldErrors.stream()
                .map(error -> new FieldErrorsResponse(error.getField(), messageService.getMessage(error)))
                .collect(Collectors.toList());
    }

    private List<FieldErrorsResponse> mapGlobalErrors(List<ObjectError> globalErrors) {
        return globalErrors.stream()
                .map(error -> new FieldErrorsResponse(null, error.getDefaultMessage()))
                .collect(Collectors.toList());
    }
}
