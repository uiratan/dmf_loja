package com.dmf.loja.validation.documento;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;

public class DocumentoValidator implements ConstraintValidator<CPFCNPJ, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.isEmpty()) {
            return true;
        }

        String documento = value.replaceAll("[^0-9]", "");  // Remove caracteres não numéricos

        return validarDocumento(documento);
    }

    private static boolean validarDocumento(String documento) {
        if (documento.length() == 11) {
            return validarCPF(documento);
        } else if (documento.length() == 14) {
            return validarCNPJ(documento);
        }

        return false;
    }

    private static boolean validarCPF(String documento) {
        CPFValidator cpfValidator = new CPFValidator();
        cpfValidator.initialize(null);
        return cpfValidator.isValid(documento, null);
    }

    private static boolean validarCNPJ(String documento) {
        CNPJValidator cnpjValidator = new CNPJValidator();
        cnpjValidator.initialize(null);
        return cnpjValidator.isValid(documento, null);
    }

}