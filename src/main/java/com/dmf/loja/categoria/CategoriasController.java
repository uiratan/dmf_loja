package com.dmf.loja.categoria;

import com.dmf.loja.validation.CampoUnicoValidator;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoriasController {

    private final EntityManager entityManager;
    private final CampoUnicoValidator<NovaCategoriaRequest> nomeUnicoCategoriaValidator;

    public CategoriasController(
            final EntityManager entityManager,
            final CampoUnicoValidator<NovaCategoriaRequest> nomeUnicoCategoriaValidator) {
        this.entityManager = entityManager;
        this.nomeUnicoCategoriaValidator = nomeUnicoCategoriaValidator;
    }

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.addValidators(nomeUnicoCategoriaValidator);
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
