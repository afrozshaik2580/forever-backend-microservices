package com.forever.userservice.dto;

import lombok.Data;

@Data
public class CartItem {
	
	private Long productId;
	private String size;
	private double unitPrice;
	private int quantity;

}
