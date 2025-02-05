package com.dmf.loja.livro;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class LivrosController {

    private final EntityManager entityManager;

    public LivrosController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostMapping("livros")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public String criarLivro(@RequestBody @Valid NovoLivroRequest novoLivroRequest) {
        Livro novoLivro = novoLivroRequest.toModel(entityManager);
        entityManager.persist(novoLivro);
        return novoLivro.toString();
    }

    @GetMapping("livros")
    public List<LivrosResponse> listarLivros() {
        return entityManager.createQuery("SELECT l.id, l.titulo FROM Livro l", LivrosResponse.class)
                .getResultList();
    }

}
