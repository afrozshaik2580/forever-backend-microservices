package com.forever.userservice.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.forever.userservice.dto.CartItem;

@FeignClient("CART-SERVICE")
public interface CartServiceClient {
	
	@GetMapping("cart/create")
	public ResponseEntity<Long> createCart(@RequestParam Long userId);
	
	@GetMapping("cart/items/{userId}")
	public ResponseEntity<List<CartItem>> getCart(@PathVariable Long userId);
	
	@PostMapping("cart/add/{userId}")
	public ResponseEntity<String> addItemstoCart(@PathVariable Long userId, @RequestBody CartItem item);
	
	@PutMapping("cart/update/{userId}")
	public ResponseEntity<String> updateCartItems(@PathVariable Long userId, @RequestBody CartItem item);

}
