package com.dmf.loja.paisestado;

import com.dmf.loja.validation.campounicoannotation.CampoUnico;
import jakarta.validation.constraints.NotBlank;

public record NovoPaisRequest(
        @CampoUnico(domainClass = Pais.class, fieldName = "nome")
        @NotBlank String nome
) {
    public Pais toModel() {
        return new Pais(this.nome);
    }
}
