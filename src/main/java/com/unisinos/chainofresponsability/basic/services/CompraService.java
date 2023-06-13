package com.unisinos.chainofresponsability.basic.services;

import com.unisinos.chainofresponsability.basic.ValidacaoCompraHandler;
import com.unisinos.chainofresponsability.basic.models.CompraContext;

public class CompraService {
    public ValidacaoCompraHandler validacaoCompraHandler;

    public CompraService(ValidacaoCompraHandler validacaoCompraHandler) {
        this.validacaoCompraHandler = validacaoCompraHandler;
    }

    public boolean validar(CompraContext compraContext) {
        return validacaoCompraHandler.validar(compraContext);
    }
}
