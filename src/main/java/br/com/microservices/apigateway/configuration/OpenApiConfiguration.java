package br.com.microservices.apigateway.configuration;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SwaggerUiConfigParameters;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class OpenApiConfiguration {

	@Bean
	@Lazy(false)
	public List<GroupedOpenApi> apiList(SwaggerUiConfigParameters config, RouteDefinitionLocator routeDefinitionLocator) {
		var definitions = routeDefinitionLocator.getRouteDefinitions().collectList().block();
		definitions.stream().filter(r -> r.getId().matches(".*-service")).forEach(r -> {
			String name = r.getId();
			config.addGroup(name);
			GroupedOpenApi.builder().pathsToMatch("/" + "/**").group(name).build();
		});

		return new ArrayList<>();
	}
}
