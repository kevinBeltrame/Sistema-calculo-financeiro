package com.CalculoFinanceiro.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.CalculoFinanceiro.model.Pessoa;

@SpringBootTest
public class TestCacularQuantidadeParcerla {

	@Autowired
	private PessoaCreditoService pessoaCreditoService;

	Pessoa pessoa;
	double salario;
	int quantidadeParcela;
	BigDecimal valorPedido;
	int quantidadeParcelaComparacao;
	BigDecimal salarioCalculadoComIdadeEValorMaximo;


	@BeforeEach
	public void setUp() {
		pessoa = new Pessoa("Andrea Gomes", 22, new BigDecimal(4304.00));

		quantidadeParcela = 0;
		salario = pessoa.getSalario().doubleValue();
		valorPedido = BigDecimal.ZERO;
		quantidadeParcelaComparacao = 0;
		salarioCalculadoComIdadeEValorMaximo = pessoaCreditoService.valorMaximodaParcela(pessoa);

	}

	@BeforeEach
	public void tearDown() {
		pessoa = null;
		quantidadeParcela = 0;
		salario = 0.0;
		quantidadeParcelaComparacao = 0;
		valorPedido = BigDecimal.ZERO;
		salarioCalculadoComIdadeEValorMaximo = BigDecimal.ZERO;

	}

	@Test
	public void quantidade_parcela_1() {
		
		// cenario
		int parcela = 1;
		valorPedido = new BigDecimal(300);
		// acao		
		int parcelaRetornada = pessoaCreditoService.cacularQuantidadeParcerla(salarioCalculadoComIdadeEValorMaximo, valorPedido);
		// verificacao		
		assertEquals(parcela, parcelaRetornada);
		

	}

	@Test
	public void quantidade_parcela_2_ou_mais() {
		
		// cenario
		int parcela = 2;
		valorPedido = new BigDecimal(1722);
		// acao
		int parcelaRetornada = pessoaCreditoService.cacularQuantidadeParcerla(salarioCalculadoComIdadeEValorMaximo, valorPedido);
	
		// verificacao
		
		assertEquals(parcela, parcelaRetornada);

	}
	
	@Test
	public void passandoValoresNegativos() {
		
		
		try {
			// cenario
			valorPedido = new BigDecimal(-1722);
			// acao
			pessoaCreditoService.cacularQuantidadeParcerla(salarioCalculadoComIdadeEValorMaximo, valorPedido);
		} catch (Exception e) {
			// verificacao			
			assertEquals(e.getClass(), NumberFormatException.class);
		}
	}
	
	@Test
	public void passandoValoresNull() {
		
		// cenario
		valorPedido = null;
		// acao
		try {
			pessoaCreditoService.cacularQuantidadeParcerla(salarioCalculadoComIdadeEValorMaximo, valorPedido);
		} catch (Exception e) {
			// verificacao			
			assertEquals(e.getClass(), IllegalArgumentException.class);
		}
	}

}
