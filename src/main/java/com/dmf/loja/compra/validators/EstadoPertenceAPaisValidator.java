package com.dmf.loja.compra.validators;

import com.dmf.loja.compra.NovaCompraRequest;
import com.dmf.loja.paisestado.Estado;
import com.dmf.loja.paisestado.Pais;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class EstadoPertenceAPaisValidator implements Validator {

    private final EntityManager entityManager;

    public EstadoPertenceAPaisValidator(EntityManager entityManager) {
        this.entityManager = entityManager;
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

        if (novaCompraRequest.isIdEstadoInformado()) {
            Pais pais = entityManager.find(Pais.class, novaCompraRequest.idPais());
            Estado estado = entityManager.find(Estado.class, novaCompraRequest.idEstado());
            if (!estado.pertenceAoPais(pais)) {
                errors.rejectValue("idEstado", null, "este estado não é o do país selecionado");
            }
        }
    }
}
