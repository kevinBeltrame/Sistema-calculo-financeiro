package com.CalculoFinanceiro.config;

import org.springframework.stereotype.Component;

@Component
public class Url {
	
	private final String protocolo = "http://";

	private final String DNS = "www.mocky.io";
	
	private final String localizacao = "/v2/5e5ec624310000b738afd85a";

	public String getProtocolo() {
		return protocolo;
	}

	public String getDNS() {
		return DNS;
	}

	public String getLocalizacao() {
		return localizacao;
	}	
	
}
