package com.dmf.loja.livro;

import com.dmf.loja.compartilhado.CustomMockMvc;
import net.jqwik.api.*;
import net.jqwik.api.constraints.*;
import net.jqwik.spring.JqwikSpringSupport;
import net.jqwik.time.api.Dates;
import org.junit.jupiter.api.Assumptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@JqwikSpringSupport
@SpringBootTest
@AutoConfigureMockMvc
public class LivrosControllerTest {

    @Autowired
    private CustomMockMvc mvc;
    private Set<String> unicos = new HashSet<>();

    @Property(tries = 20)
    @Label("fluxo de cadastro de novo livro")
    @DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
    public void teste(@ForAll @AlphaChars @StringLength(min = 1, max = 255) String titulo,
                      @ForAll @AlphaChars @StringLength(min = 1, max = 500) String resumo,
                      @ForAll @AlphaChars @StringLength(min = 1, max = 255) String sumario,
                      @ForAll @BigRange(min = "20",max = "100") BigDecimal preco,
                      @ForAll @IntRange(min = 100) int paginas,
                      @ForAll @NumericChars @StringLength(min = 10, max = 10) String isbn,
                      @ForAll("datasFuturas") LocalDate dataPublicacao) throws Exception {

        Assumptions.assumeTrue(unicos.add(titulo));
        Assumptions.assumeTrue(unicos.add(isbn));

        //vai salvar a primeira vez e deposi vai bloquear por conta da validacao de unicidade
        mvc.post("/autores", Map.of("nome","alberto","email","email@email.com","descricao","teste"));
        mvc.post("/categorias", Map.of("nome","teste"));

        String dataPublicacaoFormatada = dataPublicacao
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));


        mvc.post("/livros",Map.of("titulo",titulo,
                        "resumo",resumo,
                        "sumario",sumario,
                        "preco",preco.toString(),
                        "numeroPaginas",String.valueOf(paginas),
                        "isbn",isbn,
                        "dataPublicacao",dataPublicacaoFormatada,
                        "idCategoria","1",
                        "idAutor","1"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        mvc.post("/livros",Map.of("titulo",titulo,
                        "resumo",resumo,
                        "sumario",sumario,
                        "preco",preco.toString(),
                        "numeroPaginas",String.valueOf(paginas),
                        "isbn",isbn,
                        "dataPublicacao",dataPublicacaoFormatada,
                        "idCategoria","1",
                        "idAutor","1"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Provide
    Arbitrary<LocalDate> datasFuturas() {
        return Dates.dates().atTheEarliest(LocalDate.now().plusDays(1));
    }

}