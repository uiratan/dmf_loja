package com.dmf.loja.categoria;

import com.dmf.loja.validation.campounicoannotation.CampoUnico;
import jakarta.validation.constraints.NotBlank;

public record NovaCategoriaRequest(
        @NotBlank
        @CampoUnico(fieldName = "nome", domainClass = Categoria.class)
        String nome
) {
    public Categoria toModel() {
        return new Categoria(this.nome);
    }
}
