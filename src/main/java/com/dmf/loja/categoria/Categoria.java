package com.dmf.loja.categoria;

import com.dmf.loja.livro.Livro;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Categoria {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String nome;

    // Lado inverso: mapeia os livros com o atributo "autor" da entidade Livro.
//    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<Livro> livros = new HashSet<>();

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
