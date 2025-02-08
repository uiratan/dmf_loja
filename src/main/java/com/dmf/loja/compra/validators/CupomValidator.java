package com.dmf.loja.compra.validators;

import com.dmf.loja.compra.NovaCompraRequest;
import com.dmf.loja.cupom.Cupom;
import com.dmf.loja.cupom.CupomRepository;
import org.springframework.stereotype.Component;
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

        // Se o cupom for nulo ou nao for informado um codigo, nao valida nada
        if (novaCompraRequest.isCodigoCupomInformado()) {

            String codigoCupomMinusculas = novaCompraRequest.codigoCupom().toLowerCase();

            Optional<Cupom> cupomEncontrado = cupomRepository.findByCodigo(codigoCupomMinusculas);

            // Ao invés de usar get() (que pode gerar NoSuchElementException),
            // estamos lidando diretamente com a presença ou ausência do valor de forma segura.
            cupomEncontrado.ifPresentOrElse(
                    cupom -> {
                        if (cupom.isExpirado()) {
                            errors.rejectValue("codigoCupom", null, "este cupom está expirado");
                        }
                    },
                    () -> errors.rejectValue("codigoCupom", null, "este cupom não existe")
            );

//            Optional<Cupom> cupomEncontrado = cupomRepository.findByCodigo(codigoCupomMinusculas);
//
//            if (!cupomEncontrado.isPresent()) {
//                errors.rejectValue("codigoCupom", null, "este cupom não existe");
//                return;
//            }
//
//            if (cupomEncontrado.get().isExpirado()) {
//                errors.rejectValue("codigoCupom", null, "este cupom está expirado");
//            }

        }

    }

}


