package com.unisinos.chainofresponsability.services;

import com.unisinos.chainofresponsability.basic.ValidacaoCompraHandler;
import com.unisinos.chainofresponsability.basic.ValidacaoLimiteDiarioHandler;
import com.unisinos.chainofresponsability.basic.ValidacaoLimiteMensalHandler;
import com.unisinos.chainofresponsability.basic.ValidacaoSaldoHandler;
import com.unisinos.chainofresponsability.basic.interfaces.CartaoRepository;
import com.unisinos.chainofresponsability.basic.interfaces.ContaClient;
import com.unisinos.chainofresponsability.basic.models.CompraContext;
import com.unisinos.chainofresponsability.basic.services.CompraService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CompraServiceTests {
    ValidacaoCompraHandler validacaoCompraHandler;
    ValidacaoCompraHandler validacaoCompraHandler2;
    ValidacaoCompraHandler validacaoCompraHandler3;

    @Mock
    CartaoRepository cartaoRepository;

    @Mock
    ContaClient contaClient;

    CompraService compraService;

    @BeforeEach
    public void setUp() {
        validacaoCompraHandler = new ValidacaoLimiteDiarioHandler(cartaoRepository);
        validacaoCompraHandler2 = new ValidacaoLimiteMensalHandler(cartaoRepository);
        validacaoCompraHandler3 = new ValidacaoSaldoHandler(contaClient);

        validacaoCompraHandler.setProximo(validacaoCompraHandler2);
        validacaoCompraHandler2.setProximo(validacaoCompraHandler3);

        compraService = new CompraService(validacaoCompraHandler);
    }

    @Test
    public void validar_DadoNaoPossuoLimiteDiario_QuandoValidoMinhaCompra_EntaoTenhoRetornoFalso() {
        when(cartaoRepository.getLimiteDiarioDisponivel(anyString())).thenReturn(BigDecimal.valueOf(4.00));
        CompraContext compraContext = new CompraContext("12345", BigDecimal.valueOf(6.00));

        boolean retorno = compraService.validar(compraContext);

        assertFalse(retorno, "Compra ultrapassa limite diário");
    }

    @Test
    public void validar_DadoNaoPossuoMensalDiario_QuandoValidoMinhaCompra_EntaoTenhoRetornoFalso() {
        when(cartaoRepository.getLimiteDiarioDisponivel(anyString())).thenReturn(BigDecimal.valueOf(8.00));
        when(cartaoRepository.getLimiteMensalDisponivel(anyString())).thenReturn(BigDecimal.valueOf(3.00));

        CompraContext compraContext = new CompraContext("12345", BigDecimal.valueOf(6.00));

        boolean retorno = compraService.validar(compraContext);

        assertFalse(retorno, "Compra ultrapassa limite mensal");
    }

    @Test
    public void validar_DadoNaoPossuoSaldoSuficiente_QuandoValidoMinhaCompra_EntaoTenhoRetornoFalso() {
        when(cartaoRepository.getLimiteDiarioDisponivel(anyString())).thenReturn(BigDecimal.valueOf(8.00));
        when(cartaoRepository.getLimiteMensalDisponivel(anyString())).thenReturn(BigDecimal.valueOf(12.00));
        when(contaClient.getSaldo(anyString())).thenReturn(BigDecimal.valueOf(4.00));

        CompraContext compraContext = new CompraContext("12345", BigDecimal.valueOf(6.00));

        boolean retorno = compraService.validar(compraContext);

        assertFalse(retorno, "Conta sem saldo suficiente");
    }

    @Test
    public void validar_DadoPossuoTodasCondicoesParaCompra_QuandoValidoMinhaCompra_EntaoTenhoRetornoSucesso() {
        when(cartaoRepository.getLimiteDiarioDisponivel(anyString())).thenReturn(BigDecimal.valueOf(8.00));
        when(cartaoRepository.getLimiteMensalDisponivel(anyString())).thenReturn(BigDecimal.valueOf(12.00));
        when(contaClient.getSaldo(anyString())).thenReturn(BigDecimal.valueOf(14.00));

        CompraContext compraContext = new CompraContext("12345", BigDecimal.valueOf(6.00));

        boolean retorno = compraService.validar(compraContext);

        assertTrue(retorno, "Compra válida");
    }
}
