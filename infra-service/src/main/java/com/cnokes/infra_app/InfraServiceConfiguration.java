package com.cnokes.infra_app;

import java.io.IOException;

import com.cnokes.framework.web.TraceFilter;
import com.cnokes.framework.web.exception.RestExceptionControllerAdvice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.ForwardedHeaderFilter;
import org.springframework.web.server.adapter.ForwardedHeaderTransformer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableAspectJAutoProxy
public class InfraServiceConfiguration {

	@Configuration
	@ComponentScan(basePackageClasses = { RestExceptionControllerAdvice.class, TraceFilter.class })
	public class ExternalConfig {

	}


	@Bean
	@Order(0)
	public ForwardedHeaderFilter forwardedHeaderFilter() {
		return new ForwardedHeaderFilter();
	}

	@Bean
	public ForwardedHeaderTransformer forwardedHeaderTransformer() {
		return new ForwardedHeaderTransformer();
	}

	@Bean
	public WebMvcConfigurer webMvcConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addViewControllers(ViewControllerRegistry registry) {
				registry.addRedirectViewController("/", "swagger-ui.html");
			}

			// @Override
			// public void addCorsMappings(CorsRegistry registry) {
			// 	// TODO cors strategy and implement
			// 	registry.addMapping("/**");//.allowedOriginPatterns("http://localhost:*");
			// }
		};
	}

	public static void main(String[] args) throws IOException {
		SpringApplication.run(InfraServiceConfiguration.class, args);
	}
}
