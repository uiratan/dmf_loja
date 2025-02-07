package com.dmf.loja;

import com.dmf.loja.autor.Autor;
import com.dmf.loja.categoria.Categoria;
import com.dmf.loja.paisestado.Estado;
import com.dmf.loja.paisestado.Pais;
import jakarta.persistence.EntityManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
            carregarPaises();
            carregarEstados();
        }

        private void carregarCategorias() {
            List<String> categoriasIniciais = List.of("Java", "DDD", "Arquitetura de Software");
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
    }

}
