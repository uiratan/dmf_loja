package com.dmf.loja.livro;

import com.dmf.loja.validation.campounicoannotation.CampoUnico;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record NovoLivroRequest(
        @CampoUnico(fieldName = "titulo", domainClass = Livro.class)
        @NotBlank String titulo,

        @NotBlank @Size(max = 500) String resumo,

        String sumario,

        @NotNull @Min(20) BigDecimal preco,

        @NotNull @Min(100) Integer numeroPaginas,

        @CampoUnico(fieldName = "isbn", domainClass = Livro.class)
        @NotBlank String isbn,

        @NotNull @Future LocalDate dataPublicacao,

        @NotNull Integer categoriaId,

        @NotNull Integer autorId
) {
    public Livro toModel() {
        return new Livro(
                this.titulo,
                this.resumo,
                this.sumario,
                this.preco,
                this.numeroPaginas,
                this.isbn,
                this.dataPublicacao,
                this.categoriaId,
                this.autorId
        );
    };
}
