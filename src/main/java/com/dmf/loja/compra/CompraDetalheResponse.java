package com.dmf.loja.compra;

import com.dmf.loja.paisestado.Estado;
import com.dmf.loja.paisestado.Pais;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

public record CompraDetalheResponse(
        String email,
        String nome,
        String sobrenome,
        String documento,
        String endereco,
        String complemento,
        String cidade,
        Pais pais,
        Estado estado,
        String telefone,
        String cep,
        Set<ItemPedidoDetalheResponde> itens,
        BigDecimal valorTotal,
        boolean cupom,
        BigDecimal valorFinal
) {
    public static CompraDetalheResponse from(final Long idCompra, final EntityManager entityManager) {
        Compra compra = entityManager.find(Compra.class, idCompra);

        return new CompraDetalheResponse(
                compra.getEmail(),
                compra.getNome(),
                compra.getSobrenome(),
                compra.getDocumento(),
                compra.getEndereco(),
                compra.getComplemento(),
                compra.getCidade(),
                compra.getPais(),
                compra.getEstado(),
                compra.getTelefone(),
                compra.getCep(),
                compra.getPedido().getItens().stream()
                        .map(ItemPedidoDetalheResponde::toModel) // Transformando os itens
                        .collect(Collectors.toSet()),
                compra.getPedido().getTotal(),
                compra.existeCupom(),
                compra.existeCupom() ? compra.valorFinalComCupom() : null

        );
    }
}
