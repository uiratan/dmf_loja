package com.dmf.loja.compra.validators;

import com.dmf.loja.compra.ItemPedidoRequest;
import com.dmf.loja.compra.NovaCompraRequest;
import com.dmf.loja.compra.PedidoRequest;
import com.dmf.loja.paisestado.Estado;
import com.dmf.loja.paisestado.Pais;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.math.BigDecimal;
import java.util.List;

class EstadoPertenceAPaisValidatorTest {

    private final EntityManager manager = Mockito.mock(EntityManager.class);
    private final Pais paisPadrao = new Pais("teste");

    List<ItemPedidoRequest> itens = List.of(new ItemPedidoRequest(1L, 1));
    PedidoRequest pedido = new PedidoRequest(BigDecimal.valueOf(50), itens);

    @Test
    @DisplayName("deveria parar se ja tem erro de validação no fluxo")
    void testaValidateComErrors() {
        NovaCompraRequest request = getNovaCompraRequest(1L);

        Errors errors = new BeanPropertyBindingResult(request, "target");
        errors.reject("codigoQualquer");

        EstadoPertenceAPaisValidator validator = new EstadoPertenceAPaisValidator(manager);
        validator.validate(request, errors);

        Assertions.assertTrue(errors.hasErrors());
        Assertions.assertEquals("codigoQualquer", errors.getGlobalErrors().get(0).getCode());
    }

    @Test
    @DisplayName("deveria passar caso não tenha código de estado")
    void testaValidateSemErrors() {
        NovaCompraRequest request = getNovaCompraRequest(null);

        Errors errors = new BeanPropertyBindingResult(request, "target");
        EstadoPertenceAPaisValidator validator = new EstadoPertenceAPaisValidator(manager);
        validator.validate(request, errors);

        Assertions.assertFalse(errors.hasErrors());
    }

    @Test
    @DisplayName("deveria bloquear compra com pais e estado nao pertencente ao pais")
    void testaCompraComPaisEEstadoNaoPertenceAoPais() {
        Pais paisDiferente = new Pais("diferente");
        Estado estadoDeOutroPais = new Estado("estado", paisDiferente);
        Mockito.when(manager.find(Estado.class, 2L)).thenReturn(estadoDeOutroPais);

        Mockito.when(manager.find(Pais.class, 1L)).thenReturn(paisPadrao);

        NovaCompraRequest request = getNovaCompraRequest(2L);

        Errors errors = new BeanPropertyBindingResult(request, "target");
        EstadoPertenceAPaisValidator validator = new EstadoPertenceAPaisValidator(manager);
        validator.validate(request, errors);

        Assertions.assertEquals(1, errors.getAllErrors().size());
        Assertions.assertTrue(errors.hasFieldErrors("idEstado"));
    }

    @Test
    @DisplayName("deveria validar uma compra com pais e estado pertencente ao pais")
    void testaCompraComPaisEEstadoPertenceAoPais() {
        Estado estadoDoPais = new Estado("estado", paisPadrao);
        Mockito.when(manager.find(Pais.class, 1L)).thenReturn(paisPadrao);
        Mockito.when(manager.find(Estado.class, 1L)).thenReturn(estadoDoPais);

        NovaCompraRequest request = getNovaCompraRequest(1L);

        Errors errors = new BeanPropertyBindingResult(request, "target");
        EstadoPertenceAPaisValidator validator = new EstadoPertenceAPaisValidator(manager);
        validator.validate(request, errors);

        Assertions.assertFalse(errors.hasErrors());
    }

    private NovaCompraRequest getNovaCompraRequest(Long idEstado) {
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
                null
        );
    }
}