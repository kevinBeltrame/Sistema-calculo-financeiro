package com.CalculoFinanceiro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.CalculoFinanceiro.core.Url;


@SpringBootApplication
public class SistemaCalculoFinanceiroApiApplication {
	
	@Autowired
	private Url url;
	
	@Bean
	public WebClient webClient(WebClient.Builder builder) {
		
		String protocoloDNS = url.getDns();
		
		return builder
				.baseUrl(protocoloDNS)
		        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(SistemaCalculoFinanceiroApiApplication.class, args);
	}

}
