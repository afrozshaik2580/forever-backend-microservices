package com.forever.cartservice.dto;


import lombok.Data;

@Data
public class Product {
	
	private long id;
	private String name;
	private String description;
	private double price;

}
