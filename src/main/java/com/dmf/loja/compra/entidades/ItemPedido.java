package com.dmf.loja.compra.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
public class ItemPedido {
    @Id @GeneratedValue private Long id;
    @NotNull Long idLivro;
    @Positive Integer quantidade;
    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    public ItemPedido(Long idLivro, Integer quantidade, Pedido pedido) {
        this.idLivro = idLivro;
        this.quantidade = quantidade;
        this.pedido = pedido;
    }

    @Deprecated
    public ItemPedido() {
    }

    public Long getIdLivro() { return idLivro; }
    public Integer getQuantidade() { return quantidade; }

}
