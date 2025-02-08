package com.dmf.loja.compra;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public record CarrinhoRequest(
        @NotNull @Positive
        BigDecimal total,
        @Size(min=1)
        @NotNull @Valid List<ItemCarrinhoRequest> itens
) { }