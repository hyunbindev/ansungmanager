package com.ansung.ansung.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ansung.ansung.data.auth.TokenDto;
import com.ansung.ansung.service.AuthService;
import com.google.common.net.HttpHeaders;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;
	@GetMapping
	public ResponseEntity<Void> getAuthToken(
			@RequestParam(value = "password" ,required = true)String password,
			@RequestParam(value = "name" ,required = true)String name
			,HttpServletResponse response){
		TokenDto tokens= authService.getManagerToken(name, password);
		
		ResponseCookie refreshCookie = ResponseCookie.from("refresh_token" , tokens.getRefreshToken())
				.httpOnly(true)
				.secure(false)//개발환경에서 false ->Https 통신에서만 전송 해제
				.path("/")
				.maxAge(7 * 24 *60 *60)
				.sameSite("None")
				.build();
		response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
		return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).header("Authorization",tokens.getAccessToken()).build();
	}
}
