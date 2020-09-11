package com.CalculoFinanceiro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.CalculoFinanceiro.model.Pessoa;
import com.CalculoFinanceiro.repository.PessoaRepository;
import com.CalculoFinanceiro.repository.filter.PessoaFilter;



@Service
public class PessoaService {

	@Autowired
	private  PessoaRepository pessoaRepository;
	
	public Page<Pessoa> findAll(PessoaFilter pessoaFilter, Pageable pageable) {
		return pessoaRepository.filtrar(pessoaFilter, pageable);
	}
	
	public Pessoa buscarPeloNome(String nome) {
		
		Pessoa pessoaSalva = pessoaRepository.getOne(nome);
			
		if(pessoaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
	
		return pessoaSalva;
	}

}