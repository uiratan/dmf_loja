package com.dmf.loja.compra;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.List;

public class Compra {
    @NotBlank private String nome;
    @NotBlank @Email private String email;
    @NotBlank private String sobrenome;
    @NotBlank private String documento;
    @NotBlank private String endereco;
    @NotBlank private String complemento;
    @NotBlank private String cidade;
    @NotNull private String idPais;
    private String idEstado;
    @NotBlank private String telefone;
    @NotBlank private String cep;
    @NotNull @DecimalMin(value = "0.00", inclusive = false, message = "o total deve ser maior que zero")
    BigDecimal total;
    @NotNull List<ItemCompra> itens;

    public Compra(
            final String nome,
            final String email,
            final String sobrenome,
            final String documento,
            final String endereco,
            final String complemento,
            final String cidade,
            final String idPais,
            final String idEstado,
            final String telefone,
            final String cep,
            final BigDecimal total,
            final List<ItemCompra> itens) {
        // Validações usando Spring Assert
        Assert.hasText(nome, "O nome não pode estar vazio");
        Assert.hasText(email, "O email não pode estar vazio");
        Assert.hasText(sobrenome, "O sobrenome não pode estar vazio");
        Assert.hasText(documento, "O documento não pode estar vazio");
        Assert.hasText(endereco, "O endereço não pode estar vazio");
        Assert.hasText(complemento, "O complemento não pode estar vazio");
        Assert.hasText(cidade, "A cidade não pode estar vazia");
        Assert.hasText(idPais, "O ID do país não pode estar vazio");
        Assert.hasText(telefone, "O telefone não pode estar vazio");
        Assert.hasText(cep, "O CEP não pode estar vazio");
        Assert.notNull(total, "O total não pode ser nulo");
        Assert.isTrue(total.compareTo(BigDecimal.ZERO) > 0, "O total deve ser maior a zero");
        Assert.notNull(itens, "A lista de itens não pode ser nula");
        Assert.notEmpty(itens, "A lista de itens não pode estar vazia");

        this.nome = nome;
        this.email = email;
        this.sobrenome = sobrenome;
        this.documento = documento;
        this.endereco = endereco;
        this.complemento = complemento;
        this.cidade = cidade;
        this.idPais = idPais;
        this.idEstado = idEstado;
        this.telefone = telefone;
        this.cep = cep;
        this.total = total;
        this.itens = List.copyOf(itens);
    }

    @Deprecated
    public Compra() {
    }

    // Getters
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getSobrenome() { return sobrenome; }
    public String getDocumento() { return documento; }
    public String getEndereco() { return endereco; }
    public String getComplemento() { return complemento; }
    public String getCidade() { return cidade; }
    public String getIdPais() { return idPais; }
    public String getIdEstado() { return idEstado; }
    public String getTelefone() { return telefone; }
    public String getCep() { return cep; }
    public BigDecimal getTotal() { return total; }
    public List<ItemCompra> getItens() { return itens; }
}
