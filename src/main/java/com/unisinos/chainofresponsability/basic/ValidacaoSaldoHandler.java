package com.unisinos.chainofresponsability.basic;

import com.unisinos.chainofresponsability.basic.interfaces.ContaClient;
import com.unisinos.chainofresponsability.basic.models.CompraContext;

import java.math.BigDecimal;

public class ValidacaoSaldoHandler extends ValidacaoCompraHandler {
    private final ContaClient contaClient;

    public ValidacaoSaldoHandler(ContaClient contaClient) {
        this.contaClient = contaClient;
    }

    @Override
    public boolean validar(CompraContext compraContext) {
        BigDecimal saldoConta = contaClient.getSaldo(compraContext.getNumeroCartao());

        return saldoConta.compareTo(compraContext.getValorCompra()) >= 0;
    }
}
