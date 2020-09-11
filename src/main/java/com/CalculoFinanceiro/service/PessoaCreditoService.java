package com.CalculoFinanceiro.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.CalculoFinanceiro.model.Pessoa;
import com.CalculoFinanceiro.model.PessoaCredito;
import com.CalculoFinanceiro.service.exception.DivisaoPorZero;

@Service
public class PessoaCreditoService {

	public ResponseEntity<PessoaCredito> obterPessoaCredito(Pessoa pessoa, BigDecimal valorPedido) {
		BigDecimal salarioCalculadoComIdadeEValorMaximo = valorMaximodaParcela(pessoa);
		try {
			validarValorNullEZero(valorPedido);

			PessoaCredito pessoaCredito = obter(pessoa, valorPedido, salarioCalculadoComIdadeEValorMaximo);

			return pessoaCredito != null ? ResponseEntity.ok(pessoaCredito) : ResponseEntity.notFound().build();
		} catch (EntityNotFoundException ex) {
			throw new EntityNotFoundException();
		}
	}

	public PessoaCredito obter(Pessoa pessoa, BigDecimal valorPedido, BigDecimal salarioCalculadoComIdadeEValorMaximo) {

		PessoaCredito pessoaCredito = new PessoaCredito();

		validarValorNullEZero(valorPedido);

		pessoaCredito.setNome(pessoa.getNome());
		pessoaCredito.setSalario(pessoa.getSalario());
		pessoaCredito
				.setQuantidadeParcelas(cacularQuantidadeParcerla(salarioCalculadoComIdadeEValorMaximo, valorPedido));
		pessoaCredito.setValorPedido(valorPedido.setScale(2));
		pessoaCredito.setValorParcela(valorParcela(salarioCalculadoComIdadeEValorMaximo, valorPedido));

		pessoaCredito.setValorEmprestado(calcularValorEmprestado(salarioCalculadoComIdadeEValorMaximo, valorPedido));

		return pessoaCredito;
	}

	public BigDecimal calcularValorEmprestado(BigDecimal salarioCalculadoComIdadeEValorMaximo, BigDecimal valorPedido) {
		validarValorNullEZero(valorPedido);
		BigDecimal valordaParcela = valorParcela(salarioCalculadoComIdadeEValorMaximo, valorPedido);
		int quantidadeParcela = cacularQuantidadeParcerla(salarioCalculadoComIdadeEValorMaximo, valorPedido);

		return valordaParcela.multiply(new BigDecimal(quantidadeParcela));
	}

	public void validarValorNullEZero(BigDecimal valorPedido) {
		if (valorPedido == null) {
			throw new IllegalArgumentException();
		} else if (valorPedido.intValue() <= 0) {
			throw new NumberFormatException();
		}
	}

	public BigDecimal valorParcela(BigDecimal salarioCalculadoComIdadeEValorMaximo, BigDecimal valorPedido) {
		BigDecimal valor;
		BigDecimal quantidaeParcela = new BigDecimal(
				cacularQuantidadeParcerla(salarioCalculadoComIdadeEValorMaximo, valorPedido));

		if (quantidaeParcela.doubleValue() != 0) {
			valor = (valorPedido.divide(quantidaeParcela, 2, RoundingMode.UP));
		} else {
			valor = valorPedido;
		}
		return valor;

	}

	public int cacularQuantidadeParcerla(BigDecimal salarioCalculadoComIdadeEValorMaximo, BigDecimal valorPedido) {

		validarValorNullEZero(valorPedido);

		int quantidadeParcela = valorPedido.divide(salarioCalculadoComIdadeEValorMaximo, RoundingMode.DOWN).intValue();
		if (quantidadeParcela == 0) {
			quantidadeParcela = 1;
		}
		
		for(int i = quantidadeParcela;i<valorPedido.doubleValue();i++) {
			BigDecimal a = new BigDecimal(i);
			BigDecimal b = valorPedido.divide(a,2,RoundingMode.DOWN);
			if(valorPedido.remainder(b.multiply(a)).doubleValue() == 0) {
				quantidadeParcela = i;
				break;
			}
		}

		return quantidadeParcela;
	}


	//faixa salarial da pessoa a parcela poderá comprometer  calculoFaixaSalarial
	public BigDecimal calculoFaixaSalarial(BigDecimal salario) {

		BigDecimal valorParcelaMaximo;
		BigDecimal comprometimento = BigDecimal.ZERO;

		if (salario.intValue() >= 1000 && salario.intValue() <= 2000) {
			comprometimento = comprometimento.add(new BigDecimal(0.05));
		} else if (salario.intValue() >= 2001 && salario.intValue() <= 3000) {
			comprometimento = comprometimento.add(new BigDecimal(0.1));
		} else if (salario.intValue() >= 3001 && salario.intValue() <= 4000) {
			comprometimento = comprometimento.add(new BigDecimal(0.15));
		} else if (salario.intValue() >= 4001 && salario.intValue() <= 5000) {
			comprometimento = comprometimento.add(new BigDecimal(0.2));
		} else if (salario.intValue() >= 5001 && salario.intValue() <= 6000) {
			comprometimento = comprometimento.add(new BigDecimal(0.25));
		} else if (salario.intValue() >= 6001 && salario.intValue() <= 7000) {
			comprometimento = comprometimento.add(new BigDecimal(0.30));
		} else if (salario.intValue() >= 7001 && salario.intValue() <= 8000) {
			comprometimento = comprometimento.add(new BigDecimal(0.35));
		} else if (salario.intValue() >= 8001 && salario.intValue() <= 9000) {
			comprometimento = comprometimento.add(new BigDecimal(0.4));
		} else if (salario.intValue() >= 9001) {
			comprometimento = comprometimento.add(new BigDecimal(0.45));
		}

		valorParcelaMaximo = (salario.multiply(comprometimento));

		if (valorParcelaMaximo.doubleValue() != 0) {
			return valorParcelaMaximo;
		} else {
			throw new DivisaoPorZero();
		}
	}

	//idade da pessoa ela poderá receber apenas uma porcentagem  calculoPercentualIdade
	public BigDecimal calculoPercentualIdade(Pessoa pessoa) {

		BigDecimal porcentagem = new BigDecimal(0);

		if (pessoa.getIdade() >= 21 && pessoa.getIdade() <= 30) {
			porcentagem = porcentagem.add(new BigDecimal(1));
		} else if (pessoa.getIdade() >= 31 && pessoa.getIdade() <= 50) {
			porcentagem = porcentagem.add(new BigDecimal(0.9));
		} else if (pessoa.getIdade() >= 51 && pessoa.getIdade() <= 80) {
			porcentagem = porcentagem.add(new BigDecimal(0.7));
		} else if (pessoa.getIdade() >= 81) {
			porcentagem = porcentagem.add(new BigDecimal(0.2));
		}
		return pessoa.getSalario().multiply(porcentagem);
	}

	public BigDecimal valorMaximodaParcela(Pessoa pessoa) {

		BigDecimal salario;
		BigDecimal salarioMaximo;

		salario = calculoPercentualIdade(pessoa);

		salarioMaximo = calculoFaixaSalarial(salario);

		return salarioMaximo.setScale(2, RoundingMode.UP);

	}
}
