package com.forever.orderservice.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.forever.orderservice.dto.TokenDetails;
import com.forever.orderservice.entity.Address;
import com.forever.orderservice.entity.Order;
import com.forever.orderservice.service.OrderService;

@RestController
@RequestMapping("orders")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	private final ObjectMapper objectMapper=new ObjectMapper();
	
	@GetMapping("/manageorders")
	public ResponseEntity<?> manageAllOrders(@RequestHeader("X-User-Details") String userDetails) throws JsonMappingException, JsonProcessingException{
		TokenDetails tokenDetails = objectMapper.readValue(userDetails, TokenDetails.class);
		if(!tokenDetails.getRoles().contains("ADMIN")) {
			return new ResponseEntity<String>("access denied", HttpStatus.UNAUTHORIZED);
		}
		return orderService.manageOrders();
	}
	
	@PostMapping("/manageorders")
	public ResponseEntity<?> updateOrderStatus(@RequestHeader("X-User-Details") String userDetails, @RequestBody Map<String, Object> request) throws JsonMappingException, JsonProcessingException{
		Long orderId = Long.valueOf(request.get("orderId").toString());
        String status = request.get("status").toString();
		TokenDetails tokenDetails = objectMapper.readValue(userDetails, TokenDetails.class);
		if(!tokenDetails.getRoles().contains("ADMIN")) {
			return new ResponseEntity<String>("access denied", HttpStatus.UNAUTHORIZED); 
		}
		return orderService.updateOrderStatus(orderId, status);
	}
	
	
	@PostMapping("placeorder")
	public ResponseEntity<String> placeOrder(@RequestParam Long userId, @RequestBody Address address) {
		return orderService.placeOrder(userId, address);
	}
	
	@GetMapping("get/all")
	public ResponseEntity<List<Order>> getAllOrders(@RequestParam Long userId) {
		return orderService.getAllOrders(userId);
	}
	
	@GetMapping("get/{orderId}")
	public ResponseEntity<Order> getOrder(@PathVariable Long orderId, @RequestParam Long userId) {
		return orderService.getOrder(orderId, userId);
	}
	
	@DeleteMapping("delete/{orderId}")
	public ResponseEntity<Order> deleteOrder(@PathVariable Long orderId, @RequestParam Long userId) {
		return orderService.deleteOrder(orderId, userId);
	}
	
}
