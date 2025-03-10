package com.dmf.loja.livro;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//3
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
    //1
    public String criarLivro(@RequestBody @Valid NovoLivroRequest novoLivroRequest) {
        //1
        Livro novoLivro = novoLivroRequest.toModel(entityManager);
        entityManager.persist(novoLivro);
        return novoLivro.toString();
    }

    //1
    @GetMapping("livros")
    public List<LivrosResponse> listarLivros() {
        return entityManager.createQuery("SELECT l.id, l.titulo, l.preco FROM Livro l", LivrosResponse.class)
                .getResultList();
    }

}
