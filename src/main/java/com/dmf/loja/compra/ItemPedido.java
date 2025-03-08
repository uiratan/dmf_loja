package com.dmf.loja.compra;

import com.dmf.loja.config.Generated;
import com.dmf.loja.livro.Livro;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class ItemPedido {

    @ManyToOne
    @NotNull private Livro livro;
    @Positive private Integer quantidade;
    @Positive private BigDecimal precoMomento;

    public ItemPedido(Livro livro, Integer quantidade) {
        Assert.notNull(livro, "o livro não pode ser nulo");
        Assert.isTrue(quantidade > 0, "a quantidade deve ser maior que 0");

        this.livro = livro;
        this.quantidade = quantidade;
        this.precoMomento = livro.getPreco();
    }

    @Deprecated
    public ItemPedido() {
    }

    @Generated(Generated.IDE)
    public Livro getLivro() {
        return livro;
    }

    @Generated(Generated.IDE)
    public Integer getQuantidade() {
        return quantidade;
    }

    @Generated(Generated.IDE)
    public BigDecimal getPrecoMomento() {
        return precoMomento;
    }

    public BigDecimal total() {
        return precoMomento.multiply(BigDecimal.valueOf(quantidade));
    }

    @Override
    @Generated(Generated.IDE)
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ItemPedido that = (ItemPedido) o;
        return Objects.equals(livro, that.livro) && Objects.equals(quantidade, that.quantidade);
    }

    @Override
    @Generated(Generated.IDE)
    public int hashCode() {
        return Objects.hash(livro, quantidade);
    }

    @Override
    @Generated(Generated.IDE)
    public String toString() {
        return "ItemPedido{" +
                "livro=" + livro +
                ", quantidade=" + quantidade +
                ", precoMomento=" + precoMomento +
                '}';
    }
}
