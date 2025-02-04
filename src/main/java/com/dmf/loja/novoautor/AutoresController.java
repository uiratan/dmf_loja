package com.dmf.loja.novoautor;

import com.dmf.loja.validation.CampoUnicoValidator;
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
    private final CampoUnicoValidator<NovoAutorRequest> emailUnicoAutorValidator;

    //1

    public AutoresController(
            final EntityManager entityManager,
            final CampoUnicoValidator<NovoAutorRequest> emailUnicoAutorValidator1
    ) {
        this.entityManager = entityManager;
        this.emailUnicoAutorValidator = emailUnicoAutorValidator1;
    }

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.addValidators(emailUnicoAutorValidator);
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
