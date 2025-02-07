package com.dmf.loja.compra;

import com.dmf.loja.livro.Livro;
import com.dmf.loja.validation.existeid.ExisteId;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ItemCarrinhoRequest(
        @ExisteId(fieldName = "id", domainClass = Livro.class)
        @NotNull @Min(1) Long idLivro,
        @NotNull @Min(1) Integer quantidade
) { }