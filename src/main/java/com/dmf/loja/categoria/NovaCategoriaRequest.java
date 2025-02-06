package com.dmf.loja.categoria;

import com.dmf.loja.validation.campounicoannotation.CampoUnico;
import jakarta.validation.constraints.NotBlank;

//2
public record NovaCategoriaRequest(
        //1
        @NotBlank
        @CampoUnico(fieldName = "nome", domainClass = Categoria.class)
        String nome
) {
    //1
    public Categoria toModel() {
        return new Categoria(this.nome);
    }
}
