package com.ansung.ansung.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ansung.ansung.utils.JwtTokenProvider;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@GetMapping
	public ResponseEntity<String> getAuthToken(@RequestParam(value = "password" ,required = true)String password){
		
		return ResponseEntity.ok(JwtTokenProvider.createAcessToken(password));
	}
}
