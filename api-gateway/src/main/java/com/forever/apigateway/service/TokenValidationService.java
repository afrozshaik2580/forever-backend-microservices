package com.forever.apigateway.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.forever.apigateway.entity.TokenDetails;

import reactor.core.publisher.Mono;

@Service
public class TokenValidationService {

	private final WebClient.Builder webClientBuilder;
	
	public TokenValidationService(WebClient.Builder webClientBuilder) {
		this.webClientBuilder=webClientBuilder;
	}
	
	public Mono<TokenDetails> validateToken(String token) {
		return webClientBuilder.build()
				.post().uri("http://AUTH-SERVICE/auth/validateToken").bodyValue(token)
				.retrieve().bodyToMono(TokenDetails.class)
				.onErrorReturn(new TokenDetails(null,null,null,false, null));
	}
}
