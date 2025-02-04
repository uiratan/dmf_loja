package com.dmf.loja.autor;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

//3
@RestController
public class AutoresController {

    private final EntityManager entityManager;

    public AutoresController(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostMapping(value = "/autores")
    @Transactional
    @ResponseStatus(HttpStatus.OK)
    //1
    public String criaAutor(@RequestBody @Valid NovoAutorRequest novoAutorRequest) {
        //1
        Autor novoAutor = novoAutorRequest.toModel();
        entityManager.persist(novoAutor);
        return novoAutor.toString();
    }
}
