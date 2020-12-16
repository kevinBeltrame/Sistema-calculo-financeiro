package com.CalculoFinanceiro.api.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PessoaModel {
	private int idade;
	
	private String nome;
		
	private BigDecimal  salario;
}
