package com.dmf.loja.validation;

public record FieldErrorsResponse(
        String field,
        String message)
{
}
