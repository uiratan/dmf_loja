package com.dmf.loja.compra;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record CarrinhoRequest(
        @NotNull @DecimalMin(value = "0.00", inclusive = false, message = "o total deve ser maior que zero")
        BigDecimal total,
        @NotNull @Valid List<ItemCarrinhoRequest> itens
) { }