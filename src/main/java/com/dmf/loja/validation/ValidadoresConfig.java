package com.dmf.loja.validation;

import com.dmf.loja.categoria.CategoriaRepository;
import com.dmf.loja.categoria.NovaCategoriaRequest;
import com.dmf.loja.novoautor.AutorRepository;
import com.dmf.loja.novoautor.NovoAutorRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidadoresConfig {

    @Bean
    public CampoUnicoValidator<NovoAutorRequest> emailUnicoValidator(AutorRepository autorRepository) {
        return new CampoUnicoValidator<>(
                NovoAutorRequest::email,
                autorRepository::existsByEmail,
                "email",
                NovoAutorRequest.class
        );
    }

    @Bean
    public CampoUnicoValidator<NovaCategoriaRequest> nomeUnicoValidator(CategoriaRepository categoriaRepository) {
        return new CampoUnicoValidator<>(
                NovaCategoriaRequest::nome,
                categoriaRepository::existsByNome,
                "nome",
                NovaCategoriaRequest.class
        );
    }
}
