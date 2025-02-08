package com.dmf.loja.livro;

import java.math.BigDecimal;

//0
public record LivrosResponse(
        Long id,
        String titulo,
        BigDecimal preco
) {
}
