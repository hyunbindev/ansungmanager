package com.ansung.ansung.utils;

import java.util.Collections;
import java.util.Date;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.ansung.ansung.constant.UserRole;
import com.ansung.ansung.constant.exception.CommonExceptionEnum;
import com.ansung.ansung.exception.CommonApiException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {
	private final Environment environment;
	private static String ACCESS_TOKEN_SECRET_KEY;
	private static Long ACCESS_TOKEN_EXPRIATION;
	private static String REFRESH_TOKEN_SERCRET_KEY;
	private static Long REFRESH_TOKEN_EXPRIATION;
	@PostConstruct
	public void init() {
		ACCESS_TOKEN_SECRET_KEY = environment.getProperty("jwt.access.secret");
		ACCESS_TOKEN_EXPRIATION = Long.parseLong(environment.getProperty("jwt.access.expiration", "0"));
		REFRESH_TOKEN_SERCRET_KEY = environment.getProperty("jwt.refresh.secret");
		REFRESH_TOKEN_EXPRIATION = Long.parseLong(environment.getProperty("jwt.refresh.expiration", "6048000000"));
	}
	
	public static String createAcessToken(String managerId) {
		return Jwts.builder()
				.setSubject(managerId)
				.claim("role", UserRole.ROLE_MANAGER)
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
	
	public static String createRefeshToken(String managerId) {
		return Jwts.builder()
				.setSubject(managerId)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPRIATION)) 
				.signWith(SignatureAlgorithm.HS256, REFRESH_TOKEN_SERCRET_KEY)
				.compact();
	}
	
	public static String decodeRefreshToken(String token) {
		return Jwts.parser()
				.setSigningKey(REFRESH_TOKEN_SERCRET_KEY)
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
	public static boolean validateToken(TokenType tokentype,String token) {
		try {
			Claims claims = Jwts.parser()
                    .setSigningKey(tokentype.KEY)
                    .parseClaimsJws(token)
                    .getBody();
			return !claims.getExpiration().before(new Date());
		}catch(ExpiredJwtException e) {
			throw e;
		}catch(JwtException e) {
			throw e;
		}
	}
	public static String reIssueAccessToken(String refreshToken) {
		try {
			Claims claims = Jwts.parser()
					.setSigningKey(TokenType.REFRESH_TOKEN.KEY)
					.parseClaimsJws(refreshToken)
					.getBody();
			String managerId = claims.getSubject();
			return createAcessToken(managerId);
		}catch(JwtException e) {
			throw new CommonApiException(CommonExceptionEnum.UNAUTHROIZED);
		}
	}
	
	public static Authentication getAuthentication(String accessToken) {
		Claims claims = Jwts.parser()
				.setSigningKey(TokenType.ACCESS_TOKEN.KEY)
				.parseClaimsJws(accessToken)
				.getBody();
		User principal = new User(claims.getSubject(),"",Collections.singletonList(UserRole.ROLE_MANAGER.getAuthority()));
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal, "", principal.getAuthorities());
		return authentication;
	}
	
	public static enum TokenType{
		ACCESS_TOKEN(ACCESS_TOKEN_SECRET_KEY,ACCESS_TOKEN_EXPRIATION),
		REFRESH_TOKEN(REFRESH_TOKEN_SERCRET_KEY,REFRESH_TOKEN_EXPRIATION);
		private final String KEY;
		private final Long EXPRIATION;
		TokenType(String key , Long expiration) {
			this.KEY = key;
			this.EXPRIATION = expiration;
		}
	}
}
