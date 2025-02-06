package com.dmf.loja.validation.advice;

import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Stream;

public class ValidationErrorsResponse {
    private final String message;
    private final List<FieldErrorsResponse> errors;

    private ValidationErrorsResponse(String message, List<FieldErrorsResponse> errors) {
        this.message = message;
        this.errors = List.copyOf(errors);
    }

    public static ValidationErrorsResponse fromMessage(String message) {
        return new ValidationErrorsResponse(message, List.of());
    }

    public static ValidationErrorsResponse fromBindingResult(BindingResult bindingResult, MessageService messageService) {
        List<FieldErrorsResponse> combinedErrors = Stream.concat(
                bindingResult.getGlobalErrors().stream()
                        .map(error -> new FieldErrorsResponse(null, error.getDefaultMessage())),
                bindingResult.getFieldErrors().stream()
                        .map(error -> new FieldErrorsResponse(error.getField(), messageService.getMessage(error)))
        ).toList();

        return new ValidationErrorsResponse("Erro de validação", combinedErrors);
    }

    public String getMessage() {
        return message;
    }

    public List<FieldErrorsResponse> getErrors() {
        return errors;
    }
}
