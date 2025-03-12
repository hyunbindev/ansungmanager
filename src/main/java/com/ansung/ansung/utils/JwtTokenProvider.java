package com.ansung.ansung.utils;

import java.util.Date;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
	private final Environment environment;
	private static String ACCESS_TOKEN_SECRET_KEY;
	private static Long ACCESS_TOKEN_EXPRIATION;
	@PostConstruct
	public void init() {
		ACCESS_TOKEN_SECRET_KEY = environment.getProperty("jwt.access.secret");
		ACCESS_TOKEN_EXPRIATION = Long.parseLong(environment.getProperty("jwt.access.expiration", "604800000")); 
	}
	
	public static String createAcessToken(String payLoad) {
		return Jwts.builder()
				.setSubject(payLoad)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPRIATION)) 
				.signWith(SignatureAlgorithm.HS256, ACCESS_TOKEN_SECRET_KEY)
				.compact();
	}
	public static String decodeAcessToken(String token) {
		return Jwts.parser()
				.setSigningKey(ACCESS_TOKEN_SECRET_KEY)
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
}
