package com.CalculoFinanceiro.resource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CalculoFinanceiro.exceptionHandler.PessoaExceptionHandler.Erro;
import com.CalculoFinanceiro.model.Pessoa;
import com.CalculoFinanceiro.model.PessoaCredito;
import com.CalculoFinanceiro.repository.filter.PessoaFilter;
import com.CalculoFinanceiro.service.PessoaCreditoService;
import com.CalculoFinanceiro.service.PessoaService;
import com.CalculoFinanceiro.service.exception.DivisaoPorZero;


@RequestMapping("/api/credito")
@RestController
public class PessoaResource {
	
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private PessoaCreditoService pessoaCreditoService;
	
		
	@GetMapping
	public Page<Pessoa> pesquisar(PessoaFilter pessoaFilter, Pageable pageable) {
		return pessoaService.findAll(pessoaFilter, pageable);
	}

	@GetMapping("/{nome}/{valorPedido}")
	public ResponseEntity<PessoaCredito> buscar(@Valid @PathVariable(required = true) String nome, @PathVariable BigDecimal valorPedido)  {
		String [] nomeESobrenome = nome.split("(?=\\p{Upper})");
		String BuscarPorNome = "";
		for(int i = 0; i<nomeESobrenome.length; i++) {
			BuscarPorNome += nomeESobrenome[i] + " ";

		}
		nome = nomeESobrenome[0] + " " + nomeESobrenome[1];
		
		Pessoa pessoa = pessoaService.buscarPeloNome(BuscarPorNome.trim());	

		return pessoaCreditoService.obterPessoaCredito(pessoa,valorPedido);
	}

	
	@ExceptionHandler({ DivisaoPorZero.class })
	public ResponseEntity<Object> handleDivisaoPorZero(DivisaoPorZero ex) {
		String mensagemUsuario = messageSource.getMessage("recurso.pessoa-nao-pode-fazer-operacao", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString() + "\n vai resultar em uma divisao por 0";
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}
}