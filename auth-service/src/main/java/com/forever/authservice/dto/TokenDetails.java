package com.forever.authservice.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenDetails {

	private Long userId;
	private String name;
	private String email;
	private boolean valid;
	private List<String> roles;

}
