package com.forever.authservice.jwt;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private String SECRETKEY = "n765edcguiolkbvfde34rfvbnji98765ecfgy7654wsxfghj987yg";

	private int jwtExpTimeInMs = 86400000; 
	SecretKey signingKey = Keys.hmacShaKeyFor(SECRETKEY.getBytes());

	public String generateToken(String username) {

		return Jwts.builder().claims(null).subject(username).issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + jwtExpTimeInMs)).signWith(signingKey).compact();
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().verifyWith(signingKey).build().parseSignedClaims(token).getPayload();
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public boolean validateToken(String token, UserDetails userDetails) {

		final String username = extractUsername(token);

		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {

		Date expirationDate = extractClaim(token, Claims::getExpiration);
		return expirationDate.before(new Date());
	}

}
