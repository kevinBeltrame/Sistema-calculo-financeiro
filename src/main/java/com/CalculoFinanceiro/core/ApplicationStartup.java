package com.CalculoFinanceiro.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.CalculoFinanceiro.domain.model.Pessoa;
import com.CalculoFinanceiro.infrastructure.repository.PessoaRepository;

import reactor.core.publisher.Mono;

@Component
@EnableScheduling
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	private WebClient webClient;

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private Url url;

	private List<Pessoa> pessoas;

	long id = 0;

	public void onApplicationEvent(ApplicationReadyEvent event) {
		buscarPessoas();
		salvaPessoas();
	}

	public void salvaPessoas() {
		pessoas.forEach(pessoa -> {
			pessoa.setId(geradorDeId(pessoas.size()));
			pessoaRepository.save(pessoa);

		});
	}

	public void buscarPessoas() {

		if (pessoas != null)
			pessoas.clear();

		Mono<List<Pessoa>> monoPessoa = webClient.get().uri(url.getLocalizacao()).retrieve().bodyToFlux(Pessoa.class)
				.collectList();

		pessoas = monoPessoa.block();

	}

	public Long geradorDeId(int tamanhoDaLista) {
		id++;
		if (id <= tamanhoDaLista)
			return id;
		else {
			id = 0;
			return geradorDeId(tamanhoDaLista);
		}
	}

	@Scheduled(fixedDelay = 1000 * 60)
	public void verificaAlteracaoDePessoa() {

		buscarPessoas();

		if (pessoaRepository.count() != 0) {

			for (int i = 0; i < pessoas.size(); i++) {

				Pessoa pessoaNova = pessoas.get(i);
				pessoaNova.setId((long) i);

				if (!pessoaRepository.existsById((long) i)) {
					pessoaRepository.save(pessoaNova);
				}

				Pessoa pessoaBuscada = pessoaRepository.findById((long) i).get();

				double possuiDiferencaSalario = pessoaNova.getSalario().doubleValue()
						- pessoaBuscada.getSalario().doubleValue();

				if (possuiAlteracao(pessoaNova, pessoaBuscada, possuiDiferencaSalario)) {
					pessoas.get(i).setId(pessoaRepository.findById((long) i).get().getId());
					pessoaRepository.save(pessoas.get(i));
				}
			}
			
			if(pessoas.size()<= pessoaRepository.count()) {
				for (long i = pessoas.size(); i <= pessoaRepository.count(); i++) {
					if(pessoaRepository.existsById(i))
						pessoaRepository.deleteById(i);
				}
			}
		}

	}

	private boolean possuiAlteracao(Pessoa pessoaNova, Pessoa pessoaBuscada, double isDiferençaSalario) {
		return !pessoaNova.equals(pessoaBuscada) || isDiferençaSalario != (0.0);
	}

}
