package com.forever.authservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.forever.authservice.clients.CartServiceClient;
import com.forever.authservice.dto.TokenDetails;
import com.forever.authservice.entity.User;
import com.forever.authservice.jwt.JwtService;
import com.forever.authservice.repository.UserRepo;
import com.forever.authservice.security.MyUserDetailsService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthService {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private CartServiceClient cartServiceClient;

	public ResponseEntity<String> registerUser(User user) {
		User existingUser = userRepo.findByEmail(user.getEmail());
		if (existingUser != null) {
			return new ResponseEntity<String>("user already exixts", HttpStatus.BAD_REQUEST);
		}

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		existingUser = userRepo.save(user);
		
		Long cartId=cartServiceClient.createCart(user.getUserId()).getBody();
		user.setCartId(cartId);
		userRepo.save(user);
		
		return new ResponseEntity<String>("user added with email " + user.getEmail(), HttpStatus.CREATED);
	}

	public ResponseEntity<User> getUserByEmail(String email) {
		User user = userRepo.findByEmail(email);
		if (user != null) {
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}

		return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);

	}

	public TokenDetails validateToken(String token) {

		String username = null;
		List<String> roles = new ArrayList<String>();

		try {
			username = jwtService.extractUsername(token);
		} catch (Exception e) {
			return new TokenDetails(null,"", username, false, roles);
		}

		if (username != null) {
			UserDetails userDetails = applicationContext.getBean(MyUserDetailsService.class)
					.loadUserByUsername(username);

			if (jwtService.validateToken(token, userDetails)) {
				roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
						.collect(Collectors.toList());
				User user=userRepo.findByEmail(username);
				return new TokenDetails(user.getUserId(),user.getName(), username, true, roles);
			}

		}

		return new TokenDetails(null,"", username, false, roles);
	}

	public String getToken(HttpServletRequest request) {

		String authHeader = request.getHeader("Authorization");
		String token = null;

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
		}

		return token;
	}

}
