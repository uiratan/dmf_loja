package com.dmf.loja.paisestado;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.util.Assert;

import java.util.List;

//0
@Entity
public class Pais {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String nome;

    @OneToMany(mappedBy = "pais")
    private List<Estado> estados;

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

    public List<Estado> getEstados() {
        return estados;
    }

    public boolean hasEstados() {
        return estados != null && !estados.isEmpty();
    }

    @Override
    public String toString() {
        return "Pais{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                '}';
    }
}
