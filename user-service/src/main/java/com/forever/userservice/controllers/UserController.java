package com.forever.userservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.forever.userservice.dto.Address;
import com.forever.userservice.dto.CartItem;
import com.forever.userservice.dto.CartItemDTO;
import com.forever.userservice.dto.Order;
import com.forever.userservice.dto.TokenDetails;
import com.forever.userservice.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {
	
	private final ObjectMapper objectMapper=new ObjectMapper(); 

	@Autowired
	private UserService userService;

	 
	@GetMapping("cart")
	public ResponseEntity<List<CartItemDTO>> getCartfromUserId(@RequestHeader("X-User-Details") String userDetailsString) throws JsonMappingException, JsonProcessingException {
		TokenDetails tokenDetails=objectMapper.readValue(userDetailsString, TokenDetails.class);
		return userService.getcartItemsFromUserId(tokenDetails.getUserId());
	}
	
	@PutMapping("cart/add")
	public ResponseEntity<String> addToCart(@RequestBody CartItem cartItem, @RequestHeader("X-User-Details") String userDetailsString) throws JsonMappingException, JsonProcessingException {
		TokenDetails tokenDetails=objectMapper.readValue(userDetailsString, TokenDetails.class);
		return userService.addCartItemtoUserId(tokenDetails.getUserId(), cartItem);
	}
	
	@PutMapping("cart/update")
	public ResponseEntity<String> updateCart(@RequestBody CartItem cartItem, @RequestHeader("X-User-Details") String userDetailsString) throws JsonMappingException, JsonProcessingException {
		TokenDetails tokenDetails=objectMapper.readValue(userDetailsString, TokenDetails.class);
		return userService.updateCartItemtoUserId(tokenDetails.getUserId(), cartItem);
	}
	
	@PostMapping("placeorder")
	public ResponseEntity<String> placeOrder(@RequestHeader("X-User-Details") String userDetailsString, @RequestBody Address address) throws JsonMappingException, JsonProcessingException{
		System.out.println(address);
		TokenDetails tokenDetails=objectMapper.readValue(userDetailsString, TokenDetails.class);
		return userService.placeOrder(tokenDetails.getUserId(), address);
	}
	
	@GetMapping("orders/all")
	public ResponseEntity<List<Order>> getAllOrders(@RequestHeader("X-User-Details") String userDetailsString) throws JsonMappingException, JsonProcessingException{
		TokenDetails tokenDetails=objectMapper.readValue(userDetailsString, TokenDetails.class);
		return userService.getAllOrders(tokenDetails.getUserId());
	}
	
	@GetMapping("orders/get/{orderId}")
	public ResponseEntity<Order> getOrderDetails(@PathVariable Long orderId, @RequestHeader("X-User-Details") String userDetailsString) throws JsonMappingException, JsonProcessingException{
		TokenDetails tokenDetails=objectMapper.readValue(userDetailsString, TokenDetails.class);
		return userService.getOrderDetails(orderId, tokenDetails.getUserId());
	}
	
	@DeleteMapping("orders/delete/{orderId}")
	public ResponseEntity<Order> deleteOrder(@PathVariable Long orderId, @RequestHeader("X-User-Details") String userDetailsString) throws JsonMappingException, JsonProcessingException{
		TokenDetails tokenDetails=objectMapper.readValue(userDetailsString, TokenDetails.class);
		return userService.deleteOrder(orderId, tokenDetails.getUserId());
	}
}
