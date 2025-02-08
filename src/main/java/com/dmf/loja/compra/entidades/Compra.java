package com.dmf.loja.compra.entidades;

import com.dmf.loja.paisestado.Estado;
import com.dmf.loja.paisestado.Pais;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    @ManyToOne
    @JoinColumn(name = "pais_id", nullable = false)
    @NotNull private Pais pais;
    @ManyToOne
    @JoinColumn(name = "estado_id")
    private Estado estado;
    @NotBlank private String telefone;
    @NotBlank private String cep;
    @NotNull @Positive BigDecimal total;
    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true)
    @NotNull List<ItemCompra> itens = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private StatusCompra statusCompra;

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
            final String cep,
            final BigDecimal total) {

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
        Assert.notNull(total, "O total não pode ser nulo");
        Assert.isTrue(total.compareTo(BigDecimal.ZERO) > 0, "O total deve ser maior a zero");

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
        this.total = total;
        this.statusCompra = StatusCompra.INICIADA;
    }

    @Deprecated
    public Compra() {
    }

    public void setEstado(@NotNull @Valid Estado estado) {
        Assert.notNull(this.pais, "estado não pode ser associado enquanto o país nulo");
        Assert.isTrue(estado.pertenceAoPais(this.pais), "estado deve pertencer ao país informado");
        this.estado = estado;
    }

    public void setItens(List<ItemCompra> itens) {
        Assert.notNull(itens, "A lista de itens não pode ser nula");
        Assert.notEmpty(itens, "A lista de itens não pode estar vazia");

        this.itens = List.copyOf(itens);
        this.itens.forEach(item -> item.setCompra(this));
    }

    // Getters
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getSobrenome() { return sobrenome; }
    public String getDocumento() { return documento; }
    public String getEndereco() { return endereco; }
    public String getComplemento() { return complemento; }
    public String getCidade() { return cidade; }
    public Pais getPais() { return pais; }
    public Estado getEstado() { return estado; }
    public String getTelefone() { return telefone; }
    public String getCep() { return cep; }
    public BigDecimal getTotal() { return total; }
    public List<ItemCompra> getItens() { return itens; }
    public StatusCompra getStatusCompra() { return statusCompra; }
}
