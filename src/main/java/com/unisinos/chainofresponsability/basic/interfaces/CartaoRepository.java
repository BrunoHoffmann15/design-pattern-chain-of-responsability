package com.unisinos.chainofresponsability.basic.interfaces;

import java.math.BigDecimal;

public interface CartaoRepository {
    BigDecimal getLimiteDiarioDisponivel(String numeroCartao);
    BigDecimal getLimiteMensalDisponivel(String numeroCartao);
}
