package com.forever.orderservice.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.forever.orderservice.entity.OrderItem;

@FeignClient("CART-SERVICE")
public interface CartServiceClient {
	
	@GetMapping("cart/items/{userId}")
	public ResponseEntity<List<OrderItem>> getCart(@PathVariable Long userId);
	
	@GetMapping("cart/clear/{userId}")
	public void clearCart(@PathVariable Long userId);
	
}
