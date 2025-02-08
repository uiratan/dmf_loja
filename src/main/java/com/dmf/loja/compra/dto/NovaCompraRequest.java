package com.dmf.loja.compra.dto;


import com.dmf.loja.compra.entidades.Compra;
import com.dmf.loja.compra.entidades.ItemPedido;
import com.dmf.loja.compra.entidades.Pedido;
import com.dmf.loja.paisestado.Estado;
import com.dmf.loja.paisestado.Pais;
import com.dmf.loja.validation.documento.CPFCNPJ;
import com.dmf.loja.validation.existeid.ExisteId;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

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
        @NotBlank String cep,
        @NotNull @Valid PedidoRequest pedido
) {

    public Compra toModel(final EntityManager entityManager) {
        //1
        Pais pais = entityManager.find(Pais.class, idPais);

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
                this.cep
        );

        //1
        if (idEstado != null) {
            novaCompra.setEstado(entityManager.find(Estado.class, idEstado));
        }

        Pedido novoPedido = new Pedido(pedido.total(), novaCompra);

        List<ItemPedido> itens = this.pedido().itens().stream()
                .map(item -> new ItemPedido(item.idLivro(), item.quantidade(), novoPedido))
                .toList();
        novoPedido.setItens(itens);
        novaCompra.setPedido(novoPedido);

        return novaCompra;

    }

    public boolean temEstado() {
        return idEstado() != null;
    }

}


