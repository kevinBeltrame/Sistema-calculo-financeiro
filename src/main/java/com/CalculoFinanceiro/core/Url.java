package com.CalculoFinanceiro.core;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
@ConfigurationProperties("url")
public class Url {
	private String dns = "http://www.mocky.io";
	private String localizacao  = "/v2/5e5ec624310000b738afd85a";
}
