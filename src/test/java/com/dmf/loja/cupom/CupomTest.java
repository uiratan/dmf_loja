package com.dmf.loja.cupom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

class CupomTest {

    @ParameterizedTest
    @CsvSource({
            "0,true",
            "1,true"
    })
    void teste1(long valor, boolean resultado) {
        Cupom cupom = new Cupom("codigo", BigDecimal.TEN, LocalDate.now().plusDays(valor));
        Assertions.assertEquals(resultado, cupom.isValido());
    }

    @Test
    @DisplayName("cupom com data passada não é mais válido")
    void test2() {
        Cupom cupom = new Cupom("codigo", BigDecimal.TEN, LocalDate.now().plusDays(1));
        ReflectionTestUtils.setField(cupom, "dataValidade", LocalDate.now().minusDays(1));
        Assertions.assertFalse(cupom.isValido());
    }

    @Test
    @DisplayName("nao pode criar cupom com data no passado")
    void teste3() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Cupom("codigo", BigDecimal.TEN, LocalDate.now().minusDays(1));
        });
    }

    @ParameterizedTest
    @CsvSource({
            "0",
            "-1"
    })
    void teste4(BigDecimal desconto) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Cupom("codigo", desconto, LocalDate.now().minusDays(1));
        });
    }



}