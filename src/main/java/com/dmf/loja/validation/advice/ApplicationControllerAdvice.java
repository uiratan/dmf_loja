package com.dmf.loja.validation.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationControllerAdvice.class);

    private final MessageService messageService;

    public ApplicationControllerAdvice(MessageService messageService) {
        this.messageService = messageService;
    }

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorsResponse handleResponseStatusException(ResponseStatusException exception) {
        logger.warn("[{}] - ResponseStatusException: {}", exception.getStatusCode(), exception.toString());
        return ValidationErrorsResponse.fromMessage(exception.getStatusCode().toString());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorsResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        logger.warn("[400] - Validation Error: {}", exception.getBindingResult());
        return ValidationErrorsResponse.fromBindingResult(exception.getBindingResult(), messageService);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorsResponse handleGenericException(Exception exception) {
        logger.error("[500] - Unexpected error: {}", exception.getMessage(), exception);
        return ValidationErrorsResponse.fromMessage("An unexpected error occurred. Please contact support.");
    }
}
