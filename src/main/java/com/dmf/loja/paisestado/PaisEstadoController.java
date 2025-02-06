package com.dmf.loja.paisestado;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaisEstadoController {

    private final EntityManager entityManager;

    public PaisEstadoController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostMapping("/paises")
    @Transactional
    public String cadastraPais(@RequestBody @Valid NovoPaisRequest novoPaisRequest) {
        Pais novoPais = novoPaisRequest.toModel();
        entityManager.persist(novoPais);
        return novoPais.toString();
    }

}
