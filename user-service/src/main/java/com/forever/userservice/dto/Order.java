package com.forever.userservice.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Order {

	private Long orderId;
	private Long userId;
	private LocalDateTime orderDate;
	private String status;
	private String paymentMethod;

	private List<CartItem> orderItems; 

	private double totalPrice;

}
