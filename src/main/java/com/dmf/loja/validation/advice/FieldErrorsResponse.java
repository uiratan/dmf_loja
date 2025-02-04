package com.dmf.loja.validation.advice;

public record FieldErrorsResponse(
        String field,
        String message)
{
}
