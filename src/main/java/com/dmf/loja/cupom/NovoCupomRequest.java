package com.dmf.loja.cupom;

import com.dmf.loja.validation.annotations.campounico.CampoUnico;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record NovoCupomRequest(

        @CampoUnico(domainClass = Cupom.class, fieldName = "codigo")
        @NotBlank String codigo,
        @NotNull @Positive BigDecimal percentualDesconto,
        @JsonFormat(pattern = "dd/MM/yyy", shape = JsonFormat.Shape.STRING)
        @FutureOrPresent LocalDate dataValidade
) {

    public Cupom toModel() {
        return new Cupom(codigo.toLowerCase(), percentualDesconto, dataValidade);
    }
}
