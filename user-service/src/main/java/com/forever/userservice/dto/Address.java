package com.forever.userservice.dto;

import lombok.Data;

@Data
public class Address {

	private String firstName;
	private String lastName;
	private String email;
	private String street;
	private String city;
	private String state;
	private Long zipcode;
	private String country;
	private String phone;
}
