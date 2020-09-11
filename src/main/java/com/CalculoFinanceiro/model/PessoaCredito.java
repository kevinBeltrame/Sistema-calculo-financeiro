package com.CalculoFinanceiro.model;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;

public class PessoaCredito {
	
	private String nome;

	private BigDecimal salario;

	@NotBlank
	private BigDecimal valorPedido;

	private BigDecimal  valorEmprestado;

    private int quantidadeParcelas;

    private BigDecimal  valorParcela;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getSalario() {
		return salario;
	}

	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}

	public BigDecimal getValorPedido() {
		return valorPedido;
	}

	public void setValorPedido(BigDecimal valorPedido) {
		this.valorPedido = valorPedido;
	}

	public BigDecimal getValorEmprestado() {
		return valorEmprestado;
	}

	public void setValorEmprestado(BigDecimal valorEmprestado) {
		this.valorEmprestado = valorEmprestado;
	}

	public int getQuantidadeParcelas() {
		return quantidadeParcelas;
	}

	public void setQuantidadeParcelas(int quantidadeParcelas) {
		this.quantidadeParcelas = quantidadeParcelas;
	}

	public BigDecimal getValorParcela() {
		return valorParcela;
	}

	public void setValorParcela(BigDecimal valorParcela) {
		this.valorParcela = valorParcela;
	}

	public PessoaCredito() {
	}

	public PessoaCredito(String nome, BigDecimal salario, BigDecimal valorPedido, BigDecimal valorEmprestado,
			int quantidadeParcelas, BigDecimal valorParcela) {
		this.nome = nome;
		this.salario = salario;
		this.valorPedido = valorPedido;
		this.valorEmprestado = valorEmprestado;
		this.quantidadeParcelas = quantidadeParcelas;
		this.valorParcela = valorParcela;
	}
}
