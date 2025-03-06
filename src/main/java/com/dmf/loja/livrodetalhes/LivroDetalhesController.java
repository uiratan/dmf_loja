package com.dmf.loja.livrodetalhes;

import com.dmf.loja.livro.Livro;
import jakarta.persistence.EntityManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

//3
@RestController
@RequestMapping
public class LivroDetalhesController {

    private final EntityManager entityManager;

    public LivroDetalhesController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @GetMapping("produtos/{id}")
    public ResponseEntity<?> consultarLivro(@PathVariable("id") Long id) {
        //1
        Livro livroDesejado = entityManager.find(Livro.class, id);
        //1
        Livro livroBuscado = Optional.ofNullable(entityManager.find(Livro.class, id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        //1
        LivroDetalhesResponse livroDetalheResponse = LivroDetalhesResponse.fromModel(livroDesejado);
        return ResponseEntity.ok(livroDetalheResponse);
    }
}
