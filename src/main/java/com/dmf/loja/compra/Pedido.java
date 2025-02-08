package com.dmf.loja.compra;

import com.dmf.loja.livro.Livro;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class Pedido {

    @NotNull private final Compra compra;
    @Size(min = 1) private final Set<ItemPedido> itens = new HashSet<>();

    public Pedido(Compra compra, Set<ItemPedido> itens) {
        Assert.notNull(compra, "a compra não pode ser nulo");
        Assert.isTrue(!itens.isEmpty(), "todo pedido deve ter pelo menos um item");

        this.compra = compra;
        this.itens.addAll(itens);
    }

    public boolean verificaTotal(@NotNull @Positive BigDecimal total) {
        return totalPedido().doubleValue() == total.doubleValue();
    }

    private BigDecimal totalPedido() {
        return itens.stream()
                .map(ItemPedido::total)
                .reduce(BigDecimal.ZERO, BigDecimal::add); // valor inicial, função de redução

    }

    @Override
    public String toString() {
        return "Pedido{" +
                "compra=" + compra +
                ", itens=" + itens +
                '}';
    }
}