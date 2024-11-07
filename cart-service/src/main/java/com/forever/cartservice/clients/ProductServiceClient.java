package com.forever.cartservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.forever.cartservice.dto.Product;

@FeignClient("PRODUCT-SERVICE")
public interface ProductServiceClient {
	
	@GetMapping("products/{id}")
	public ResponseEntity<Product> getProductDetails(@PathVariable Long id);

}
