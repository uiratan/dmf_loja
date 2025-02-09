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

import java.math.BigDecimal;
import java.util.function.Function;

@Entity
public class Compra {
    @Id @GeneratedValue private Long id;
    @NotBlank private String nome;
    @NotBlank @Email private String email;
    @NotBlank private String sobrenome;
    @NotBlank private String documento;
    @NotBlank private String endereco;
    @NotBlank private String complemento;
    @NotBlank private String cidade;
    @NotNull @ManyToOne private Pais pais;
    @ManyToOne private Estado estado;
    @NotBlank private String telefone;
    @NotBlank private String cep;
    @Enumerated(EnumType.STRING) private StatusCompra statusCompra;
    @OneToOne(mappedBy = "compra", cascade = CascadeType.PERSIST) private Pedido pedido;
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


    @Deprecated
    public Compra() {
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

    public boolean existeCupom() {
        return cupomAplicado != null;
    }

    public BigDecimal valorFinalComCupom() {
        BigDecimal valorInicial = pedido.getTotal();
        BigDecimal percentualDesconto = cupomAplicado.getPercentualDescontoMomento();
        BigDecimal valorDesconto = valorInicial.multiply(percentualDesconto).divide(BigDecimal.valueOf(100));

        // Calcular o valor final subtraindo o desconto do valor inicial
        return valorInicial.subtract(valorDesconto);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public String getDocumento() {
        return documento;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getCidade() {
        return cidade;
    }

    public Pais getPais() {
        return pais;
    }

    public Estado getEstado() {
        return estado;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCep() {
        return cep;
    }

    public StatusCompra getStatusCompra() {
        return statusCompra;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public CupomAplicado getCupomAplicado() {
        return cupomAplicado;
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
