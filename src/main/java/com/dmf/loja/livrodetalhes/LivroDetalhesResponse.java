package com.dmf.loja.livrodetalhes;

import com.dmf.loja.livro.Livro;

import java.math.BigDecimal;

//2
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
    //1
    public static LivroDetalhesResponse fromModel(Livro livro) {
        return new LivroDetalhesResponse(
                livro.getTitulo(),
                livro.getResumo(),
                livro.getSumario(),
                livro.getPreco(),
                livro.getNumeroPaginas(),
                livro.getIsbn(),
                livro.getDataPublicacaoComFormato("dd/MM/yyyy"),
                //1
                new AutorLivroDetalhes(livro.getAutor().getNome(), livro.getAutor().getDescricao())
        );
    }
}
