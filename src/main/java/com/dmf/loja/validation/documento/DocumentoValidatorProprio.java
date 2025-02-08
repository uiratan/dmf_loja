package com.dmf.loja.validation.documento;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DocumentoValidatorProprio implements ConstraintValidator<CPFCNPJ, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.isEmpty()) {
            return true;
        }

        return validarDocumento(value);
    }

    // Método para validar CPF ou CNPJ
    private static boolean validarDocumento(String documento) {
        if (documento == null) {
            return false;
        }

        documento = documento.replaceAll("[^0-9]", "");  // Remove caracteres não numéricos

        if (documento.length() == 11) {
            return validarCPF(documento);
        } else if (documento.length() == 14) {
            return validarCNPJ(documento);
        }

        return false;  // Se o comprimento não for nem 11 nem 14, retorna inválido
    }

    // Método para validar CPF
    private static boolean validarCPF(String cpf) {
        if (cpf == null || cpf.length() != 11 || !cpf.matches("[0-9]{11}")) {
            return false;
        }

        // Verifica se o CPF é um número repetido, como 111.111.111-11, 222.222.222-22, etc.
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        int soma = 0;
        int peso = 10;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * peso--;
        }

        int digito1 = 11 - (soma % 11);
        if (digito1 == 10 || digito1 == 11) digito1 = 0;

        soma = 0;
        peso = 11;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * peso--;
        }

        int digito2 = 11 - (soma % 11);
        if (digito2 == 10 || digito2 == 11) digito2 = 0;

        return digito1 == Character.getNumericValue(cpf.charAt(9)) && digito2 == Character.getNumericValue(cpf.charAt(10));
    }

    // Método para validar CNPJ
    private static boolean validarCNPJ(String cnpj) {
        if (cnpj == null || cnpj.length() != 14 || !cnpj.matches("[0-9]{14}")) {
            return false;
        }

        // Verifica se o CNPJ é um número repetido, como 11.111.111/0001-11, 22.222.222/0001-22, etc.
        if (cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }

        int soma = 0;
        int[] peso1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        for (int i = 0; i < 12; i++) {
            soma += Character.getNumericValue(cnpj.charAt(i)) * peso1[i];
        }

        int digito1 = 11 - (soma % 11);
        if (digito1 == 10 || digito1 == 11) digito1 = 0;

        soma = 0;
        int[] peso2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        for (int i = 0; i < 13; i++) {
            soma += Character.getNumericValue(cnpj.charAt(i)) * peso2[i];
        }

        int digito2 = 11 - (soma % 11);
        if (digito2 == 10 || digito2 == 11) digito2 = 0;

        return digito1 == Character.getNumericValue(cnpj.charAt(12)) && digito2 == Character.getNumericValue(cnpj.charAt(13));
    }



}