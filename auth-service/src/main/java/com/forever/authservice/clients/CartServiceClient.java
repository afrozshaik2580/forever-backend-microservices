package com.forever.authservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("CART-SERVICE")
public interface CartServiceClient {
	
	@GetMapping("cart/create")
	public ResponseEntity<Long> createCart(@RequestParam Long userId);

}
