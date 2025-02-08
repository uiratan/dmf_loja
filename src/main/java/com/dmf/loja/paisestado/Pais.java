package com.dmf.loja.paisestado;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import org.springframework.util.Assert;

import java.util.Objects;

//1
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

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return "Pais{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pais pais = (Pais) o;
        return Objects.equals(getNome(), pais.getNome());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getNome());
    }
}
