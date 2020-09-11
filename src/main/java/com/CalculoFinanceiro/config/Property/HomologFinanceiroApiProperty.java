package com.CalculoFinanceiro.config.Property;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.netty.handler.codec.http.HttpMethod;

@Configuration
@Profile("homolog")
public class HomologFinanceiroApiProperty  implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		 registry.addMapping("/api/credito/**")
			.allowedOrigins("http://localhost:4200")
			.allowedMethods(HttpMethod.GET.name());
	}
}
