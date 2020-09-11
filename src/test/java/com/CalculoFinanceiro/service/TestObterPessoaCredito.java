package com.CalculoFinanceiro.service;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.CalculoFinanceiro.model.Pessoa;
import com.CalculoFinanceiro.model.PessoaCredito;

@SpringBootTest
public class TestObterPessoaCredito {

	@Autowired
	private PessoaCreditoService pessoaCreditoService;

	Pessoa pessoa;
	BigDecimal valorPedido;
	PessoaCredito pessoaCredito;
	BigDecimal salarioCalculadoComIdadeEValorMaximo;


	@BeforeEach
	public void setUp() {
		pessoa = new Pessoa("Andrea Ramos", 31, new BigDecimal(6496.00));
		valorPedido = BigDecimal.ZERO;
		salarioCalculadoComIdadeEValorMaximo = pessoaCreditoService.valorMaximodaParcela(pessoa);

	}

	@AfterEach
	public void tearDown() {
		pessoa = null;
		valorPedido = BigDecimal.ZERO;
		pessoaCredito = null;
		salarioCalculadoComIdadeEValorMaximo = BigDecimal.ZERO;
	}

	@Test
	public void obter_pessoa_credito_test() {
		
		// cenario

		valorPedido = new BigDecimal(700.00).setScale(2);

		BigDecimal valorEmprestado = pessoaCreditoService.calcularValorEmprestado(salarioCalculadoComIdadeEValorMaximo, valorPedido);
		BigDecimal valorParcela = pessoaCreditoService.valorParcela(salarioCalculadoComIdadeEValorMaximo, valorPedido);
		int quantidadeParcela = pessoaCreditoService.cacularQuantidadeParcerla(salarioCalculadoComIdadeEValorMaximo, valorPedido);
		
		// acao
		pessoaCredito = pessoaCreditoService.obter(pessoa, valorPedido,salarioCalculadoComIdadeEValorMaximo);
		
		// verificacao
		assertEquals(pessoaCredito.getNome(), pessoa.getNome());
		assertEquals(pessoaCredito.getSalario(), pessoa.getSalario());
		assertEquals(pessoaCredito.getValorPedido(), valorPedido);
		assertEquals(pessoaCredito.getValorEmprestado(), valorEmprestado);
		assertEquals(pessoaCredito.getValorParcela(), valorParcela);
		assertEquals(pessoaCredito.getQuantidadeParcelas(), quantidadeParcela);

	}
	
	
	@Test
	public void validar_valor_0() {

		// cenario

		valorPedido = new BigDecimal(0).setScale(2);

		try {
			// acao
			pessoaCreditoService.obterPessoaCredito(pessoa, valorPedido);
			fail("deveria lançar um Exception");
		} catch (Exception e) {
			// verificacao
			assertEquals(e.getMessage(), null);
			assertEquals(e.getClass(), NumberFormatException.class);
		}

	}


}
