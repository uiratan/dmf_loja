package com.dmf.loja.novoautor;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProibeEmailDuplicadoAutorValidator implements Validator {

    private final AutorRepository autorRepository;

    public ProibeEmailDuplicadoAutorValidator(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return NovoAutorRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        NovoAutorRequest request = (NovoAutorRequest) target;

        if (autorRepository.existsByEmail(request.email())) {
            errors.rejectValue("email", null, "Já existe um autor cadastrado com este e-mail: " + request.email());
        }
    }
}
