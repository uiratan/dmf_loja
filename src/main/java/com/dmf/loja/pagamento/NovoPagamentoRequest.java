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

    public NovoPagamentoRequest registraComprador(final EntityManager entityManager) {
        Pais paisDoPagamento = recuperaPais(entityManager);
        Estado estadoDoPagamento = validaEstado(paisDoPagamento, entityManager);

        return new NovoPagamentoRequest(
                this.nome,
                this.email,
                this.sobrenome,
                this.documento,
                this.endereco,
                this.complemento,
                this.cidade,
                this.idPais,
                this.idEstado,
                this.telefone,
                this.cep
        );

    }

    private Estado validaEstado(Pais pais, EntityManager entityManager) {
        if (pais.hasEstados() && idEstado == null) {
            throw new IllegalArgumentException(pais.getNome() + " possui estados cadastrados. Um estado deve ser informado.");
        }

        return idEstado != null ? recuperaEstado(entityManager, pais) : null;
    }

    private Estado recuperaEstado(EntityManager entityManager, Pais pais) {
        Estado estado = entityManager.find(Estado.class, idEstado);
        Assert.state(estado != null, "Você está querendo cadastrar um pagamento com um Estado que não existe no banco");

        if (!estado.pertenceAoPais(pais)) {
            throw new IllegalArgumentException(estado.getNome() + " não pertence ao " + pais.getNome() + ".");
        }

        return estado;
    }

    private Pais recuperaPais(EntityManager entityManager) {
        Pais pais = entityManager.find(Pais.class, idPais);
        Assert.state(pais != null, "Você está querendo cadastrar um pagamento com um País que não existe no banco");
        return pais;
    }

}


