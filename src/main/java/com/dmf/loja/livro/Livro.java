package com.dmf.loja.livro;

import com.dmf.loja.autor.Autor;
import com.dmf.loja.categoria.Categoria;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//2
@Entity
public class Livro {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String titulo;

    @Column(nullable = false)
    @NotBlank
    @Size(max = 500)
    private String resumo;

    @Size(max = 4000)
    private String sumario;

    @Column(nullable = false)
    @NotNull
    @Min(20)
    private BigDecimal preco;

    @Column(nullable = false)
    @NotNull
    @Min(100)
    private Integer numeroPaginas;

    @Column(nullable = false)
    @NotBlank
    private String isbn;

    @Column(nullable = false)
    @NotNull
    @Future
    private LocalDate dataPublicacao;

    //1
    @NotNull
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    //1
    @NotNull
    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;

    public Livro(
            String titulo,
            String resumo,
            String sumario,
            BigDecimal preco,
            Integer numeroPaginas,
            String isbn,
            LocalDate dataPublicacao,
            Categoria categoria,
            Autor autor
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

    @Deprecated
    public Livro() {
    }

    public String getTitulo() {
        return titulo;
    }

    public String getResumo() {
        return resumo;
    }

    public String getSumario() {
        return sumario;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public Integer getNumeroPaginas() {
        return numeroPaginas;
    }

    public String getIsbn() {
        return isbn;
    }

    public LocalDate getDataPublicacao() {
        return dataPublicacao;
    }

    public String getDataPublicacaoComFormato(String formato) {
        return dataPublicacao.format(DateTimeFormatter.ofPattern(formato));
    }

    public Autor getAutor() {
        return autor;
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
