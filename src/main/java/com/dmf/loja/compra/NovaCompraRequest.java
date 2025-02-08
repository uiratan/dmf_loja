package com.dmf.loja.compra;


import com.dmf.loja.cupom.Cupom;
import com.dmf.loja.cupom.CupomRepository;
import com.dmf.loja.paisestado.Estado;
import com.dmf.loja.paisestado.Pais;
import com.dmf.loja.validation.annotations.documento.CPFCNPJ;
import com.dmf.loja.validation.annotations.existeid.ExisteNoBanco;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.function.Function;

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
        @ExisteNoBanco(domainClass = Pais.class)
        @NotNull Long idPais,
        @ExisteNoBanco(fieldName = "id", domainClass = Estado.class)
        Long idEstado,
        @NotBlank String telefone,
        @NotBlank String cep,
        @NotNull @Valid PedidoRequest pedido,
        String codigoCupom
) {

    public Compra toModel(final EntityManager entityManager, final CupomRepository cupomRepository) {
        //1
        Pais pais = entityManager.find(Pais.class, idPais);

        Function<Compra, Pedido> funcaoCriacaoPedido = pedido.toModel(entityManager);

        //1
        Compra novaCompra = new Compra(
                this.nome,
                this.email,
                this.sobrenome,
                this.documento,
                this.endereco,
                this.complemento,
                this.cidade,
                pais,
                this.telefone,
                this.cep,
                funcaoCriacaoPedido
        );

        //1
        // informações não obrigatórias não entram pelo construtor
        if (idEstado != null) {
            novaCompra.setEstado(entityManager.find(Estado.class, idEstado));
        }

        if (isCodigoCupomInformado()) {
            Cupom cupomEncontrado = cupomRepository.findByCodigoAndDataValidadeAfter(this.codigoCupom.toLowerCase(), LocalDate.now())
                    .orElseThrow(() -> new IllegalArgumentException("cupom inválido"));
            novaCompra.setCupom(cupomEncontrado);
        }

        return novaCompra;

    }

    public boolean isCodigoCupomInformado() {
        return this.codigoCupom != null && !this.codigoCupom.isEmpty();
    }

    public boolean isIdEstadoInformado() {
        return idEstado() != null;
    }


}


