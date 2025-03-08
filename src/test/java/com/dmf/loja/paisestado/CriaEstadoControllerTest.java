package com.dmf.loja.paisestado;

import com.dmf.loja.compartilhado.CustomMockMvc;
import net.jqwik.api.ForAll;
import net.jqwik.api.Label;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.AlphaChars;
import net.jqwik.api.constraints.StringLength;
import net.jqwik.spring.JqwikSpringSupport;
import org.junit.jupiter.api.Assumptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@JqwikSpringSupport
@SpringBootTest
@AutoConfigureMockMvc
class CriaEstadoControllerTest {

    @Autowired
    private CustomMockMvc mvc;
    private static Set<String> unicos = new HashSet<>();

    @Property(tries = 10)
    @Label("fluxo de cadastro de novo estado")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void teste(@ForAll @AlphaChars @StringLength(min = 1, max = 255) String nome) throws Exception {
        Assumptions.assumeTrue(unicos.add(nome));

        mvc.post("/paises", Map.of("nome", "pais"));

        // 1a vez é 200
        mvc.post("/estados", Map.of("nome", nome, "idPais", "1")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        // 2a vez é 400
        mvc.post("/estados", Map.of("nome", nome, "idPais", "1")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

}