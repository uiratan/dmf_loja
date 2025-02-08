package com.dmf.loja.livro;

import com.dmf.loja.autor.Autor;
import com.dmf.loja.categoria.Categoria;
import com.dmf.loja.validation.annotations.campounico.CampoUnico;
import com.dmf.loja.validation.annotations.existeid.ExisteNoBanco;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.*;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDate;

//4
public record NovoLivroRequest(
        //1
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
        //1
        @ExisteNoBanco(fieldName = "id", domainClass = Categoria.class)
        @NotNull Long idCategoria,
        @ExisteNoBanco(fieldName = "id", domainClass = Autor.class)
        @NotNull Long idAutor
) {
    public Livro toModel(EntityManager entityManager) {

        //1
        Categoria categoria = entityManager.find(Categoria.class, idCategoria);
        //1
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
