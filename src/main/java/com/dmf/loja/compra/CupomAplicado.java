package com.dmf.loja.compra;

import com.dmf.loja.cupom.Cupom;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

@Embeddable
public class CupomAplicado {

    @ManyToOne
    private Cupom cupom;
    @Positive
    @NotNull
    private BigDecimal percentualDescontoMomento;
    @NotNull
    @Future
    private LocalDate dataValidadeMomento;

    public CupomAplicado(@NotNull @Valid Cupom cupom) {
        this.cupom = cupom;
        this.percentualDescontoMomento = cupom.getPercentualDesconto();
        this.dataValidadeMomento = cupom.getDataValidade();
    }

    @Deprecated
    public CupomAplicado() {
    }

    @Override
    public String toString() {
        return "CupomAplicado{" +
                "cupom=" + cupom +
                ", percentualDescontoMomento=" + percentualDescontoMomento +
                ", dataValidadeMomento=" + dataValidadeMomento +
                '}';
    }
}
