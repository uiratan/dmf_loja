package com.dmf.loja.categoria;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProibeNomeDuplicadoCategoriaValidator implements Validator {


    private final CategoriaRepository categoriaRepository;

    public ProibeNomeDuplicadoCategoriaValidator(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return NovaCategoriaRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        NovaCategoriaRequest request = (NovaCategoriaRequest) target;

        if (categoriaRepository.existsByNome(request.nome())) {
            errors.rejectValue("nome", null, "Já existe uma categoria cadastrada com este e-mail: " + request.nome());
        }
    }
}
