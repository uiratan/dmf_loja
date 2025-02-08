package com.dmf.loja.compra;

import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public record PedidoRequest(
        @NotNull @Positive
        BigDecimal total,
        @Size(min = 1)
        @NotNull @Valid List<ItemPedidoRequest> itens
) {

    public Function<Compra, Pedido> toModel(EntityManager entityManager) {
        Set<ItemPedido> itensCalculados = itens().stream()
                .map(item -> item.toModel(entityManager))
                .collect(Collectors.toSet());

        return (compra) -> {
            Pedido pedido = new Pedido(compra, itensCalculados);
            Assert.isTrue(pedido.verificaTotal(total), "o valor total enviado está divergente do total real");
            return pedido;
        };
    }
}