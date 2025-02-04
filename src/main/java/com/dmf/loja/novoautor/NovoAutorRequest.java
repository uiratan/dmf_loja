package com.dmf.loja.novoautor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NovoAutorRequest(
        @NotBlank
        String nome,

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(max = 400)
        String descricao
) {
    public Autor toModel() {
        return new Autor(this.nome, this.email, this.descricao);
    }
}
