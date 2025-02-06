package com.dmf.loja.pagamento;


import com.dmf.loja.paisestado.Estado;
import com.dmf.loja.paisestado.Pais;
import com.dmf.loja.validation.existeid.ExisteId;
import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.util.Assert;

public record NovoPagamentoRequest(
        @NotBlank @Email String email,
        @NotBlank String nome,
        @NotBlank String sobrenome,
        @NotBlank String documento, //precisa ser um cpf ou cnpj
        @NotBlank String endereco,
        @NotBlank String complemento,
        @NotBlank String cidade,

        @ExisteId(fieldName = "id", domainClass = Estado.class)
        @NotNull Long idPais,

        Long idEstado,

        @NotBlank String telefone,
        @NotBlank String cep
) {


}


