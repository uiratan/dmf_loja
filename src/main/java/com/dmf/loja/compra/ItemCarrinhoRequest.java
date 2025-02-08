package com.dmf.loja.compra;

import com.dmf.loja.livro.Livro;
import com.dmf.loja.validation.existeid.ExisteId;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ItemCarrinhoRequest(
        @ExisteId(fieldName = "id", domainClass = Livro.class)
        @NotNull Long idLivro,
        @Positive Integer quantidade
) { }