package com.dmf.loja.compra;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ItemCompra {
    @NotNull @Min(1) Long idLivro;
    @NotNull @Min(1) Integer quantidade;

    public ItemCompra(Long idLivro, Integer quantidade) {
        this.idLivro = idLivro;
        this.quantidade = quantidade;
    }

    @Deprecated
    public ItemCompra() {
    }

    public Long getIdLivro() { return idLivro; }
    public Integer getQuantidade() { return quantidade; }
}
