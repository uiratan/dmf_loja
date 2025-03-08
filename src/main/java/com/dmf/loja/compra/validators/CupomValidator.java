package com.dmf.loja.compra.validators;

import com.dmf.loja.compra.NovaCompraRequest;
import com.dmf.loja.cupom.Cupom;
import com.dmf.loja.cupom.CupomRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;
import java.util.Optional;

/**
 * Valida a existência do cupom e se a data do cupom está válida
 */

@Component
public class CupomValidator implements Validator {

    //1
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
        //1
        if (errors.hasErrors()) {
            return;
        }

        //1
        NovaCompraRequest novaCompraRequest = (NovaCompraRequest) target;

        Optional<String> possivelCodigo = novaCompraRequest.findCodigoCupom();
        //1
        if (possivelCodigo.isPresent()) {
            //1
            Cupom cupom = cupomRepository.getByCodigo(possivelCodigo.get());
            Assert.state(Objects.nonNull(cupom), "o código do cupom precisa existir neste ponto da validação");
            //1
            if (!cupom.isValido()) {
                errors.rejectValue("codigoCupom", null, "este cupom está expirado");
            }
        }

    }

}




