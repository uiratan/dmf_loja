package com.dmf.loja.novoautor;

import org.springframework.data.repository.CrudRepository;

public interface AutorRepository extends CrudRepository<Autor, Long> {

    boolean existsByEmail(String email);

}
