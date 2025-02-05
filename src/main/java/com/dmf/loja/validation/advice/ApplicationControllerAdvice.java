package com.dmf.loja.validation.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.Arrays;
import java.util.List;

//3
@RestControllerAdvice
public class ApplicationControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationControllerAdvice.class);

    //1
    private final MessageService messageService;

    public ApplicationControllerAdvice(MessageService messageService) {
        this.messageService = messageService;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    //1
    public ValidationErrorsResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        logger.warn("Erro de validação: {}", exception.getBindingResult());

        return new ValidationErrorsResponse(
                exception.getBindingResult().getGlobalErrors(),
                exception.getBindingResult().getFieldErrors(),
                messageService
        );
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ValidationErrorsResponse handleGenericException(Exception exception) {
//        logger.error("Unexpected error occurred: {}", exception.getMessage(), exception);
        logger.error("Unexpected error occurred: {}", exception.getMessage());
        return new ValidationErrorsResponse("An unexpected error occurred. Please contact support.");
    }

}
