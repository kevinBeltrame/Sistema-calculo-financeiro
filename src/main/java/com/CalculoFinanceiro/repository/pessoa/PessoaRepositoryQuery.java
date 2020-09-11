package com.CalculoFinanceiro.repository.pessoa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.CalculoFinanceiro.model.Pessoa;
import com.CalculoFinanceiro.repository.filter.PessoaFilter;

public interface PessoaRepositoryQuery {

	public Page<Pessoa> filtrar(PessoaFilter pessoaFilter, Pageable pageable);

}
