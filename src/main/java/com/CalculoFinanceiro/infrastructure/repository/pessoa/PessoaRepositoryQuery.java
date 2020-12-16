package com.CalculoFinanceiro.infrastructure.repository.pessoa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.CalculoFinanceiro.domain.model.Pessoa;
import com.CalculoFinanceiro.infrastructure.repository.filter.PessoaFilter;

public interface PessoaRepositoryQuery {

	public Page<Pessoa> filtrar(PessoaFilter pessoaFilter, Pageable pageable);

}
