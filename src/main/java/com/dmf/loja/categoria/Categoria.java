package com.dmf.loja.categoria;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import org.springframework.util.Assert;

@Entity
public class Categoria {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    public Categoria(String nome) {
        Assert.hasText(nome, "O nome é obrigatório");
        this.nome = nome;
    }

    @Deprecated
    public Categoria() { }

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
