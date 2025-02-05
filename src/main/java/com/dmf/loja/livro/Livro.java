package com.dmf.loja.livro;

import com.dmf.loja.autor.Autor;
import com.dmf.loja.categoria.Categoria;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Livro {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private final String titulo;

    @Column(nullable = false)
    @NotBlank
    @Size(max = 500)
    private final String resumo;

    @Size(max = 4000)
    private final String sumario;

    @Column(nullable = false)
    @NotNull
    @Min(20)
    private final BigDecimal preco;

    @Column(nullable = false)
    @NotNull
    @Min(100)
    private final Integer numeroPaginas;

    @Column(nullable = false)
    @NotBlank
    private final String isbn;

    @Column(nullable = false)
    @NotNull
    @Future
    private final LocalDate dataPublicacao;

    // Lado proprietário: o Livro contém a chave estrangeira do Autor.
    @NotNull
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private final Categoria categoria;

    // Lado proprietário: o Livro contém a chave estrangeira da Categoria.
    @NotNull
    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private final Autor autor;

    public Livro(
            final String titulo,
            final String resumo,
            final String sumario,
            final BigDecimal preco,
            final Integer numeroPaginas,
            final String isbn,
            final LocalDate dataPublicacao,
            final Categoria categoria,
            final Autor autor
    ) {
        // Verificações de invariantes utilizando Assert do Spring
        Assert.hasText(titulo, "O título não pode ser vazio ou nulo");
        Assert.hasText(resumo, "O resumo não pode ser vazio ou nulo");
        Assert.isTrue(preco.compareTo(BigDecimal.valueOf(20)) >= 0, "O preço deve ser maior ou igual a 20");
        Assert.isTrue(numeroPaginas >= 100, "O número de páginas deve ser maior ou igual a 100");
        Assert.hasText(isbn, "O ISBN não pode ser vazio ou nulo");
        Assert.notNull(dataPublicacao, "A data de publicação não pode ser nula");
        Assert.isTrue(dataPublicacao.isAfter(LocalDate.now()), "A data de publicação deve ser no futuro");
        Assert.notNull(categoria, "A categoria não pode ser nula");
        Assert.notNull(autor, "O autor  não pode ser nulo");

        this.titulo = titulo;
        this.resumo = resumo;
        this.sumario = sumario;
        this.preco = preco;
        this.numeroPaginas = numeroPaginas;
        this.isbn = isbn;
        this.dataPublicacao = dataPublicacao;
        this.categoria = categoria;
        this.autor = autor;
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
                ", categoria=" + categoria +
                ", autor=" + autor +
                '}';
    }
}
