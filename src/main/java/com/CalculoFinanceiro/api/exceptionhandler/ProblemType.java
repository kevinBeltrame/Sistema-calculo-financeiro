package com.CalculoFinanceiro.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

	ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
	PESSOA_NAO_ENCONTRADA("/pessoa-nao-encontrada","Pessoa informada não foi posivel encontrada na base de dados"),
	IDADE_INFERIOR("/idade-inferior","Idade Inferior a 21"),
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
	VALOR_INFORMADO_NAO_ESPERADO("/valor-informado-nao-esperado","Valor informado não corresponde ao que se espera"),
	ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio");
	
	private String title;
	private String uri;
	
	ProblemType(String path, String title) {
		this.uri = "http://localhost:8080" + path;
		this.title = title;
	}
	
}
