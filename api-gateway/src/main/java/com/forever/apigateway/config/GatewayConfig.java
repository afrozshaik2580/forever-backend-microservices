package com.forever.apigateway.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import com.forever.apigateway.filter.AuthenticationFilter;

@Configuration
public class GatewayConfig {

	@Bean
	public RouteLocator apiGatewayRouter(RouteLocatorBuilder builder, AuthenticationFilter authenticationFilter) {

		return builder.routes()
				.route(p -> p.path("/auth/**")
						.uri("lb://AUTH-SERVICE")
						)
				.route(p -> p.path("/user/**")
						.filters(f -> f
								.filter(authenticationFilter.apply(new AuthenticationFilter.Config()))
								)
						.uri("lb://USER-SERVICE")
						)
				.route(p -> p.path("/products")
	                    .and()
	                    .method("GET")
	                    .uri("lb://PRODUCT-SERVICE")
	            )
				.route(p -> p.path("/products/**")
						.filters(f -> f
								.filter(authenticationFilter.apply(new AuthenticationFilter.Config()))
								)
						.uri("lb://PRODUCT-SERVICE")
						)
				.route(p -> p.path("/cart/**")
						.filters(f -> f
								.filter(authenticationFilter.apply(new AuthenticationFilter.Config()))
								)
						.uri("lb://CART-SERVICE")
						)
				.route(p -> p.path("/orders/**")
						.filters(f -> f
								.filter(authenticationFilter.apply(new AuthenticationFilter.Config()))
								)
						.uri("lb://ORDER-SERVICE")
						)
				.build();
	}

	@Bean
	@LoadBalanced
	public WebClient.Builder webClienBuilder() {
		return WebClient.builder();
	}

}
