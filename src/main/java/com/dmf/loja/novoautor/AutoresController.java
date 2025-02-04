package com.dmf.loja.novoautor;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

//3
@RestController
public class AutoresController {

    private final EntityManager entityManager;

    //1
    private final ProibeEmailDuplicadoAutorValidator proibeEmailDuplicadoAutorValidator;

    public AutoresController(EntityManager entityManager, ProibeEmailDuplicadoAutorValidator proibeEmailDuplicadoAutorValidator) {
        this.entityManager = entityManager;
        this.proibeEmailDuplicadoAutorValidator = proibeEmailDuplicadoAutorValidator;
    }

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.addValidators(proibeEmailDuplicadoAutorValidator);
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
