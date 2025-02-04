package com.dmf.loja.categoria;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoriasController {

    private final EntityManager entityManager;

    public CategoriasController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostMapping("/categorias")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public String criarCategoria(@RequestBody @Valid NovaCategoriaRequest novaCategoriaRequest) {
        Categoria novaCategoria = novaCategoriaRequest.toModel();
        entityManager.persist(novaCategoria);
        return novaCategoria.toString();
    }

}
