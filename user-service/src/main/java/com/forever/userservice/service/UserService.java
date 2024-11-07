package com.forever.userservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.forever.userservice.clients.CartServiceClient;
import com.forever.userservice.clients.OrderServiceClient;
import com.forever.userservice.clients.ProductServiceClient;
import com.forever.userservice.dto.Address;
import com.forever.userservice.dto.CartItem;
import com.forever.userservice.dto.CartItemDTO;
import com.forever.userservice.dto.Order;
import com.forever.userservice.dto.Product;


@Service
public class UserService {
	
	@Autowired
	private CartServiceClient cartServiceClient;
	
	@Autowired
	private ProductServiceClient productServiceClient;
	
	@Autowired
	private OrderServiceClient orderServiceClient;

	public ResponseEntity<String> addCartItemtoUserId(Long userId, CartItem cartItem) {
		
		ResponseEntity<String> response;
		try {
			response = cartServiceClient.addItemstoCart(userId, cartItem);
		} catch (Exception e) {
			return new ResponseEntity<String>("product not found", HttpStatus.NOT_FOUND);
		}
		
		return response;
	}

	public ResponseEntity<List<CartItemDTO>> getcartItemsFromUserId(Long userId) {
		
		List<CartItem> cartItems=cartServiceClient.getCart(userId).getBody();
		List<CartItemDTO> items=new ArrayList<CartItemDTO>();
		
		for(CartItem cartItem:cartItems) {
			CartItemDTO cartItemDTO=new CartItemDTO();
			Product product = productServiceClient.getProductDetails(cartItem.getProductId()).getBody();
			cartItemDTO.setProduct(product);
			cartItemDTO.setQuantity(cartItem.getQuantity());
			cartItemDTO.setSize(cartItem.getSize());
			items.add(cartItemDTO);
		}
		
		return new ResponseEntity<List<CartItemDTO>>(items,HttpStatus.OK);
	}

	public ResponseEntity<String> placeOrder(Long userId, Address address) {
		return orderServiceClient.placeOrder(userId, address);
	}

	public ResponseEntity<Order> getOrderDetails(Long orderId, Long userId) {
		return orderServiceClient.getOrder(orderId, userId);
	}

	public ResponseEntity<Order> deleteOrder(Long orderId, Long userId) {
		return orderServiceClient.deleteOrder(orderId,userId);
	}

	public ResponseEntity<List<Order>> getAllOrders(Long userId) {
		return orderServiceClient.getAllOrders(userId);
	}

	public ResponseEntity<String> updateCartItemtoUserId(Long userId, CartItem cartItem) {
		return cartServiceClient.updateCartItems(userId,cartItem);
	}

}
