package com.CalculoFinanceiro.api.model;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class PessoaCreditoModel {
	
	private String nome;

	private BigDecimal salario;

	@NotBlank
	@Digits(integer = 6,fraction = 2)
	private BigDecimal valorPedido;

	private BigDecimal  valorEmprestado;

    private int quantidadeParcelas;

    private BigDecimal  valorParcela;

}
