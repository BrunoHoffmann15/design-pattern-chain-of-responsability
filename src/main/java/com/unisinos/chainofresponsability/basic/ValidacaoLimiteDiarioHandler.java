package com.unisinos.chainofresponsability.basic;

import com.unisinos.chainofresponsability.basic.interfaces.CartaoRepository;
import com.unisinos.chainofresponsability.basic.models.CompraContext;

import java.math.BigDecimal;

public class ValidacaoLimiteDiarioHandler extends ValidacaoCompraHandler {
    private CartaoRepository cartaoRepository;

    public ValidacaoLimiteDiarioHandler(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    @Override
    public boolean validar(CompraContext compraContext) {
        BigDecimal limiteDiario = cartaoRepository.getLimiteDiarioDisponivel(compraContext.getNumeroCartao());

        if (limiteDiario.compareTo(compraContext.getValorCompra()) < 0) {
            return false;
        }

        return super.proximo.validar(compraContext);
    }
}
