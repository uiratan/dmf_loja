package com.dmf.loja.paisestado;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//2
@RestController
public class EstadosController {

    private final EntityManager entityManager;

    public EstadosController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostMapping("/estados")
    @Transactional
    //1
    public String cadastraEstado(@RequestBody @Valid NovoEstadoRequest novoEstadoRequest) {
        //1
        Estado novoEstado = novoEstadoRequest.toModel(entityManager);
        entityManager.persist(novoEstado);
        return novoEstado.toString();
    }

}
