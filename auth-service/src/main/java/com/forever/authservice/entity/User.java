package com.forever.authservice.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	
	@Email(message = "please provide a valid email")
	@NotBlank(message = "email should not be null")
	@Column(unique = true)
	private String email;

	@NotBlank(message = "name should not be empty")
	private String name;

	@NotBlank(message = "password cannot be empty")
	@Size(min = 3, message = "password should contain minimum 3 characters")
	private String password;

	@ElementCollection
	private List<String> roles;
	
	private Long cartId;

}
