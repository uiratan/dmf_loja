package com.dmf.loja.compra;

import com.dmf.loja.compartilhado.CustomMockMvc;
import net.jqwik.api.ForAll;
import net.jqwik.api.Label;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.AlphaChars;
import net.jqwik.api.constraints.IntRange;
import net.jqwik.api.constraints.NumericChars;
import net.jqwik.api.constraints.StringLength;
import net.jqwik.spring.JqwikSpringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@JqwikSpringSupport
@SpringBootTest
@AutoConfigureMockMvc
class ComprasControllerTest {

    @Autowired
    private CustomMockMvc mvc;

    @Property(tries = 10)
    @Label("fluxo de cadastro de novo estado")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void teste(
            @ForAll @AlphaChars @StringLength(min = 1, max = 50) String email,
            @ForAll @AlphaChars @StringLength(min = 1, max = 255) String nome,
            @ForAll @AlphaChars @StringLength(min = 1, max = 255) String sobrenome,
            @ForAll @AlphaChars @StringLength(min = 1, max = 255) String endereco,
            @ForAll @AlphaChars @StringLength(min = 1, max = 255) String complemento,
            @ForAll @AlphaChars @StringLength(min = 1, max = 255) String cidade,
            @ForAll @NumericChars @StringLength(min = 1, max = 20) String telefone,
            @ForAll @NumericChars @StringLength(min = 1, max = 20) String cep,
            @ForAll @IntRange(min = 1, max = 50) int quantidade
    ) throws Exception {

        mvc.post("/autores", Map.of("nome","uiratan","email","email@email.com","descricao","teste"));
        mvc.post("/categorias", Map.of("nome","teste"));
        BigDecimal valorLivro = new BigDecimal("20");
        mvc.post("/livros",
                Map.of("titulo","titulo",
                "resumo","resumo",
                "sumario","sumario",
                "preco", valorLivro.toString(),
                "numeroPaginas", 100,
                "isbn", "123456789",
                "dataPublicacao", LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                "idCategoria","1",
                "idAutor","1"));
        mvc.post("/paises", Map.of("nome", "pais"));
        mvc.post("/estados", Map.of("nome", "estadoDoPais", "idPais", "1"));

        HashMap<String, Object> dados = new HashMap<>();
        dados.put("email", email + "@gmail.com");
        dados.put("nome", nome);
        dados.put("sobrenome", sobrenome);
        dados.put("documento", "789456321");
        dados.put("endereco", endereco);
        dados.put("complemento", complemento);
        dados.put("cidade", cidade);
        dados.put("idPais", "1");
        dados.put("idEstado", "1");
        dados.put("telefone", telefone);
        dados.put("cep", cep);
        List<Map<String, Object>> itens = List.of(Map.of(
                "idLivro", "1",
                "quantidade", quantidade
        ));
        Map<String, Object> pedido = Map.of(
                "total", valorLivro.multiply(new BigDecimal(quantidade)),
                "itens", itens);

        dados.put("pedido", pedido);

        mvc.post("/compras", dados).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

    }
}