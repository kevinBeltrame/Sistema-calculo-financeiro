package com.CalculoFinanceiro.service;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestePassarValorPedidoNull {

	@Autowired
	private PessoaCreditoService pessoaCreditoService;

	@Test
	public void passar_valor_null() {
		// cenario

		BigDecimal valorPedido = null;

		// acao
		try {
			pessoaCreditoService.validarValorNullEZero(valorPedido);
			fail();

		} catch (Exception e) {
			// verificacao
			assertEquals(e.getClass(), IllegalArgumentException.class);
		}
	}

	@Test
	public void passar_valor() {
		// cenario

		BigDecimal valorPedido = new BigDecimal(5000);

		try {	
			// acao
			pessoaCreditoService.validarValorNullEZero(valorPedido);
			// verificacao
		} catch (Exception e) {
			fail();
		}
	}
}
