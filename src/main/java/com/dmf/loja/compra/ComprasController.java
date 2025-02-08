package com.dmf.loja.compra;

import com.dmf.loja.compra.validators.EstadoPertenceAPaisValidator;
import com.dmf.loja.compra.validators.PaisTemEstadosValidator;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

//1
@RestController
public class ComprasController {

    private final EntityManager entityManager;
    private final EstadoPertenceAPaisValidator estadoPertenceAPaisValidator;
    private final PaisTemEstadosValidator paisTemEstadosValidator;

    public ComprasController(EntityManager entityManager, EstadoPertenceAPaisValidator estadoPertenceAPaisValidator, PaisTemEstadosValidator paisTemEstadosValidator) {
        this.entityManager = entityManager;
        this.estadoPertenceAPaisValidator = estadoPertenceAPaisValidator;
        this.paisTemEstadosValidator = paisTemEstadosValidator;
    }

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.addValidators(estadoPertenceAPaisValidator, paisTemEstadosValidator);
    }

    @Transactional
    @PostMapping("/compras")
    //1
    public ResponseEntity<Compra> realizarPagamento(@RequestBody @Valid NovaCompraRequest novaCompraRequest, UriComponentsBuilder ucb) {
        Compra novaCompra = novaCompraRequest.toModel(entityManager);
        entityManager.persist(novaCompra);

        URI locationOfNewCashCard = ucb
                .path("compras/{id}")
                .buildAndExpand(novaCompra.getId())
                .toUri();

        return ResponseEntity.created(locationOfNewCashCard).body(novaCompra);
    }
}