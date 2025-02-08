package com.dmf.loja.cupom;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
public class Cupom {
    @Id @GeneratedValue private Long id;
    private String codigo;
    private BigDecimal percentualDesconto;
    private LocalDate dataValidade;

    public Cupom(
            @NotBlank final String codigo,
            @NotNull @Positive final BigDecimal percentualDesconto,
            @Future final LocalDate dataValidade) {
        this.codigo = codigo;
        this.percentualDesconto = percentualDesconto;
        this.dataValidade = dataValidade;
    }

    public String getDataValidadeComFormato(String formato) {
        return dataValidade.format(DateTimeFormatter.ofPattern(formato));
    }

    @Override
    public String toString() {
        return "Cupom{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", percentualDesconto=" + percentualDesconto +
                ", dataValidade=" + getDataValidadeComFormato("dd/MM/yyyy") +
                '}';
    }
}
