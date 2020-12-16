package com.CalculoFinanceiro.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CalculoFinanceiro.domain.model.Pessoa;
import com.CalculoFinanceiro.infrastructure.repository.pessoa.PessoaRepositoryQuery;



public interface PessoaRepository extends JpaRepository<Pessoa, Long>,PessoaRepositoryQuery  {


}
