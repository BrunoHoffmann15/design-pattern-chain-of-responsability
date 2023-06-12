package com.unisinos.chainofresponsability.basic;

import com.unisinos.chainofresponsability.basic.models.CompraContext;

public abstract class ValidacaoCompraHandler {
    protected ValidacaoCompraHandler proximo;

    public void setProximo(ValidacaoCompraHandler handler) {
        this.proximo = proximo;
    }

    public abstract boolean validar(CompraContext compraContext);
}
