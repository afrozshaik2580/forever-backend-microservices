package com.forever.apigateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.forever.apigateway.service.TokenValidationService;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

	private final TokenValidationService tokenValidationService;
	private final ObjectMapper objectMapper=new ObjectMapper();

	public AuthenticationFilter(TokenValidationService tokenValidationService) {
		super(Config.class);
		this.tokenValidationService = tokenValidationService;
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			
			String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
			
		
			if (authHeader == null || !authHeader.startsWith("Bearer ")) {
				exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
				return exchange.getResponse().setComplete();
			}

			String token = authHeader.substring(7);
			
			
			return tokenValidationService.validateToken(token).flatMap(details -> {
				if (!details.isValid()) {
					System.out.println("token is not validdddddddddddddddddddd");
					exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
					return exchange.getResponse().setComplete();
				}
				try {
					String userdetailsjson=objectMapper.writeValueAsString(details);
					ServerHttpRequest modifiedRequest=exchange.getRequest().mutate()
							.header("X-User-Details", userdetailsjson)
							.build();
					
					ServerWebExchange modifiedExchange=exchange.mutate().request(modifiedRequest).build();
					return chain.filter(modifiedExchange);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				return chain.filter(exchange);
			});
		};
	}

	public static class Config {

	}

}
