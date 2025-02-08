package com.dmf.loja.cupom;

import com.dmf.loja.validation.annotations.campounico.CampoUnico;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record NovoCupomRequest(

        @CampoUnico(domainClass = Cupom.class, fieldName = "codigo")
        @NotBlank String codigo,
        @NotNull @Positive BigDecimal percentualDesconto,
        @JsonFormat(pattern = "dd/MM/yyy", shape = JsonFormat.Shape.STRING)
        @Future LocalDate dataValidade
) {

    public Cupom toModel() {
        return new Cupom(codigo, percentualDesconto, dataValidade);
    }
}
