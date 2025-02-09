package com.dmf.loja.compra.validators;

import com.dmf.loja.compra.NovaCompraRequest;
import com.dmf.loja.cupom.Cupom;
import com.dmf.loja.cupom.CupomRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

/**
 * Valida a existência do cupom e se a data do cupom está válida
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

        NovaCompraRequest novaCompraRequest = (NovaCompraRequest) target;

        Optional<String> possivelCodigo = novaCompraRequest.findCodigoCupom();
        if (possivelCodigo.isPresent()) {
            Cupom cupom = cupomRepository.getByCodigo(possivelCodigo.get());
            if (!cupom.isValido()) {
                errors.rejectValue("codigoCupom", null, "este cupom está expirado");
            }
        }

    }

}




