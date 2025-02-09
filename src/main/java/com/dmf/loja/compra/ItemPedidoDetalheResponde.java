package com.dmf.loja.compra;

import java.math.BigDecimal;

public record ItemPedidoDetalheResponde(
    String livro,
    Integer quantidade,
    BigDecimal precoMomento
) {

    public static ItemPedidoDetalheResponde toModel(ItemPedido itemPedido) {
        return new ItemPedidoDetalheResponde(
                itemPedido.getLivro().getTitulo(),
                itemPedido.getQuantidade(),
                itemPedido.getPrecoMomento()
        );
    }
}
