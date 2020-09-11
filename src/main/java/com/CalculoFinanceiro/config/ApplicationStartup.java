package com.CalculoFinanceiro.config;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.reactive.function.client.WebClient;

import com.CalculoFinanceiro.model.Pessoa;
import com.CalculoFinanceiro.repository.PessoaRepository;

import reactor.core.publisher.Mono;

@Configuration
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent>  {
	
	@Autowired
	private WebClient webClient;

	@Autowired
	private  PessoaRepository pessoaRepository;
	
	private List<Pessoa> pessoa = new ArrayList<>();
	
	@Autowired
	private Url url;


	public void onApplicationEvent( ApplicationReadyEvent event) {
		buscarPessoas();
	}
	
	@Scheduled(cron = "0 0 3 * * *")
	public void buscarPessoas() {
		Mono<List<Pessoa>> monoPessoa = webClient.get().uri(url.getLocalizacao())
				.retrieve()
				.bodyToFlux(Pessoa.class)
				.collectList();
		
		
		
		pessoa = monoPessoa.block();
		pessoa.forEach(pessoaRepository::save);	
	}

}
