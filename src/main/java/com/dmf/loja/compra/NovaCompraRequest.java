package com.dmf.loja.compra;


import com.dmf.loja.cupom.Cupom;
import com.dmf.loja.cupom.CupomRepository;
import com.dmf.loja.paisestado.Estado;
import com.dmf.loja.paisestado.Pais;
import com.dmf.loja.validation.annotations.documento.Documento;
import com.dmf.loja.validation.annotations.existeid.ExisteNoBanco;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.function.Function;

//8
public record NovaCompraRequest(
        @NotBlank @Email String email,
        @NotBlank String nome,
        @NotBlank String sobrenome,
        @NotBlank @Documento String documento, //precisa ser um cpf ou cnpj
        @NotBlank String endereco,
        @NotBlank String complemento,
        @NotBlank String cidade,
        @ExisteNoBanco(domainClass = Pais.class)
        @NotNull Long idPais,
        @ExisteNoBanco(fieldName = "id", domainClass = Estado.class)
        Long idEstado,
        @NotBlank String telefone,
        @NotBlank String cep,
        //1
        @NotNull @Valid PedidoRequest pedido,
        @ExisteNoBanco(fieldName = "codigo", domainClass = Cupom.class)
        String codigoCupom
) {

    //1
    public Compra toModel(final EntityManager entityManager, final CupomRepository cupomRepository) {
        //1
        Pais pais = entityManager.find(Pais.class, idPais);

        //1
        //1
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
                //1
                funcaoCriacaoPedido
        );

        // informações não obrigatórias não entram pelo construtor
        //1
        if (isIdEstadoInformado()) {
            novaCompra.setEstado(entityManager.find(Estado.class, idEstado));
        }

        //1
        if (isCodigoCupomInformado()) {
            Cupom cupomExistente = cupomRepository.getByCodigo(codigoCupom);
            novaCompra.aplicarCupom(cupomExistente);
        }
        return novaCompra;
    }

    public boolean isCodigoCupomInformado() {
        return StringUtils.hasText(codigoCupom);
    }

    public boolean isIdEstadoInformado() {
//        return idEstado() != null;
        return Optional.ofNullable(idEstado).isPresent();
    }

    // nomenclatura para Optional
    // getByXXX: retorna um objeto
    // findByXX: pode retornar um null ou um objeto

    // é preciso filtrar se o codigo passado nao é nulo para nao dar erro no validador
    public Optional<String> findCodigoCupom() {
        return Optional.ofNullable(codigoCupom)
                .filter(codigo -> !codigo.trim().isEmpty())
                .map(String::toLowerCase);
    }
}


