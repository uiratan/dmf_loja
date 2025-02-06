package com.dmf.loja.pagamento;

import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PagamentoController {

    @PostMapping("/pagamentos")
    public NovoPagamentoRequest realizarPagamento(@RequestBody @Valid NovoPagamentoRequest novoPagamentoRequest) {
        return novoPagamentoRequest;
    }
}

/**
 * necessidades
 * Uma coisa importante. Na cdc, você não faz um cadastro e tem suas compras associadas.
 * Toda vez você coloca seu email, cpf/cnpj etc.
 * Como isso vai ser implementado depende da aplicação.
 *
 * Os seguintes campos precisam ser preenchidos:
 *  NovoPagamentoRequest
 *
 * resultado esperado
 * Compra parcialmente gerada, mas ainda não gravada no banco de dados.
 *
 * Falta os dados do pedido em si que vão ser trabalhados no próximo cartão.
 * sobre a utilização do material de suporte aqui
 * Este começo de fechamento de compra envolve muitos passos. Decidimos começar pegando apenas os dados do formulário relativo a pessoa que está comprando. Este é um formulário um pouco mais desafiador, já que possuímos algumas validações customizadas que precisam ser feitas.
 */