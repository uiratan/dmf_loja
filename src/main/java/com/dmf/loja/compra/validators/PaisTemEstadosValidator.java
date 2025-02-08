package com.dmf.loja.compra.validators;

import com.dmf.loja.compra.NovaCompraRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PaisTemEstadosValidator implements Validator {

    private final EntityManager entityManager;

    public PaisTemEstadosValidator(EntityManager entityManager) {
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

        if (paisPossuiEstado(novaCompraRequest) && estadoNaoInformado(novaCompraRequest)) {
            errors.rejectValue("idEstado", null, "este país precisa de um estado");
        }
    }

    private boolean estadoNaoInformado(NovaCompraRequest novaCompraRequest) {
        return novaCompraRequest.idEstado() == null;
    }

    private boolean paisPossuiEstado(NovaCompraRequest novaCompraRequest) {
        String jpql = "SELECT COUNT(e) FROM Estado e WHERE e.pais.id = :paisId";
        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        query.setParameter("paisId", novaCompraRequest.idPais());
        Long count = query.getSingleResult();
        return count > 0;

    }
}
