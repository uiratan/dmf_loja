package com.dmf.loja.compra.validators;

import com.dmf.loja.compra.ItemPedidoRequest;
import com.dmf.loja.compra.NovaCompraRequest;
import com.dmf.loja.compra.PedidoRequest;
import com.dmf.loja.cupom.Cupom;
import com.dmf.loja.cupom.CupomRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

class CupomValidatorTest {

    List<ItemPedidoRequest> itens = List.of(new ItemPedidoRequest(1L, 1));
    PedidoRequest pedido = new PedidoRequest(BigDecimal.valueOf(50), itens);
    private CupomRepository cupomRepository = Mockito.mock(CupomRepository.class);

    @Test
    @DisplayName("deveria parar caso já tenha erro")
    void testaValidateComErrors() {
        NovaCompraRequest request = getNovaCompraRequest(1L, null);

        Errors errors = new BeanPropertyBindingResult(request, "target");
        errors.reject("codigo");

        CupomValidator validator = new CupomValidator(cupomRepository);
        validator.validate(request, errors);

        Assertions.assertTrue(errors.hasErrors());
        Assertions.assertEquals("codigo", errors.getGlobalErrors().get(0).getCode());
    }

    @Test
    @DisplayName("deveria passar caso não tenha código de cupom")
    void testaValidateSemErrors() {
        NovaCompraRequest request = getNovaCompraRequest(1L, null);

        Errors errors = new BeanPropertyBindingResult(request, "target");
        CupomValidator validator = new CupomValidator(cupomRepository);
        validator.validate(request, errors);

        Assertions.assertFalse(errors.hasErrors());
    }

    @Test
    @DisplayName("deveria passar caso o cupom esteja válido")
    void testaValidateComCupomECupomValido() {
        Cupom cupom = new Cupom("codigo", BigDecimal.TEN, LocalDate.now().plusDays(1));

        NovaCompraRequest request = getNovaCompraRequest(1L, "codigo");
        Mockito.when(cupomRepository.getByCodigo("codigo")).thenReturn(cupom);

        Errors errors = new BeanPropertyBindingResult(request, "target");
        CupomValidator validator = new CupomValidator(cupomRepository);
        validator.validate(request, errors);

        Assertions.assertFalse(errors.hasErrors());
    }

    @Test
    void testaValidateComCupomECupomInvalido() {
        Cupom cupom = new Cupom("codigo", BigDecimal.TEN, LocalDate.now().plusDays(1));
        ReflectionTestUtils.setField(cupom, "dataValidade", LocalDate.now().minusDays(1));

        NovaCompraRequest request = getNovaCompraRequest(1L, "codigo");
        Mockito.when(cupomRepository.getByCodigo("codigo")).thenReturn(cupom);

        Errors errors = new BeanPropertyBindingResult(request, "target");
        CupomValidator validator = new CupomValidator(cupomRepository);
        validator.validate(request, errors);

        Assertions.assertTrue(errors.hasErrors());
        Assertions.assertEquals("este cupom está expirado", errors.getFieldError("codigoCupom").getDefaultMessage());
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