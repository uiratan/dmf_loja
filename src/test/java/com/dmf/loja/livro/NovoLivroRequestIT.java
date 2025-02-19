package com.dmf.loja.livro;

class NovoLivroRequestIT { }
//import com.dmf.loja.autor.Autor;
//import com.dmf.loja.categoria.Categoria;
//import jakarta.persistence.EntityManager;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@ActiveProfiles("test") // Usa application-test.properties
//@Transactional // Garante rollback após o teste
//class NovoLivroRequestIT {
//
//    @Autowired
//    private EntityManager entityManager;
//
//    @Test
//    @DisplayName("Deve persistir um livro no banco H2 gerenciado pelo Spring")
//    void devePersistirLivro() {
//        Categoria categoria = new Categoria("Ficção");
//        Autor autor = new Autor("Autor Teste", "email@email.com", "Biografia");
//
//        entityManager.persist(categoria);
//        entityManager.persist(autor);
//        entityManager.flush();
//
//        NovoLivroRequest request = new NovoLivroRequest(
//                "Título Teste",
//                "Resumo Teste",
//                "Sumário Teste",
//                BigDecimal.valueOf(50.00),
//                200,
//                "123-456-789",
//                LocalDate.now().plusDays(1),
//                categoria.getId(),
//                autor.getId()
//        );
//
//        Livro livro = request.toModel(entityManager);
//        entityManager.persist(livro);
//        entityManager.flush();
//
//        Livro livroSalvo = entityManager.find(Livro.class, livro.getId());
//        assertNotNull(livroSalvo);
//        assertEquals("Título Teste", livroSalvo.getTitulo());
//    }
//}
