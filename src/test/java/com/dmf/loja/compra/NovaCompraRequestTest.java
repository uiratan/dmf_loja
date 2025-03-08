package com.dmf.loja.compra;

import com.dmf.loja.autor.Autor;
import com.dmf.loja.categoria.Categoria;
import com.dmf.loja.cupom.Cupom;
import com.dmf.loja.cupom.CupomRepository;
import com.dmf.loja.livro.Livro;
import com.dmf.loja.paisestado.Estado;
import com.dmf.loja.paisestado.Pais;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

class NovaCompraRequestTest {

    private EntityManager entityManager;
    private CupomRepository cupomRepository;

    private Pais pais;
    private Estado estado;
    private Cupom cupom;
    private PedidoRequest pedido;
    private NovaCompraRequest request;

    @BeforeEach
    void setUp() {
        entityManager = Mockito.mock(EntityManager.class);
        cupomRepository = Mockito.mock(CupomRepository.class);

        List<ItemPedidoRequest> itemPedidos = List.of(new ItemPedidoRequest(1L, 1));
        pedido = new PedidoRequest(BigDecimal.valueOf(50), itemPedidos);

        Categoria categoria = new Categoria("Java");
        Autor autor = new Autor("Nome do Autor", "autor@email.com", "Biografia");
        Livro livro = new Livro(
                "titulo",
                "resumo",
                "sumario",
                BigDecimal.valueOf(50),
                100,
                "isbn",
                LocalDate.now().plusDays(10),
                categoria,
                autor
        );

        pais = new Pais("Brasil");
        estado = new Estado("Piaui", pais);

        cupom = new Cupom("codigo-cupom", BigDecimal.TEN, LocalDate.now().plusDays(1));

        Mockito.when(entityManager.find(Livro.class, 1L)).thenReturn(livro);
        Mockito.when(entityManager.find(Pais.class, 1L)).thenReturn(pais);
        Mockito.when(entityManager.find(Estado.class, 1L)).thenReturn(estado);
        Mockito.when(cupomRepository.getByCodigo("codigo-cupom")).thenReturn(cupom);
    }

    @Test
    @DisplayName("cria compra com estado e com cupom")
    void teste1() throws JsonProcessingException {
        request = getNovaCompraRequest(1L, "codigo-cupom");
        Compra compra = request.toModel(entityManager, cupomRepository);

        Assertions.assertNotNull(compra);
        Assertions.assertEquals(compra.getEstado(), estado);
        Assertions.assertEquals(compra.getCupomAplicado().getPercentualDescontoMomento(), cupom.getPercentualDesconto());

        Mockito.verify(entityManager, Mockito.times(1)).find(Estado.class, 1L);
        Mockito.verify(cupomRepository, Mockito.times(1)).getByCodigo("codigo-cupom");
    }

    @Test
    @DisplayName("cria compra sem estado e com cupom")
    void teste2() throws JsonProcessingException {
        request = getNovaCompraRequest(null, "codigo-cupom");
        Compra compra = request.toModel(entityManager, cupomRepository);

        Assertions.assertNotNull(compra);
        Assertions.assertNull(compra.getEstado());
        Assertions.assertEquals(compra.getCupomAplicado().getPercentualDescontoMomento(), cupom.getPercentualDesconto());

        Mockito.verify(entityManager, Mockito.never()).find(Mockito.eq(Estado.class), Mockito.anyLong());
        Mockito.verify(cupomRepository, Mockito.times(1)).getByCodigo("codigo-cupom");
    }

    @Test
    @DisplayName("cria compra sem estado e sem cupom")
    void teste3() throws JsonProcessingException {
        request = getNovaCompraRequest(null, null);
        Compra compra = request.toModel(entityManager, cupomRepository);

        Assertions.assertNotNull(compra);
        Assertions.assertNull(compra.getEstado());
        Assertions.assertNull(compra.getCupomAplicado());

        Mockito.verify(entityManager, Mockito.never()).find(Mockito.eq(Estado.class), Mockito.anyLong());
        Mockito.verify(cupomRepository, Mockito.never()).getByCodigo(Mockito.anyString());

    }

    private NovaCompraRequest getNovaCompraRequest(Long idEstado, String cupom) {
        return new NovaCompraRequest(
                "uiratan@gmail.com",
                "uiratan",
                "cavalcante",
                "64915956334x",
                "endereço",
                "complemento",
                "cidade",
                1L,
                idEstado,
                "telefone",
                "cep",
                pedido,
                cupom
        );
    }


}