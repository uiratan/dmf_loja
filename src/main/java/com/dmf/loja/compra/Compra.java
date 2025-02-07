package com.dmf.loja.compra;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Compra {
    @NotBlank private final String nome;
    @NotBlank @Email private final String email;
    @NotBlank private final String sobrenome;
    @NotBlank private final String documento;
    @NotBlank private final String endereco;
    @NotBlank private final String complemento;
    @NotBlank private final String cidade;
    @NotNull private final String idPais;
    private final String idEstado;
    @NotBlank private final String telefone;
    @NotBlank private final String cep;

    public Compra(
            String nome,
            String email,
            String sobrenome,
            String documento,
            String endereco,
            String complemento,
            String cidade,
            String idPais,
            String idEstado,
            String telefone,
            String cep) {
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
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
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

    public String getIdPais() {
        return idPais;
    }

    public String getIdEstado() {
        return idEstado;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCep() {
        return cep;
    }
}
