package com.dmf.loja.compra.validators;

import com.dmf.loja.compra.dto.PedidoRequest;
import com.dmf.loja.compra.dto.NovaCompraRequest;
import com.dmf.loja.livro.Livro;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

@Component
public class CarrinhoValidator implements Validator {

    private final EntityManager entityManager;

    public CarrinhoValidator(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return NovaCompraRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        NovaCompraRequest novaCompraRequest = (NovaCompraRequest) target;
        PedidoRequest carrinho = novaCompraRequest.carrinho();

        if (carrinho.itens().isEmpty()) {
            errors.rejectValue("carrinho", null, "carrinho vazio");
        }

        BigDecimal valorTotalCalculado = calcularPrecoTotal(carrinho);

        if (carrinho.total().compareTo(valorTotalCalculado) !=0 ) {
            errors.rejectValue("carrinho.total", null, "valor inconsistente");
        }
    }

    private BigDecimal calcularPrecoTotal(PedidoRequest carrinho) {
        return carrinho.itens().stream()
                .map(item ->
                        entityManager.find(Livro.class, item.idLivro()).getPreco()
                                .multiply(BigDecimal.valueOf(item.quantidade()))
                ).reduce(BigDecimal.ZERO, BigDecimal::add);
    }


}
