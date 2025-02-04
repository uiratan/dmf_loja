package com.dmf.loja.categoria;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.repository.CrudRepository;

public interface CategoriaRepository extends CrudRepository<Categoria, Long> {

    boolean existsByNome(@NotBlank String nome);

}
