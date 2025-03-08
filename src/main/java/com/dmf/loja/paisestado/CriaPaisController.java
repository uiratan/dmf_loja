package com.dmf.loja.paisestado;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//2
@RestController
public class CriaPaisController {

    private final EntityManager entityManager;

    public CriaPaisController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostMapping("/paises")
    @Transactional
    //1
    public String cadastraPais(@RequestBody @Valid NovoPaisRequest novoPaisRequest) {
        //1
        Pais novoPais = novoPaisRequest.toModel();
        entityManager.persist(novoPais);
        return novoPais.toString();
    }

}
