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

import com.CalculoFinanceiro.domain.exception.IdadeInferiorA21Exception;
import com.CalculoFinanceiro.domain.model.Pessoa;
import com.CalculoFinanceiro.domain.service.PessoaCreditoService;


@SpringBootTest
public class TestCalculoPercentualIdade {

	@Autowired
	private PessoaCreditoService pessoaCreditoService;

	Pessoa pessoa;
	double salario;
	BigDecimal porcentagemRecebida;

	@BeforeEach
	public void setUp() {
		pessoa = new Pessoa(null,31,"Andrea Ramos", new BigDecimal(6496.00));
		porcentagemRecebida = new BigDecimal(0);
		salario = pessoa.getSalario().doubleValue();
	}

	@AfterEach
	public void tearDown() {
		pessoa = null;
		porcentagemRecebida = new BigDecimal(0);
		salario = 0.0;
	}

	/*
	 * teste para calcupar o quanto a pessoa pode receber de percentual do salario
	 * 
	 * Idade maior que 80, poderá receber até 20% do salário Idade maior que 50,
	 * poderá receber até 70% do salário Idade maior que 30, poderá receber até 90%
	 * do salário Idade maior que 20, poderá receber até 100% do salário
	 */

	@Test
	public void idade_21_a_30_teste() {

		// cenario

		pessoa.setIdade(21);
		// acao

		porcentagemRecebida = pessoaCreditoService.calculoPercentualIdade(pessoa);

		// verificacao

		assertEquals(porcentagemRecebida.doubleValue(), salario);

		// cenario
		pessoa.setIdade(30);

		// acao
		porcentagemRecebida = pessoaCreditoService.calculoPercentualIdade(pessoa);

		// verificacao
		assertEquals(porcentagemRecebida.doubleValue(), salario);

		// cenario
		pessoa.setIdade(31);

		// acao
		porcentagemRecebida = pessoaCreditoService.calculoPercentualIdade(pessoa);

		// verificacao
		assertNotEquals(porcentagemRecebida.doubleValue(), salario);

	}

	@Test
	public void idade_31_a_50_teste() {

		// cenario
		salario *= 0.9;
		pessoa.setIdade(31);
		// acao
		porcentagemRecebida = pessoaCreditoService.calculoPercentualIdade(pessoa);
		// verificacao
		assertEquals(porcentagemRecebida.doubleValue(), salario);

		// cenario
		pessoa.setIdade(50);
		// acao
		porcentagemRecebida = pessoaCreditoService.calculoPercentualIdade(pessoa);
		// verificacao
		assertEquals(porcentagemRecebida.doubleValue(), salario);

		// cenario
		pessoa.setIdade(30);
		// acao
		porcentagemRecebida = pessoaCreditoService.calculoPercentualIdade(pessoa);
		// verificacao
		assertNotEquals(porcentagemRecebida.doubleValue(), salario);

		// cenario
		pessoa.setIdade(51);
		// acao
		porcentagemRecebida = pessoaCreditoService.calculoPercentualIdade(pessoa);
		// verificacao
		assertNotEquals(porcentagemRecebida.doubleValue(), salario);

	}

	@Test
	public void idade_51_a_80_teste() {

		// cenario
		salario *= 0.7;
		pessoa.setIdade(51);
		// acao
		porcentagemRecebida = pessoaCreditoService.calculoPercentualIdade(pessoa);
		// verificacao
		assertEquals(porcentagemRecebida.doubleValue(), salario);

		// cenario
		pessoa.setIdade(80);
		// acao
		porcentagemRecebida = pessoaCreditoService.calculoPercentualIdade(pessoa);
		// verificacao
		assertEquals(porcentagemRecebida.doubleValue(), salario);

		// cenario
		pessoa.setIdade(50);
		// acao
		porcentagemRecebida = pessoaCreditoService.calculoPercentualIdade(pessoa);
		// verificacao
		assertNotEquals(porcentagemRecebida.doubleValue(), salario);

		// cenario
		pessoa.setIdade(81);
		// acao
		porcentagemRecebida = pessoaCreditoService.calculoPercentualIdade(pessoa);
		// verificacao
		assertNotEquals(porcentagemRecebida.doubleValue(), salario);

	}

	@Test
	public void idade_81_maior_teste() {
		// cenario
		salario *= 0.2;
		pessoa.setIdade(81);
		// acao
		porcentagemRecebida = pessoaCreditoService.calculoPercentualIdade(pessoa);
		// verificacao
		assertEquals(porcentagemRecebida.doubleValue(), salario);

		// cenario
		pessoa.setIdade(80);
		// acao
		porcentagemRecebida = pessoaCreditoService.calculoPercentualIdade(pessoa);
		// verificacao
		assertNotEquals(porcentagemRecebida.doubleValue(), salario);

	}

	@Test
	public void idade_menor_e_igual_20_teste() {

		// cenario
		salario *= 0;
		pessoa.setIdade(20);
		// acao
		try {
			porcentagemRecebida = pessoaCreditoService.calculoPercentualIdade(pessoa);
			fail();
		}catch (IdadeInferiorA21Exception ex) {
			assertThatExceptionOfType(IdadeInferiorA21Exception.class);
		}
	}
}
