package com.CalculoFinanceiro.service;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.CalculoFinanceiro.domain.exception.DivisaoPorZeroException;
import com.CalculoFinanceiro.domain.model.Pessoa;
import com.CalculoFinanceiro.domain.service.PessoaCreditoService;


@SpringBootTest
public class TestCalculoFaixaSalarial {

	@Autowired
	private PessoaCreditoService pessoaCreditoService;

	Pessoa pessoa = new Pessoa(null,31,"Andrea Ramos", new BigDecimal(6496.00));
//
	BigDecimal salario;
	BigDecimal porcentagem;
	BigDecimal valor;
	BigDecimal valordoComprometimento;

	@BeforeEach
	public void setUp() {
		pessoa = new Pessoa(null,31,"Andrea Ramos", new BigDecimal(6496.00));
		salario = pessoa.getSalario();
		porcentagem = BigDecimal.ZERO;

	}

	@AfterEach
	public void tearDown() {
		pessoa = null;
		salario = BigDecimal.ZERO;
		valordoComprometimento = BigDecimal.ZERO;
		valor = BigDecimal.ZERO;
		porcentagem = BigDecimal.ZERO;
	}

	/*
	 * na regra de negocio pessoa so pode comprometer uma porcentagem de seu valor
	 * 
	 * De 1000 a 2000 reais, a parcela poderá comprometer 5% do salário De 2001 a
	 * 3000 reais, a parcela poderá comprometer 10% do salário De 3001 a 4000 reais,
	 * a parcela poderá comprometer 15% do salário De 4001 a 5000 reais, a parcela
	 * poderá comprometer 20% do salário De 5001 a 6000 reais, a parcela poderá
	 * comprometer 25% do salário De 6001 a 7000 reais, a parcela poderá comprometer
	 * 30% do salário De 7001 a 8000 reais, a parcela poderá comprometer 35% do
	 * salário De 8001 a 9000 reais, a parcela poderá comprometer 40% do salário A
	 * partir de 9001 reais, a parcela poderá comprometer 45% do salário
	 * 
	 */

	@Test
	public void calcular_compromedimento_test() {

		// cenario
		salario = pessoa.getSalario().multiply(new BigDecimal(0.9));
		porcentagem = porcentagem.add(new BigDecimal(0.25));
		valor = salario.multiply(porcentagem);

		// acao

		valordoComprometimento = pessoaCreditoService.calculoFaixaSalarial(salario);

		// verificacao
		assertEquals(valordoComprometimento, valor);

	}

	@Test
	public void calcular_compromedimento_errado() {
		// cenario
		salario = pessoa.getSalario();
		porcentagem = porcentagem.add(new BigDecimal(0.4));
		valor = salario.multiply(porcentagem);

		// acao
		valordoComprometimento = pessoaCreditoService.calculoFaixaSalarial(salario);

		// verificacao
		assertNotEquals(valordoComprometimento, valor);

	}

	@Test
	public void calcular_compromedimento_de_1000_a_2000_test() {

		// cenario

		pessoa.setSalario(new BigDecimal(1500));

		salario = pessoa.getSalario().multiply(new BigDecimal(0.9));
		porcentagem = porcentagem.add(new BigDecimal(0.05));
		valor = salario.multiply(porcentagem);

		// acao
		valordoComprometimento = pessoaCreditoService.calculoFaixaSalarial(salario);

		// verificacao
		assertEquals(valordoComprometimento, valor);

	}

	@Test
	public void calcular_compromedimento_de_2001_a_3000_test() {

		// cenario

		pessoa.setSalario(new BigDecimal(2500));

		salario = pessoa.getSalario().multiply(new BigDecimal(0.9));
		porcentagem = porcentagem.add(new BigDecimal(0.1));
		valor = salario.multiply(porcentagem);

		// acao
		valordoComprometimento = pessoaCreditoService.calculoFaixaSalarial(salario);

		// verificacao
		assertEquals(valordoComprometimento, valor);

	}

	@Test
	public void calcular_compromedimento_de_3001_a_4000_test() {

		// cenario

		pessoa.setSalario(new BigDecimal(3500));

		salario = pessoa.getSalario().multiply(new BigDecimal(0.9));
		porcentagem = porcentagem.add(new BigDecimal(0.15));
		valor = salario.multiply(porcentagem);

		// acao

		valordoComprometimento = pessoaCreditoService.calculoFaixaSalarial(salario);

		// verificacao
		assertEquals(valordoComprometimento, valor);

	}

	@Test
	public void calcular_compromedimento_de_4001_a_5000_test() {

		// cenario
		pessoa.setSalario(new BigDecimal(4500));

		salario = pessoa.getSalario().multiply(new BigDecimal(0.9));
		porcentagem = porcentagem.add(new BigDecimal(0.2));
		valor = salario.multiply(porcentagem);

		// acao
		valordoComprometimento = pessoaCreditoService.calculoFaixaSalarial(salario);

		// verificacao

		assertEquals(valordoComprometimento, valor);

	}

	@Test
	public void calcular_compromedimento_de_5001_a_6000_test() {

		// cenario

		pessoa.setSalario(new BigDecimal(5600));

		salario = pessoa.getSalario().multiply(new BigDecimal(0.9));
		porcentagem = porcentagem.add(new BigDecimal(0.25));
		valor = salario.multiply(porcentagem);

		// acao
		valordoComprometimento = pessoaCreditoService.calculoFaixaSalarial(salario);

		// verificacao
		assertEquals(valordoComprometimento, valor);

	}

	@Test
	public void calcular_compromedimento_de_6001_a_7000_test() {

		// cenario
		pessoa.setSalario(new BigDecimal(6700));

		salario = pessoa.getSalario().multiply(new BigDecimal(0.9));
		porcentagem = porcentagem.add(new BigDecimal(0.3));
		valor = salario.multiply(porcentagem);

		// acao
		valordoComprometimento = pessoaCreditoService.calculoFaixaSalarial(salario);

		// verificacao
		assertEquals(valordoComprometimento, valor);

	}

	@Test
	public void calcular_compromedimento_de_7001_a_8000_test() {

		// cenario
		pessoa.setSalario(new BigDecimal(7800));

		salario = pessoa.getSalario().multiply(new BigDecimal(0.9));
		porcentagem = porcentagem.add(new BigDecimal(0.35));
		valor = salario.multiply(porcentagem);

		// acao
		valordoComprometimento = pessoaCreditoService.calculoFaixaSalarial(salario);

		// verificacao
		assertEquals(valordoComprometimento, valor);

	}

	@Test
	public void calcular_compromedimento_de_8001_a_9000_test() {

		// cenario

		pessoa.setSalario(new BigDecimal(8900));

		salario = pessoa.getSalario().multiply(new BigDecimal(0.9));
		porcentagem = porcentagem.add(new BigDecimal(0.4));
		valor = salario.multiply(porcentagem);

		// acao
		valordoComprometimento = pessoaCreditoService.calculoFaixaSalarial(salario);

		// verificacao
		assertEquals(valordoComprometimento, valor);

	}

	@Test
	public void calcular_compromedimento_mais_9001_test() {
		
		// cenario

		pessoa.setSalario(new BigDecimal(10500));

		salario = pessoa.getSalario().multiply(new BigDecimal(0.9));
		porcentagem = porcentagem.add(new BigDecimal(0.45));
		valor = salario.multiply(porcentagem);

		// acao
		valordoComprometimento = pessoaCreditoService.calculoFaixaSalarial(salario);

		// verificacao
		assertEquals(valordoComprometimento, valor);

	}
	@Test
	public void idade_menor_e_igual_20_teste() {

		// cenario

				pessoa.setSalario(new BigDecimal(999.99));

				salario = pessoa.getSalario().multiply(new BigDecimal(0.9));
				porcentagem = porcentagem.add(new BigDecimal(0.45));
				valor = salario.multiply(porcentagem);

				try {
					valordoComprometimento = pessoaCreditoService.calculoFaixaSalarial(salario);
					fail();
				}catch (DivisaoPorZeroException ex) {
					assertThatExceptionOfType(DivisaoPorZeroException.class);
				}
		
	}
}
