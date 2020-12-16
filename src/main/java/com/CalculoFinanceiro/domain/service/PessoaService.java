package com.CalculoFinanceiro.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.CalculoFinanceiro.api.assembler.PessoaModelAssembler;
import com.CalculoFinanceiro.api.model.PessoaModel;
import com.CalculoFinanceiro.domain.model.Pessoa;
import com.CalculoFinanceiro.infrastructure.repository.PessoaRepository;
import com.CalculoFinanceiro.infrastructure.repository.filter.PessoaFilter;

@Service
public class PessoaService {

	@Autowired
	private  PessoaRepository pessoaRepository;
	
	@Autowired
	private PessoaModelAssembler pessoaModelAssembler;
	
	
	public Page<PessoaModel> findAll(PessoaFilter pessoaFilter, Pageable pageable) {
		
		Page<Pessoa> pessoaPage = pessoaRepository.filtrar(pessoaFilter, pageable);
		
		List<PessoaModel> pessoaModel = pessoaModelAssembler.toCollectionModel(pessoaPage.getContent());
		
		
		Page<PessoaModel> pessoaModelPage = new PageImpl<>(pessoaModel, pageable, 
				pessoaPage.getTotalElements());
		
		return pessoaModelPage;
	}
	
	public Pessoa buscarPessoaPeloNome(String nome) {
				
		List<Pessoa> pessoas = pessoaRepository.findAll();
	
		
		Pessoa pessoa = null;
		
		for(int i = 0; i< pessoas.size();i++) {
			if(pessoas.get(i).getNome().equalsIgnoreCase(nome)) {
				pessoa = pessoas.get(i);
				break;
			}
		}
		
		if(pessoa == null) {
			throw new EmptyResultDataAccessException(nome, 0);
		}
		
		return pessoa;
	}
}
