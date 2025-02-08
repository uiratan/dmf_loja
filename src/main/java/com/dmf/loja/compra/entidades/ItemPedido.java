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
    @JoinColumn(name = "compra_id", nullable = false)
    private Compra compra;

    public ItemPedido(Long idLivro, Integer quantidade) {
        this.idLivro = idLivro;
        this.quantidade = quantidade;
    }

    @Deprecated
    public ItemPedido() {
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }


    public Long getIdLivro() { return idLivro; }
    public Integer getQuantidade() { return quantidade; }

}
