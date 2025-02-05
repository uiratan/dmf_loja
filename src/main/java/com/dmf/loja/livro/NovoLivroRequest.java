package com.dmf.loja.livro;

import com.dmf.loja.autor.Autor;
import com.dmf.loja.categoria.Categoria;
import com.dmf.loja.validation.campounicoannotation.CampoUnico;
import com.dmf.loja.validation.existeid.ExisteId;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.*;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDate;

public record NovoLivroRequest(
        @CampoUnico(fieldName = "titulo", domainClass = Livro.class)
        @NotBlank String titulo,

        @NotBlank @Size(max = 500) String resumo,

        @NotBlank String sumario,

        @NotNull @Min(20) BigDecimal preco,

        @NotNull @Min(100) Integer numeroPaginas,

        @CampoUnico(fieldName = "isbn", domainClass = Livro.class)
        @NotBlank String isbn,

        @JsonFormat(pattern = "dd/MM/yyy", shape = JsonFormat.Shape.STRING)
        @NotNull @Future LocalDate dataPublicacao,

        @ExisteId(fieldName = "id", domainClass = Categoria.class)
        @NotNull Long idCategoria,

        @ExisteId(fieldName = "id", domainClass = Autor.class)
        @NotNull Long idAutor
) {
    public Livro toModel(EntityManager entityManager) {

        Categoria categoria = entityManager.find(Categoria.class, idCategoria);
        Autor autor = entityManager.find(Autor.class, idAutor);

        Assert.state(categoria!= null, "Você está querendo cadastrar um livro com uma categoria que não existe no banco");
        Assert.state(autor!= null, "Você está querendo cadastrar um livro com um autor que não existe no banco");

        return new Livro(
                this.titulo,
                this.resumo,
                this.sumario,
                this.preco,
                this.numeroPaginas,
                this.isbn,
                this.dataPublicacao,
                categoria,
                autor
        );
    }
}
