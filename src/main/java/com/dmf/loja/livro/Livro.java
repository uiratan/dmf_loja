package com.dmf.loja.livro;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Livro {

    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false)
    @NotBlank String titulo;

    @Column(nullable = false)
    @NotBlank @Size(max = 500) String resumo;

    @Size(max = 4000)
    String sumario;

    @Column(nullable = false)
    @NotNull @Min(20) BigDecimal preco;

    @Column(nullable = false)
    @NotNull @Min(100) Integer numeroPaginas;

    @Column(nullable = false)
    @NotBlank String isbn;

    @Column(nullable = false)
    @NotNull @Future LocalDate dataPublicacao;

    @Column(nullable = false)
    @NotNull Integer categoriaId;

    @Column(nullable = false)
    @NotNull Integer autorId;

    public Livro(
            final String titulo,
            final String resumo,
            final String sumario,
            final BigDecimal preco,
            final Integer numeroPaginas,
            final String isbn,
            final LocalDate dataPublicacao,
            final Integer categoriaId,
            final Integer autorId) {

        // Verificações de invariantes utilizando Assert do Spring
        Assert.hasText(titulo, "O título não pode ser vazio ou nulo");
        Assert.hasText(resumo, "O resumo não pode ser vazio ou nulo");
        Assert.isTrue(preco.compareTo(BigDecimal.valueOf(20)) >= 0, "O preço deve ser maior ou igual a 20");
        Assert.isTrue(numeroPaginas >= 100, "O número de páginas deve ser maior ou igual a 100");
        Assert.hasText(isbn, "O ISBN não pode ser vazio ou nulo");
        Assert.notNull(dataPublicacao, "A data de publicação não pode ser nula");
        Assert.isTrue(dataPublicacao.isAfter(LocalDate.now()), "A data de publicação deve ser no futuro");
        Assert.notNull(categoriaId, "A categoria ID não pode ser nula");
        Assert.notNull(autorId, "O autor ID não pode ser nulo");

        this.titulo = titulo;
        this.resumo = resumo;
        this.sumario = sumario;
        this.preco = preco;
        this.numeroPaginas = numeroPaginas;
        this.isbn = isbn;
        this.dataPublicacao = dataPublicacao;
        this.categoriaId = categoriaId;
        this.autorId = autorId;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", resumo='" + resumo + '\'' +
                ", sumario='" + sumario + '\'' +
                ", preco=" + preco +
                ", numeroPaginas=" + numeroPaginas +
                ", isbn='" + isbn + '\'' +
                ", dataPublicacao=" + dataPublicacao +
                ", categoriaId=" + categoriaId +
                ", autorId=" + autorId +
                '}';
    }
}
