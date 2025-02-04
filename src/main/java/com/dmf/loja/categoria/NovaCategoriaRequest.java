package com.dmf.loja.categoria;

import jakarta.validation.constraints.NotBlank;

public record NovaCategoriaRequest(
        @NotBlank
        String nome
) {
    public Categoria toModel() {
        return new Categoria(this.nome);
    }
}
