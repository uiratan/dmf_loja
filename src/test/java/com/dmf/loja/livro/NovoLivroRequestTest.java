package com.dmf.loja.livro;

import com.dmf.loja.autor.Autor;
import com.dmf.loja.categoria.Categoria;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;

class NovoLivroRequestTest {

    private EntityManager entityManager;

    private LocalDate dataPublicacao;
    private Categoria categoria;
    private Autor autor;
    private NovoLivroRequest request;

    @BeforeEach
    void setUp() {
        entityManager = Mockito.mock(EntityManager.class);

        dataPublicacao = LocalDate.now().plusDays(1);
        categoria = new Categoria("Ficção");
        autor = new Autor("Nome do Autor", "autor@email.com", "Biografia");

        request = new NovoLivroRequest(
                "Título do Livro",
                "Resumo do Livro",
                "Sumário do Livro",
                BigDecimal.valueOf(50.00),
                200,
                "123-456-789",
                dataPublicacao,
                1L,
                1L
        );
    }

    @Test
    @DisplayName("cria livro se categoria e autor cadastrados")
    void deveCriarLivroComDadosValidos() {
        // Arrange (Preparação dos dados)
        Mockito.when(entityManager.find(Categoria.class, 1L)).thenReturn(categoria);
        Mockito.when(entityManager.find(Autor.class, 1L)).thenReturn(autor);

        // Act (Chamar o metodo a ser testado)
        Livro livro = request.toModel(entityManager);

        // Assert (Verificar resultados)
        Assertions.assertNotNull(livro);
        Assertions.assertEquals("Título do Livro", livro.getTitulo());
        Assertions.assertEquals("Resumo do Livro", livro.getResumo());
        Assertions.assertEquals("Sumário do Livro", livro.getSumario());
        Assertions.assertEquals(BigDecimal.valueOf(50.00), livro.getPreco());
        Assertions.assertEquals(200, livro.getNumeroPaginas());
        Assertions.assertEquals("123-456-789", livro.getIsbn());
        Assertions.assertEquals(dataPublicacao, livro.getDataPublicacao());
        Assertions.assertEquals(categoria, livro.getCategoria());
        Assertions.assertEquals(autor, livro.getAutor());

        // Verificar se as buscas foram feitas corretamente no EntityManager
        Mockito.verify(entityManager, Mockito.times(1)).find(Categoria.class, 1L);
        Mockito.verify(entityManager, Mockito.times(1)).find(Autor.class, 1L);
    }

    @Test
    @DisplayName("nao cria o livro caso autor não exista no banco")
    void deveLancarExcecaoQuandoAutorNaoExiste() {
        // Arrange
        Mockito.when(entityManager.find(Categoria.class, 1L)).thenReturn(categoria);
        Mockito.when(entityManager.find(Autor.class, 1L)).thenReturn(null);

        // Act & Assert
        IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class, () -> {
            request.toModel(entityManager);
        });

        Assertions.assertEquals("Você está querendo cadastrar um livro com um autor " +
                "que não existe no banco", exception.getMessage());
    }

    @Test
    @DisplayName("nao cria o livro caso a categoria não exista no banco")
    void deveLancarExcecaoQuandoCategoriaNaoExiste() {
        // Arrange
        Mockito.when(entityManager.find(Categoria.class, 1L)).thenReturn(null);
        Mockito.when(entityManager.find(Autor.class, 1L)).thenReturn(autor);

        // Act & Assert
        IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class, () -> {
            request.toModel(entityManager);
        });

        Assertions.assertEquals("Você está querendo cadastrar um livro com uma categoria " +
                "que não existe no banco", exception.getMessage());
    }

}