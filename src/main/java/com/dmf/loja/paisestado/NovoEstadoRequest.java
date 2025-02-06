package com.dmf.loja.paisestado;

import com.dmf.loja.validation.campounicoannotation.CampoUnico;
import com.dmf.loja.validation.existeid.ExisteId;
import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.util.Assert;

public record NovoEstadoRequest(
        @CampoUnico(fieldName = "nome", domainClass = Estado.class)
        @NotBlank String nome,

        @ExisteId(fieldName = "id", domainClass = Pais.class)
        @NotNull Long idPais
) {
    Estado toModel(EntityManager entityManager) {
        Pais pais = entityManager.find(Pais.class, idPais);
        Assert.state(pais != null, "Você está querendo cadastrar um Estado com um País que não existe no banco");

        return new Estado(
                this.nome(),
                pais
        );
    }
}
