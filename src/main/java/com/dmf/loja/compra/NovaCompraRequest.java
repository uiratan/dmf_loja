package com.dmf.loja.compra;


import com.dmf.loja.paisestado.Estado;
import com.dmf.loja.paisestado.Pais;
import com.dmf.loja.validation.documento.CPFCNPJ;
import com.dmf.loja.validation.existeid.ExisteId;
import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

//6
public record NovaCompraRequest(
        @NotBlank @Email String email,
        @NotBlank String nome,
        @NotBlank String sobrenome,
        //1
        @NotBlank @CPFCNPJ String documento, //precisa ser um cpf ou cnpj
        @NotBlank String endereco,
        @NotBlank String complemento,
        @NotBlank String cidade,
        //1
        @ExisteId(fieldName = "id", domainClass = Pais.class)
        @NotNull Long idPais,
        @ExisteId(fieldName = "id", domainClass = Estado.class)
        Long idEstado,
        @NotBlank String telefone,
        @NotBlank String cep
) {

    public Compra toModel(final EntityManager entityManager) {
        //1
        Pais pais = entityManager.find(Pais.class, idPais);
        //1 //1
        Estado estado = (idEstado != null) ? entityManager.find(Estado.class, idEstado) : null;

        return new Compra(
                this.nome,
                this.email,
                this.sobrenome,
                this.documento,
                this.endereco,
                this.complemento,
                this.cidade,
                pais.getNome(),
                //1
                (estado != null) ? estado.getNome() : null,
                this.telefone,
                this.cep
        );
    }

    public boolean temEstado() {
        return idEstado() != null;
    }

}


