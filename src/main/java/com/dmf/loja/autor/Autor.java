package com.dmf.loja.autor;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.util.Assert;

import java.time.Instant;

//0
@Entity
public class Autor {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank private String nome;

    @Column(nullable = false, unique = true)
    @NotBlank @Email private String email;

    @Column(nullable = false, length = 400)
    @NotBlank @Size(max = 400) private String descricao;

    @Column(nullable = false, updatable = false, columnDefinition = "DATETIME(6)")
    private Instant instanteCriacao;

    public Autor(
            final String nome,
            final String email,
            final String descricao
    ) {
        Assert.hasText(nome, "O nome é obrigatório");
        Assert.hasText(email, "O email é obrigatório");
        Assert.hasText(descricao, "A descrição é obrigatória");
        Assert.isTrue(descricao.length() <= 400, "A descrição não pode passar de 400 caracteres");

        this.nome = nome;
        this.email = email;
        this.descricao = descricao;
        this.instanteCriacao = Instant.now();
    }

    @Deprecated
    public Autor() {}

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getDescricao() {
        return descricao;
    }

    public Instant getInstanteCriacao() {
        return instanteCriacao;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", descricao='" + descricao + '\'' +
                ", instanteCriacao=" + instanteCriacao +
                '}';
    }
}
