package com.forever.cartservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.forever.cartservice.clients.ProductServiceClient;
import com.forever.cartservice.dto.Product;
import com.forever.cartservice.entity.Cart;
import com.forever.cartservice.entity.CartItem;
import com.forever.cartservice.repository.CartItemRepository;
import com.forever.cartservice.repository.CartRepository;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private ProductServiceClient productServiceClient;
	
	public ResponseEntity<Long> createCart(Long userId) {
		Cart cart=cartRepository.save(new Cart(userId));
		return new ResponseEntity<Long>(cart.getCartId(),HttpStatus.CREATED);
	}

	public ResponseEntity<String> addToCart(Long userId, CartItem item) {
		Cart cart=cartRepository.findByUserId(userId);
		
		if(cart==null) {
			return new ResponseEntity<String>("cart not found", HttpStatus.NOT_FOUND);
		}
		
		
		List<CartItem> items=cart.getItems();
		
		for(CartItem currItem:items) {
			if(currItem.getProductId() == item.getProductId() && currItem.getSize().equals(item.getSize())) {
				currItem.setQuantity(currItem.getQuantity() + 1);
				cartRepository.save(cart);
				return new ResponseEntity<String>("added to cart", HttpStatus.OK);
			}
		}
		
		Product product;
		try {
			product=productServiceClient.getProductDetails(item.getProductId()).getBody();
			
		} catch (Exception e) {
			return new ResponseEntity<String>("product not found", HttpStatus.NOT_FOUND);
		}
		
		item.setUnitPrice(product.getPrice());
		item.setQuantity(1);
		
		items.add(item);
		cart.setItems(items);
		cartRepository.save(cart);
		
		return new ResponseEntity<String>("added to cart", HttpStatus.OK);
	}
	
	public ResponseEntity<String> updateCart(Long userId, CartItem item) {
		Cart cart=cartRepository.findByUserId(userId);
		
		if(cart==null) {
			return new ResponseEntity<String>("cart not found", HttpStatus.NOT_FOUND);
		}
		
		List<CartItem> items=cart.getItems();
		
		for(CartItem currItem:items) {
			if(currItem.getProductId() == item.getProductId() && currItem.getSize().equals(item.getSize())) {
				if(item.getQuantity()==0) {
					items.remove(currItem);
					cartItemRepository.deleteById(currItem.getId());
				}
				else {
					currItem.setQuantity(item.getQuantity());
				}
				cartRepository.save(cart);
				return new ResponseEntity<String>("updated cart", HttpStatus.OK);
			}
		}
		
		return new ResponseEntity<String>("cartitem not found", HttpStatus.NOT_FOUND);
	}


	public ResponseEntity<List<CartItem>> getCart(Long userId) {
		Cart cart=cartRepository.findByUserId(userId);
		
		List<CartItem> items=new ArrayList<>();
		
		if(cart==null) {
			return new ResponseEntity<List<CartItem>>(items,HttpStatus.NOT_FOUND);
		}	
		
		items=cart.getItems();
		return new ResponseEntity<List<CartItem>>(items,HttpStatus.OK);
	}
	

	public void clearcart(Long userId) {
		Cart cart=cartRepository.findByUserId(userId);
		cart.setItems(null);
		cartRepository.save(cart);
	}

	
}
