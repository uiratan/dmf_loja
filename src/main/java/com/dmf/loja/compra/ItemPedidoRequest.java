package com.dmf.loja.compra;

import com.dmf.loja.livro.Livro;
import com.dmf.loja.validation.annotations.existeid.ExisteId;
import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ItemPedidoRequest(
        @ExisteId(fieldName = "id", domainClass = Livro.class)
        @NotNull Long idLivro,
        @Positive Integer quantidade
) {
    public ItemPedido toModel(EntityManager entityManager) {
        @NotNull Livro livro = entityManager.find(Livro.class, idLivro);
        return new ItemPedido(livro, quantidade);
    }
}