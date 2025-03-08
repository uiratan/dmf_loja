package com.dmf.loja.cupom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CupomController {

//    private final EntityManager entityManager;
//
//    public CupomController(EntityManager entityManager) {
//        this.entityManager = entityManager;
//    }
@PersistenceContext
private EntityManager manager;

    @PostMapping("/cupons")
    @Transactional
    public String novoCupom(@RequestBody @Valid NovoCupomRequest novoCupomRequest) {
        Cupom novoCupom = novoCupomRequest.toModel();
        manager.persist(novoCupom);
        return novoCupom.toString();
    }
}

/**
 * resultado esperado
 * status 400 e json de erros em caso de falha de validação
 * status 200 e cupom gerado
 */