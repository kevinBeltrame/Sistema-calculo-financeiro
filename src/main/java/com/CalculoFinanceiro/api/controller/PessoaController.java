package com.CalculoFinanceiro.api.controller;

import java.math.BigDecimal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CalculoFinanceiro.api.model.PessoaCreditoModel;
import com.CalculoFinanceiro.api.model.PessoaModel;
import com.CalculoFinanceiro.domain.model.Pessoa;
import com.CalculoFinanceiro.domain.service.PessoaCreditoService;
import com.CalculoFinanceiro.domain.service.PessoaService;
import com.CalculoFinanceiro.infrastructure.repository.filter.PessoaFilter;

@RequestMapping("/api/credito")
@RestController
public class PessoaController {


	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private PessoaCreditoService pessoaCreditoService;

	@GetMapping
	public Page<PessoaModel> pesquisar(PessoaFilter pessoaFilter, Pageable pageable) {

		return pessoaService.findAll(pessoaFilter, pageable);
	}

	@GetMapping("/{nome}/{valorPedido}")
	public ResponseEntity<PessoaCreditoModel> buscar(@Valid @PathVariable(required = true) String nome,
			@PathVariable BigDecimal valorPedido) {
				
		String[] nomeESobrenome = nome.split("(?=\\p{Upper})");
		String buscarPorNome = "";
		for (int i = 0; i < nomeESobrenome.length; i++) {
			buscarPorNome += nomeESobrenome[i] + " ";
		}

		Pessoa pessoa = pessoaService.buscarPessoaPeloNome(buscarPorNome.trim());
		
		return pessoaCreditoService.obterPessoaCredito(pessoa, valorPedido);
	}

}