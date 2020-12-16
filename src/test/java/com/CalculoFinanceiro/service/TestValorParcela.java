package com.CalculoFinanceiro.service;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.CalculoFinanceiro.domain.model.Pessoa;
import com.CalculoFinanceiro.domain.service.PessoaCreditoService;


@SpringBootTest
public class TestValorParcela {

	@Autowired
	private PessoaCreditoService pessoaCreditoService;

	Pessoa pessoa;
	BigDecimal valorParcela;
	BigDecimal valorPedido;
	BigDecimal quantidaeParcela;
	BigDecimal valor;
	BigDecimal salarioCalculadoComIdadeEValorMaximo;


	@BeforeEach
	public void setUp() {
		pessoa = new Pessoa(null,31,"Andrea Ramos", new BigDecimal(6496.00));
		valorParcela = BigDecimal.ZERO;
		valorPedido = BigDecimal.ZERO;
		quantidaeParcela = BigDecimal.ZERO;
		valor = BigDecimal.ZERO;
		salarioCalculadoComIdadeEValorMaximo = pessoaCreditoService.valorMaximodaParcela(pessoa);
	}

	@AfterEach
	public void tearDown() {
		pessoa = null;
		valorParcela = BigDecimal.ZERO;
		valorPedido = BigDecimal.ZERO;
		quantidaeParcela = BigDecimal.ZERO;
		valor = BigDecimal.ZERO;
		salarioCalculadoComIdadeEValorMaximo = BigDecimal.ZERO;

	}

	@Test
	public void valorParcela() {
		
		// cenario

		valorPedido = new BigDecimal(900);

		BigDecimal quantidaeParcela = new BigDecimal(
				pessoaCreditoService.cacularQuantidadeParcerla(salarioCalculadoComIdadeEValorMaximo, valorPedido));

		if (quantidaeParcela.doubleValue() != 0) {
			valor = (valorPedido.divide(quantidaeParcela, 2, RoundingMode.HALF_UP));
		} else {
			valor = valorPedido;
			fail();
		}

		// acao 
		valorParcela = pessoaCreditoService.valorParcela(salarioCalculadoComIdadeEValorMaximo, valorPedido);

		// verificacao
		assertEquals(valorParcela, valor);

		assertNotEquals(valorParcela, valorPedido);

	}

	@Test
	public void valorParcela_igual_pedido() {

		// cenario

		valorPedido = new BigDecimal(41.55).setScale(2, RoundingMode.HALF_EVEN);

		BigDecimal quantidaeParcela = new BigDecimal(
				pessoaCreditoService.cacularQuantidadeParcerla(salarioCalculadoComIdadeEValorMaximo, valorPedido));

		if (quantidaeParcela.doubleValue() != 0) {
			valor = (valorPedido.divide(quantidaeParcela, 2, RoundingMode.HALF_UP));

		} else {
			valor = valorPedido;
			fail();
		}

		// acao 

		valorParcela = pessoaCreditoService.valorParcela(salarioCalculadoComIdadeEValorMaximo, valorPedido);

		// verificacao

		assertEquals(valorParcela, valorPedido);

		assertEquals(valorParcela, valor);

	}
}
