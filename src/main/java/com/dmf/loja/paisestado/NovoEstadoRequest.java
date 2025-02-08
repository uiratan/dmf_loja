package com.dmf.loja.paisestado;

import com.dmf.loja.validation.annotations.campounico.CampoUnico;
import com.dmf.loja.validation.annotations.existeid.ExisteId;
import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.util.Assert;

//4
public record NovoEstadoRequest(
        //1
        @CampoUnico(fieldName = "nome", domainClass = Estado.class)
        @NotBlank String nome,

        //1
        @ExisteId(domainClass = Pais.class)
        @NotNull Long idPais
) {
    //1
    Estado toModel(EntityManager entityManager) {
        //1
        Pais pais = entityManager.find(Pais.class, idPais);
        Assert.state(pais != null, "Você está querendo cadastrar um Estado com um País que não existe no banco");

        return new Estado(
                this.nome(),
                pais
        );
    }
}
