package com.dmf.loja.autor;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.jqwik.api.*;
import net.jqwik.api.constraints.AlphaChars;
import net.jqwik.api.constraints.StringLength;
import net.jqwik.spring.JqwikSpringSupport;
import org.junit.jupiter.api.Assumptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@JqwikSpringSupport
@SpringBootTest
@AutoConfigureMockMvc
class AutoresControllerTest {

    @Autowired
    private MockMvc mvc;
    private static Set<String> emailsGerados = new HashSet<>();

    @Property(tries = 10)
    @Label("fluxo de cadastro de novo autor")
    void teste(@ForAll @AlphaChars @StringLength(min = 1, max = 255) String nome,
               @ForAll("emails") String email,
               @ForAll @AlphaChars @StringLength(min = 1, max = 255) String descricao) throws Exception {

        String payload = new ObjectMapper()
                .writeValueAsString(
                        Map.of("nome",nome,
                                "email", email,
                                "descricao",descricao));

        MockHttpServletRequestBuilder conteudo = MockMvcRequestBuilders.post("/autores")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(payload);

        mvc.perform(conteudo)
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        mvc.perform(conteudo)
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Provide("emails")
    Arbitrary<String> emails() {
        Arbitrary<String> nomes = Arbitraries.strings()
                .withCharRange('a', 'z')
                .numeric() // Permite números
                .ofMinLength(1)
                .ofMaxLength(50);

        Arbitrary<String> dominios = Arbitraries.of("gmail.com", "outlook.com", "yahoo.com", "example.com", "hotmail.com", "icloud.com");

        Arbitrary<String> emails = Combinators.combine(nomes, dominios)
                .as((nome, dominio) -> nome + "@" + dominio);

        return emails.filter(email -> emailsGerados.add(email));
    }
}