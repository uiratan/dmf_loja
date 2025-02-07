package com.dmf.loja.compra;

import com.dmf.loja.paisestado.EstadoPertenceAPaisValidator;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//1
@RestController
public class CompraController {

    private final EntityManager entityManager;
    private final EstadoPertenceAPaisValidator estadoPertenceAPaisValidator;

    public CompraController(EntityManager entityManager, EstadoPertenceAPaisValidator estadoPertenceAPaisValidator) {
        this.entityManager = entityManager;
        this.estadoPertenceAPaisValidator = estadoPertenceAPaisValidator;
    }

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.addValidators(estadoPertenceAPaisValidator);
    }

    @PostMapping("/compras")
    //1
    public Compra realizarPagamento(@RequestBody @Valid NovaCompraRequest novaCompraRequest) {
        return novaCompraRequest.toModel(entityManager);
    }
}