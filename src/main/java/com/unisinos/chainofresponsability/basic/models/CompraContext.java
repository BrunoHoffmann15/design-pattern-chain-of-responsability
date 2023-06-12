package com.unisinos.chainofresponsability.basic.models;

import java.math.BigDecimal;

public class CompraContext {
    private String numeroCartao;
    private BigDecimal valorCompra;

    public CompraContext(String numeroCartao, BigDecimal valorCompra) {
        this.numeroCartao = numeroCartao;
        this.valorCompra = valorCompra;
    }

    public String getNumeroCartao() {
        return this.numeroCartao;
    }

    public BigDecimal getValorCompra() {
        return this.valorCompra;
    }
}
