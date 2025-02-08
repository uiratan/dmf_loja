package com.dmf.loja.autor;

import com.dmf.loja.validation.annotations.campounico.CampoUnico;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

//2
public record NovoAutorRequest(
        @NotBlank
        String nome,

        @NotBlank
        @Email
        //1
        @CampoUnico(fieldName = "email", domainClass = Autor.class)
        String email,

        @NotBlank
        @Size(max = 400)
        String descricao
) {
    //1
    public Autor toModel() {
        return new Autor(this.nome, this.email, this.descricao);
    }
}
