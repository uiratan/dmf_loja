package com.dmf.loja.validation.advice;

//0
public record FieldErrorsResponse(
        String field,
        String message)
{
}
