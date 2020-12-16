package com.CalculoFinanceiro.core.property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.netty.handler.codec.http.HttpMethod;

@Configuration
@Profile("dev")
public class FinanceiroApiProperty implements WebMvcConfigurer {

	@Autowired
	private ConfiguracaoCors configuracaoCors;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping(configuracaoCors.getMapping())
			.allowedOrigins(configuracaoCors.getOrigins())
			.allowedMethods(HttpMethod.GET.name());
	}

}
