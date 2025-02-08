package com.dmf.loja.compra;

import com.dmf.loja.livro.Livro;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Objects;

public class ItemPedido {

    @ManyToOne
    @NotNull private final Livro livro;
    @Positive private final Integer quantidade;
    @Positive private final BigDecimal precoMomento;

    public ItemPedido(Livro livro, Integer quantidade) {
        Assert.notNull(livro, "o livro não pode ser nulo");
        Assert.isTrue(quantidade > 0, "a quantidade deve ser maior que 0");

        this.livro = livro;
        this.quantidade = quantidade;
        this.precoMomento = livro.getPreco();
    }

    public BigDecimal total() {
        return precoMomento.multiply(BigDecimal.valueOf(quantidade));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ItemPedido that = (ItemPedido) o;
        return Objects.equals(livro, that.livro) && Objects.equals(quantidade, that.quantidade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(livro, quantidade);
    }

    @Override
    public String toString() {
        return "ItemPedido{" +
                "livro=" + livro +
                ", quantidade=" + quantidade +
                ", precoMomento=" + precoMomento +
                '}';
    }
}
