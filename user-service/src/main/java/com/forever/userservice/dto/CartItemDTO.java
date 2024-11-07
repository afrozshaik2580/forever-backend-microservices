package com.forever.userservice.dto;

import lombok.Data;


@Data
public class CartItemDTO {
	
	private Product product;
	private int quantity;
	private String size;

}
