package com.CalculoFinanceiro.core.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties("configuracao.cors")
@Component
@Getter
@Setter
public class ConfiguracaoCors {
	
	private String mapping = "/api/credito/**";

	private String origins = "http://localhost:4200";

}
