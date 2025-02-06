package com.dmf.loja.livrodetalhes;

import com.dmf.loja.livro.Livro;

import java.math.BigDecimal;

public record LivroDetalhesResponse(
        String titulo,
        String resumo,
        String sumario,
        BigDecimal preco,
        Integer numeroPaginas,
        String isbn,
        String dataPublicacao,
        AutorLivroDetalhes autor
) {
    public static LivroDetalhesResponse fromModel(Livro livro) {
        return new LivroDetalhesResponse(
                livro.getTitulo(),
                livro.getResumo(),
                livro.getSumario(),
                livro.getPreco(),
                livro.getNumeroPaginas(),
                livro.getIsbn(),
                livro.getDataPublicacaoComFormato("dd/MM/yyyy"),
                new AutorLivroDetalhes(livro.getAutor().getNome(), livro.getAutor().getDescricao())
        );
    }
}
