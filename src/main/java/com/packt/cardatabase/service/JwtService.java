package com.packt.cardatabase.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtService {
	static final long EXPIRATIONTIME = 86400000; // 1 day in ms
	static final String PREFIX = "Bearer ";
	// Generate secret key. Only for the demonstration
	// You should read it from the application configuration
	//static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	// 설정 파일에서 비밀 키를 읽어옴
	@Value("${jwt.secret}")
	private String secret;

	private Key key;

	// 비밀 키 초기화
	@PostConstruct
	public void init() {
		byte[] decodedKey = Base64.getDecoder().decode(secret);
		this.key = Keys.hmacShaKeyFor(decodedKey); // 비밀 키 생성
	}

	// Generate JWT token
	public String getToken(String username,String role) {
		String token = Jwts.builder()
			  .setSubject(username)
				.claim("role", role)  // Add role to the token
			  .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
			  .signWith(key)
			  .compact();
		return token;
  	}

	// Extract the role from the JWT token
	public String getAuthRole(HttpServletRequest request) {
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (token != null) {
			Claims claims = Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(token.replace(PREFIX, ""))
					.getBody();

			return claims.get("role", String.class);
		}

		return null;
	}

	// Get a token from request Authorization header, 
	// parse a token and get username
	public String getAuthUser(HttpServletRequest request) {
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);
	
		if (token != null) {
			Claims claims = Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(token.replace(PREFIX, ""))
					.getBody();

			String user = claims.getSubject();
			String role = claims.get("role", String.class); // Extract role from token

			if (user != null)
				return user;
		}

		return null;
	}
}
