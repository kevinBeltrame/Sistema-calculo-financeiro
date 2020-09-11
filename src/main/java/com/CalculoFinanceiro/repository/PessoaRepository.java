package com.CalculoFinanceiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CalculoFinanceiro.model.Pessoa;
import com.CalculoFinanceiro.repository.pessoa.PessoaRepositoryQuery;



public interface PessoaRepository extends JpaRepository<Pessoa, String>,PessoaRepositoryQuery  {

}
