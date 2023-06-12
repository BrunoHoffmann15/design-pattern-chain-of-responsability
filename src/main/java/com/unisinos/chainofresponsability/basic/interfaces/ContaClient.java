package com.unisinos.chainofresponsability.basic.interfaces;

import java.math.BigDecimal;

public interface ContaClient {
    BigDecimal getSaldo(String numeroCartao);
}
