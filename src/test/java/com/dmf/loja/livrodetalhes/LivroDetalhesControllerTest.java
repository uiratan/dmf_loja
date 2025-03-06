package com.dmf.loja.livrodetalhes;

import com.dmf.loja.compartilhado.CustomMockMvc;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
class LivroDetalhesControllerTest {

    @Autowired
    private CustomMockMvc mvc;

    @Test
    @DisplayName("exibe detalhes do livro corretamente")
    public void teste1() throws Exception {
        mvc.post("/autores", Map.of(
                "nome", "uiratan",
                "email", "test@gmail.com",
                "descricao", "descricao"));

        mvc.post("/categorias", Map.of("nome", "categoria"));

        BigDecimal valorLivro = new BigDecimal("20");
        String titulo = "titulo";
        String resumo = "resumo";
        String sumario = "sumario";
        int numeroPaginas = 100;
        String isbn = "123456789";
        String dataPublicacao = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        mvc.post("/livros", Map.of(
                "titulo", titulo,
                "resumo", resumo,
                "sumario", sumario,
                "preco", valorLivro.toString(),
                "numeroPaginas", numeroPaginas,
                "isbn", isbn,
                "dataPublicacao", dataPublicacao,
                "idCategoria", "1",
                "idAutor", "1"
        ));

        ResultActions resultado = mvc.get("/produtos/1");

        Map<String, Object> autor = Map.of(
                "nome", "uiratan",
                "descricao", "descricao");

        Map<String, Object> detalheLivro = Map.of(
                "titulo", titulo,
                "resumo", resumo,
                "sumario", sumario,
                "preco", valorLivro.setScale(2),
                "numeroPaginas", numeroPaginas,
                "isbn", isbn,
                "dataPublicacao", dataPublicacao,
                "autor", autor
        );

        String jsonEsperado = new ObjectMapper().writeValueAsString(detalheLivro);

        resultado.andExpect(MockMvcResultMatchers.content().json(jsonEsperado));
    }

}

