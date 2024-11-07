package com.forever.userservice.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.forever.userservice.dto.Address;
import com.forever.userservice.dto.Order;

@FeignClient("ORDER-SERVICE")
public interface OrderServiceClient {

	@PostMapping("orders/placeorder")
	public ResponseEntity<String> placeOrder(@RequestParam Long userId, @RequestBody Address address);
	
	@GetMapping("orders/get/{orderId}")
	public ResponseEntity<Order> getOrder(@PathVariable Long orderId, @RequestParam Long userId);
	
	@GetMapping("orders/get/all")
	public ResponseEntity<List<Order>> getAllOrders(@RequestParam Long userId);
	
	@DeleteMapping("orders/delete/{orderId}")
	public ResponseEntity<Order> deleteOrder(@PathVariable Long orderId, @RequestParam Long userId);
}
