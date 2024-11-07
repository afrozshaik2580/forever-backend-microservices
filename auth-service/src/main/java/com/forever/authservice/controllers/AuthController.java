package com.forever.authservice.controllers;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forever.authservice.dto.LoginDto;
import com.forever.authservice.dto.TokenDetails;
import com.forever.authservice.entity.User;
import com.forever.authservice.jwt.JwtService;
import com.forever.authservice.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtService jwtService;

	@PostMapping("register")
	public ResponseEntity<String> registerUser(@RequestBody @Valid User user, BindingResult result) {

		if (result.hasErrors()) {
			return new ResponseEntity<String>(result.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
		}

		return authService.registerUser(user); 

	}

	@PostMapping("login")
	public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
		Authentication authentication;
		try {
			authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
			
		} catch (Exception e) {
			return new ResponseEntity<String>("invalid email or password", HttpStatus.BAD_REQUEST);
		}

		if (authentication.isAuthenticated()) {			
			return new ResponseEntity<String>(jwtService.generateToken(loginDto.getEmail()), HttpStatus.OK);
		}
		return new ResponseEntity<String>("invalid email or password", HttpStatus.BAD_REQUEST);

	}

	@PostMapping("validateToken")
	public TokenDetails validateToken(@RequestBody String token) {
		System.out.println(token);
		return authService.validateToken(token);
	}

	@GetMapping("user/{email}")
	public ResponseEntity<User> getUser(@PathVariable String email, HttpServletRequest request) {
		String token = authService.getToken(request);

		TokenDetails tokenDetails = validateToken(token);
		boolean isValidToken = tokenDetails.isValid();

		List<String> roles = new ArrayList<>();

		if (isValidToken) {
			roles = tokenDetails.getRoles();
		}
		if (roles.contains("ADMIN")) {
			return authService.getUserByEmail(email);
		}
		return new ResponseEntity<User>(new User(), HttpStatus.FORBIDDEN);
	}

	@GetMapping("test")
	public String test(HttpServletRequest request,
			@RequestHeader(value = "X-Custom-Header", required = false, defaultValue = "defaultvalue") String header) {
		Enumeration<String> headerNames = request.getHeaderNames();

		while (headerNames.hasMoreElements()) {
			String headername = headerNames.nextElement();
			System.out.println(headername + ":" + request.getHeader(headername));
		}
		return "header is " + header;
	}

}
