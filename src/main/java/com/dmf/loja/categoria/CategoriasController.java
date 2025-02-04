package com.dmf.loja.categoria;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

//2
@RestController
public class CategoriasController {

    private final EntityManager entityManager;

    public CategoriasController(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostMapping("/categorias")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    //1
    public String criarCategoria(@RequestBody @Valid NovaCategoriaRequest novaCategoriaRequest) {
        //1
        Categoria novaCategoria = novaCategoriaRequest.toModel();
        entityManager.persist(novaCategoria);
        return novaCategoria.toString();
    }

}
