package com.dmf.loja.compra;

import com.dmf.loja.cupom.Cupom;
import com.dmf.loja.paisestado.Estado;
import com.dmf.loja.paisestado.Pais;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.util.Assert;

import java.util.function.Function;

@Entity
public class Compra {
    @Id @GeneratedValue private Long id;
    @NotBlank private final String nome;
    @NotBlank @Email private final String email;
    @NotBlank private final String sobrenome;
    @NotBlank private final String documento;
    @NotBlank private final String endereco;
    @NotBlank private final String complemento;
    @NotBlank private final String cidade;
    @NotNull @ManyToOne private final Pais pais;
    @ManyToOne private Estado estado;
    @NotBlank private final String telefone;
    @NotBlank private final String cep;
    @Enumerated(EnumType.STRING) private final StatusCompra statusCompra;
    @OneToOne(mappedBy = "compra", cascade = CascadeType.PERSIST) private final Pedido pedido;
    @Embedded private CupomAplicado cupomAplicado;

    public Compra(
            final String nome,
            final String email,
            final String sobrenome,
            final String documento,
            final String endereco,
            final String complemento,
            final String cidade,
            final Pais pais,
            final String telefone,
            final String cep, Function<Compra, Pedido> funcaoCriacaoPedido
    ) {

        // Validações usando Spring Assert
        Assert.hasText(nome, "O nome não pode estar vazio");
        Assert.hasText(email, "O email não pode estar vazio");
        Assert.hasText(sobrenome, "O sobrenome não pode estar vazio");
        Assert.hasText(documento, "O documento não pode estar vazio");
        Assert.hasText(endereco, "O endereço não pode estar vazio");
        Assert.hasText(complemento, "O complemento não pode estar vazio");
        Assert.hasText(cidade, "A cidade não pode estar vazia");
        Assert.notNull(pais, "O país não pode ser nulo");
        Assert.hasText(telefone, "O telefone não pode estar vazio");
        Assert.hasText(cep, "O CEP não pode estar vazio");

        this.nome = nome;
        this.email = email;
        this.sobrenome = sobrenome;
        this.documento = documento;
        this.endereco = endereco;
        this.complemento = complemento;
        this.cidade = cidade;
        this.pais = pais;
        this.telefone = telefone;
        this.cep = cep;
        this.pedido = funcaoCriacaoPedido.apply(this);

        this.statusCompra = StatusCompra.INICIADA;
    }

    public void setEstado(@NotNull @Valid Estado estado) {
        if (estado == null) return;
        Assert.notNull(this.pais, "estado não pode ser associado enquanto o país nulo");
        Assert.isTrue(estado.pertenceAoPais(this.pais), "estado deve pertencer ao país informado");
        this.estado = estado;
    }

    public void aplicarCupom(@NotNull @Valid Cupom cupom) {
        Assert.isTrue(cupom.isValido(), "cupom está expirado");
        Assert.isNull(this.cupomAplicado, "cupom de uma compra não pode ser trocado");
        Assert.isNull(this.id, "cupom não pode ser aplicado a uma compra existente");

        this.cupomAplicado = new CupomAplicado(cupom);
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Compra{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", documento='" + documento + '\'' +
                ", endereco='" + endereco + '\'' +
                ", complemento='" + complemento + '\'' +
                ", cidade='" + cidade + '\'' +
                ", pais=" + pais +
                ", estado=" + estado +
                ", telefone='" + telefone + '\'' +
                ", cep='" + cep + '\'' +
                ", statusCompra=" + statusCompra +
                ", pedido=" + pedido +
                ", cupom=" + cupomAplicado +
                '}';
    }
}
