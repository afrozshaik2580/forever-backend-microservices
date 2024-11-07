package com.forever.cartservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.forever.cartservice.entity.CartItem;
import com.forever.cartservice.service.CartService;

@RestController
@RequestMapping("cart")
public class CartController {

	@Autowired
	private CartService cartService;

	@GetMapping("create")
	public ResponseEntity<Long> createCart(@RequestParam Long userId) {
		return cartService.createCart(userId);
	}

	@PostMapping("add/{userId}")
	public ResponseEntity<String> addItemstoCart(@PathVariable Long userId, @RequestBody CartItem item) {
		return cartService.addToCart(userId, item);
	}

	@PutMapping("update/{userId}")
	public ResponseEntity<String> updateCartItems(@PathVariable Long userId, @RequestBody CartItem item) {
		return cartService.updateCart(userId, item);
	}
	
	@GetMapping("items/{userId}")
	public ResponseEntity<List<CartItem>> getCart(@PathVariable Long userId) {
		return cartService.getCart(userId);
	}

	@GetMapping("clear/{userId}")
	public void clearCart(@PathVariable Long userId) {
		cartService.clearcart(userId);
	}

}
