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
        @ExisteNoBanco(fieldName = "codigo", domainClass = Cupom.class)
        @NotBlank String cupom
) {

    public Compra toModel(final EntityManager entityManager, final CupomRepository cupomRepository) {
        //1
        Pais pais = entityManager.find(Pais.class, idPais);

        Function<Compra, Pedido> funcaoCriacaoPedido = pedido.toModel(entityManager);

        /**
         o código do cupom precisa ser válido
         o cupom precisa ser válido ainda
         uma vez associado o cupom, uma compra nunca pode ter essa informação alterada.
         O cupom só pode ser associada com uma compra que ainda não foi registrada no banco de dados (esse daqui eu não implementei)
         */

//        boolean cupomValido = cupomRepository.existsByCodigoAndDataValidadeAfter(this.cupom.toLowerCase(), LocalDate.now());
////        boolean cupomValido = cupomRepository.isCupomValido("MEUCUPOM", LocalDate.now());
//
//        Cupom cupom = cupomRepository.findByCodigo(this.cupom.toLowerCase())
//                .orElseThrow(() -> new IllegalArgumentException("cupom nao cadastrado"));
//
//        if (cupom.isCupomValido()) {
//
//        }

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
                funcaoCriacaoPedido,
                null
        );

        //1
        if (idEstado != null) {
            novaCompra.setEstado(entityManager.find(Estado.class, idEstado));
        }



        return novaCompra;

    }

    public boolean temEstado() {
        return idEstado() != null;
    }

}


