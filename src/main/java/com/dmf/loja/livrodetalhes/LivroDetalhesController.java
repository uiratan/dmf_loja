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

@RestController
@RequestMapping
public class LivroDetalhesController {

    private final EntityManager entityManager;

    public LivroDetalhesController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @GetMapping("produtos/{id}")
    public ResponseEntity<?> consultarLivro(@PathVariable("id") Long id) {
        Livro livroDesejado = entityManager.find(Livro.class, id);
        if (livroDesejado == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//            return ResponseEntity.notFound().build();
        }

        LivroDetalhesResponse livroDetalheResponse = LivroDetalhesResponse.fromModel(livroDesejado);
        return ResponseEntity.ok(livroDetalheResponse);
    }
}
