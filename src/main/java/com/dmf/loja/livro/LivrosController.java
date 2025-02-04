package com.dmf.loja.livro;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LivrosController {

    private final EntityManager entityManager;
    public LivrosController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostMapping(value = "/livros")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public String criarLivro(@RequestBody @Valid NovoLivroRequest novoLivroRequest) {
        Livro novoLivro = novoLivroRequest.toModel();
        entityManager.persist(novoLivro);
        return novoLivro.toString();
    }
}
