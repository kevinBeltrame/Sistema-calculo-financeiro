package com.CalculoFinanceiro.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Pessoa{
	
	@JsonProperty("Nome")
	@Id
	private String nome;
	
	@JsonProperty("Idade")
	private int idade;
	
	@JsonProperty("Salario")
	private BigDecimal  salario;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public BigDecimal getSalario() {
		return salario;
	}

	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}

	public Pessoa(String nome, int idade, BigDecimal salario) {
		super();
		this.nome = nome;
		this.idade = idade;
		this.salario = salario;
	}

	public Pessoa() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}  
	
   
}
