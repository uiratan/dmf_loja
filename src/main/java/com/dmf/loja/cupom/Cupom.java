package com.dmf.loja.cupom;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import org.springframework.util.Assert;

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
            @FutureOrPresent final LocalDate dataValidade) {
        Assert.hasText(codigo, "O código não pode ser nulo ou vazio");
        Assert.notNull(percentualDesconto, "O percentual de desconto não pode ser nulo");
        Assert.isTrue(percentualDesconto.compareTo(BigDecimal.ZERO) > 0, "O percentual de desconto deve ser positivo");
        Assert.notNull(dataValidade, "A data de validade não pode ser nula");
        Assert.isTrue(dataValidade.compareTo(LocalDate.now()) >= 0, "A data de validade deve ser futura");

        this.codigo = codigo.toLowerCase();
        this.percentualDesconto = percentualDesconto;
        this.dataValidade = dataValidade;
    }

    @Deprecated
    public Cupom () {}

    public String getDataValidadeComFormato(String formato) {
        return dataValidade.format(DateTimeFormatter.ofPattern(formato));
    }

    public boolean isValido() {
        // se usar isAfter ou isBefere nao considera verdadeiro se a data da compra
        // for a mesma data de expiracao do cupom
        // LocalDate dataEspecifica = LocalDate.of(2025, 2, 9);
        // return this.dataValidade.isAfter(dataEspecifica);
        return LocalDate.now().compareTo(this.dataValidade) <= 0;
    }

    public String getCodigo() {
        return codigo;
    }

    public BigDecimal getPercentualDesconto() {
        return percentualDesconto;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
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
