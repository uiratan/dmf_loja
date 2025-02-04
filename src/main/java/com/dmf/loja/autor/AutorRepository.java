package com.dmf.loja.autor;

import org.springframework.data.repository.CrudRepository;

public interface AutorRepository extends CrudRepository<Autor, Long> {

    boolean existsByEmail(String email);

}
