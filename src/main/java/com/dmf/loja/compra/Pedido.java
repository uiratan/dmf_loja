package com.dmf.loja.compra;

import com.dmf.loja.livro.Livro;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Pedido {

    @Id @GeneratedValue private Long id;
    @OneToOne
    @NotNull private Compra compra;
    @ElementCollection
    @Size(min = 1) private Set<ItemPedido> itens = new HashSet<>();

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
                "itens=" + itens +
                '}';
    }
}