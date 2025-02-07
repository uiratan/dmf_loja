package com.dmf.loja.compra;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record CarrinhoRequest(
        @NotNull @DecimalMin("0.0") BigDecimal total,
        @NotNull @Valid List<ItemCarrinhoRequest> itens
) { }