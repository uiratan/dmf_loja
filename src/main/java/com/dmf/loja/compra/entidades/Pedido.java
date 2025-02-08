package com.dmf.loja.compra.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pedido {
    @Id @GeneratedValue private Long id;
    @NotNull @Positive BigDecimal total;
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @NotNull
    List<ItemPedido> itens = new ArrayList<>();
    @OneToOne
    @JoinColumn(name = "compra_id", nullable = false)
    private Compra compra;

    public Pedido(BigDecimal total, Compra compra) {
        Assert.notNull(total, "O total não pode ser nulo");
        Assert.isTrue(total.compareTo(BigDecimal.ZERO) > 0, "O total deve ser maior a zero");
        Assert.notNull(compra, "A compra não pode ser nula");

        this.total = total;
        this.compra = compra;
    }

    @Deprecated
    public Pedido() {}

    public void setItens(List<ItemPedido> itens) {
        Assert.notNull(itens, "A lista de itens não pode ser nula");
        Assert.notEmpty(itens, "A lista de itens não pode estar vazia");
        this.itens = itens;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }
}
