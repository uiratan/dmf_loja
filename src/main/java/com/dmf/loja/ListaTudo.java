package com.dmf.loja;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class ListaTudo {

    @PersistenceContext
    private EntityManager manager;

    @GetMapping(value = "/lista-tudo")
    public HashMap<String, Object> list() {
        List autores = manager.createQuery("select a from Autor a").getResultList();

        HashMap<String, Object> resultado = new HashMap<>();
        resultado.put("autores", autores.toString());

        List categorias = manager.createQuery("select c from Categoria c").getResultList();
        resultado.put("categorias", categorias.toString());

        List cupons = manager.createQuery("select c from Cupom c").getResultList();
        resultado.put("cupons", cupons.toString());

        return resultado;
    }
}