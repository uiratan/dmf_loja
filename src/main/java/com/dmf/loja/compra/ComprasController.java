package com.dmf.loja.compra;

import com.dmf.loja.compra.validators.CupomValidator;
import com.dmf.loja.compra.validators.EstadoPertenceAPaisValidator;
import com.dmf.loja.compra.validators.PaisTemEstadosValidator;
import com.dmf.loja.cupom.CupomRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

//1
@RestController
public class ComprasController {

    private final EntityManager entityManager;
    private final EstadoPertenceAPaisValidator estadoPertenceAPaisValidator;
    private final PaisTemEstadosValidator paisTemEstadosValidator;
    private final CupomValidator cupomValidator;

    private final CupomRepository cupomRepository;

    public ComprasController(EntityManager entityManager, EstadoPertenceAPaisValidator estadoPertenceAPaisValidator, PaisTemEstadosValidator paisTemEstadosValidator, CupomValidator cupomValidator, CupomRepository cupomRepository) {
        this.entityManager = entityManager;
        this.estadoPertenceAPaisValidator = estadoPertenceAPaisValidator;
        this.paisTemEstadosValidator = paisTemEstadosValidator;
        this.cupomValidator = cupomValidator;
        this.cupomRepository = cupomRepository;
    }

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.addValidators(estadoPertenceAPaisValidator, paisTemEstadosValidator, cupomValidator);
    }

    @Transactional
    @PostMapping("/compras")
    //1
    public ResponseEntity<String> realizarPagamento(@RequestBody @Valid NovaCompraRequest novaCompraRequest, UriComponentsBuilder ucb) {
        Compra novaCompra = novaCompraRequest.toModel(entityManager, cupomRepository);
        entityManager.persist(novaCompra);

        URI locationOfNewCashCard = ucb
                .path("compras/{id}")
                .buildAndExpand(novaCompra.getId())
                .toUri();

        return ResponseEntity.created(locationOfNewCashCard).body(novaCompra.toString());
    }

    @GetMapping("compras/{id}")
    public CompraDetalheResponse consultaCompra(@PathVariable(name = "id") Long codigoDaCompra) {
        return CompraDetalheResponse.from(codigoDaCompra, entityManager);
    }
}