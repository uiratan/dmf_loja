package com.dmf.loja.compra;

import com.dmf.loja.autor.Autor;
import com.dmf.loja.categoria.Categoria;
import com.dmf.loja.livro.Livro;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

class PedidoTest {

    @DisplayName("verifica se o total do pedido é igual ao passado como argumento")
    @ParameterizedTest
    @CsvSource({
            "49.99, false",
            "50, true",
            "50.01, false"
    })
    void teste1(BigDecimal valor, boolean resultadoEsperado) {
        Autor autor = new Autor("nome", "machado@google.com", "desc");
        Categoria categoria = new Categoria("Ficção");
        Livro livro1 = new Livro("titulo", "Resumo", "Sumário", BigDecimal.valueOf(50.0),
                200, "1234567890", LocalDate.now().plusDays(10), categoria, autor);

        Set<ItemPedido> itens = Set.of(new ItemPedido(livro1, 1));
        Pedido pedido = new Pedido(Mockito.mock(Compra.class), itens);

        Assertions.assertEquals(resultadoEsperado, pedido.verificaTotal(valor));
    }

    @Test
    @DisplayName("verifica lista de itens vazia")
    void teste2() {
        // Verifica se IllegalArgumentException é lançada ao tentar criar um Pedido com itens vazios
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Pedido(Mockito.mock(Compra.class), new HashSet<>());
        });

        // Verifica se a mensagem da exceção é a esperada
        Assertions.assertEquals("todo pedido deve ter pelo menos um item", exception.getMessage());
    }



}