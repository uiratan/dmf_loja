package com.dmf.loja.paisestado;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.util.Assert;

@Entity
public class Estado {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank private String nome;

    @ManyToOne
    @JoinColumn(name = "pais_id", nullable = false)
    @NotNull private Pais pais;

    public Estado(String nome, Pais pais) {
        Assert.hasText(nome, "O nome é obrigatório");
        Assert.notNull(pais, "O País não pode ser nulo");

        this.nome = nome;
        this.pais = pais;
    }

    @Deprecated
    public Estado() {
    }

    @Override
    public String toString() {
        return "Estado{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", pais=" + pais +
                '}';
    }
}
