package com.dmf.loja;

import com.dmf.loja.autor.Autor;
import com.dmf.loja.categoria.Categoria;
import com.dmf.loja.cupom.Cupom;
import com.dmf.loja.livro.Livro;
import com.dmf.loja.paisestado.Estado;
import com.dmf.loja.paisestado.Pais;
import jakarta.persistence.EntityManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class LojaApplication {

    public static void main(String[] args) {
        SpringApplication.run(LojaApplication.class, args);
    }

    @Component
    public static class DataLoader implements CommandLineRunner {
        private final EntityManager entityManager;

        public DataLoader(EntityManager entityManager) {
            this.entityManager = entityManager;
        }

        @Override
        @Transactional
        public void run(String... args) {
            carregarCategorias();
            carregarAutores();
            carregarLivros();
            carregarPaises();
            carregarEstados();
            carregaCupons();
        }

        private void carregarCategorias() {
            List<String> categoriasIniciais = List.of("DDD", "Arquitetura de Software", "Java");
            for (String nome : categoriasIniciais) {
                entityManager.persist(new Categoria(nome));
            }
        }

        private void carregarAutores() {
            List<Autor> autoresIniciais = List.of(
                    new Autor("Erick Evans", "evans@ddd.com", "O cara que definiu o DDD."),
                    new Autor("Martin Fowler", "fowler@thoughworks.com", "O melhor autor sobre padrões de projetos corporativos."),
                    new Autor("Uncle Bob", "bob@clean.com", "Nosso tiozão.")
            );
            for (Autor autor : autoresIniciais) {
                entityManager.persist(autor);
            }
        }

        private void carregarLivros() {
            // Criando Livro 1
            Livro livro1 = new Livro(
                    "Domain-Driven Design: Tackling Complexity in the Heart of Software",
                    "Software design thought leader and founder of Domain Language, Eric Evans, " +
                            "provides a systematic approach to domain-driven design, presenting an extensive " +
                            "set of design best practices, experience-based techniques, and fundamental principles " +
                            "that facilitate the development of software projects facing complex domains.",
                    "Sumário do livro DDD...",
                    new BigDecimal("100"),
                    529,
                    "978-0321125217",
                    LocalDate.now().plusDays(30), // Data futura
                    entityManager.find(Categoria.class, 1),
                    entityManager.find(Autor.class, 1)
            );

            // Criando Livro 2
            Livro livro2 = new Livro(
                    "Patterns of Enterprise Application Architecture",
                    "Patterns of Enterprise Application Architecture is written in direct response to the stiff challenges that face enterprise application developers.",
                    "Sumário de PoEAA...",
                    new BigDecimal("50"),
                    560,
                    "978-0321127426",
                    LocalDate.now().plusDays(60), // Data futura
                    entityManager.find(Categoria.class, 2),
                    entityManager.find(Autor.class, 2)
            );

            entityManager.persist(livro1);
            entityManager.persist(livro2);
        }

        private void carregarPaises() {
            List<String> paisesIniciais = List.of("Brasil", "Estados Unidos", "Argentina");
            for (String nome : paisesIniciais) {
                entityManager.persist(new Pais(nome));
            }
        }

        private void carregarEstados() {
            final var brasil = entityManager.find(Pais.class, 1);
            final var eua = entityManager.find(Pais.class, 2);

            List<Estado> estadosIniciais = List.of(
                    new Estado("Piaui", brasil),
                    new Estado("Ceará", brasil),
                    new Estado("Texas", eua)
            );
            for (Estado estado : estadosIniciais) {
                entityManager.persist(estado);
            }
        }

        private void carregaCupons() {
            LocalDate dataValidadeFutura = LocalDate.now().plusDays(10);
            LocalDate dataValidadeAntiga = LocalDate.now().minusDays(10);

            List<Cupom> cupons = List.of(
                    new Cupom("10agora", BigDecimal.TEN, dataValidadeFutura),
                    new Cupom("vintao", BigDecimal.valueOf(20), dataValidadeFutura)
//                    new Cupom("ANTIGO", BigDecimal.valueOf(20), dataValidadeAntiga)
            );

            for (Cupom cupom : cupons) {
                entityManager.persist(cupom);
            }

            System.out.println(cupons);

        }
    }

}
