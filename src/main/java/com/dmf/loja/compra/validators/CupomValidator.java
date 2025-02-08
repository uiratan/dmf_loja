package com.dmf.loja.compra.validators;

import com.dmf.loja.compra.NovaCompraRequest;
import com.dmf.loja.cupom.CupomRepository;
import com.dmf.loja.paisestado.Estado;
import com.dmf.loja.paisestado.Pais;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

/**
 * Valida se a data do cupom está válida
 */

@Component
public class CupomValidator implements Validator {

    private final CupomRepository cupomRepository;

    public CupomValidator(CupomRepository cupomRepository) {
        this.cupomRepository = cupomRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return NovaCompraRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        String codigoCupom = ((NovaCompraRequest) target).cupom();

        boolean cupomNãoExpirado = cupomRepository.existsByCodigoAndDataValidadeAfter(codigoCupom.toLowerCase(), LocalDate.now());

        if (!cupomNãoExpirado) {
            errors.rejectValue("cupom", null, "este cupom está expirado");
        }
    }

}
