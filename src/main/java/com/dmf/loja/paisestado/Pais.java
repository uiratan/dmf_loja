package com.dmf.loja.paisestado;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import org.springframework.util.Assert;

@Entity
public class Pais {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String nome;

    public Pais(String nome) {
        Assert.hasText(nome, "O nome é obrigatório");

        this.nome = nome;
    }

    @Deprecated
    public Pais() {
    }

    @Override
    public String toString() {
        return "Pais{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                '}';
    }
}
