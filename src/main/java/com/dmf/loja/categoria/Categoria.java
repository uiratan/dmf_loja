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

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
