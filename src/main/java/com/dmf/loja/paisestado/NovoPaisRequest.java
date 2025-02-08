package com.dmf.loja.paisestado;

import com.dmf.loja.validation.annotations.campounico.CampoUnico;
import jakarta.validation.constraints.NotBlank;

//2
public record NovoPaisRequest(
        //1
        @CampoUnico(domainClass = Pais.class, fieldName = "nome")
        @NotBlank String nome
) {
    //1
    public Pais toModel() {
        return new Pais(this.nome);
    }
}
