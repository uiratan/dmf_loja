package com.dmf.loja.compra;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ItemCarrinhoRequest(
        @NotNull @Min(1) Long idLivro,
        @NotNull @Min(1) Integer quantidade
) { }