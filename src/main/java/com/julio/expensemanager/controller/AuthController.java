package com.julio.expensemanager.controller;

import com.julio.expensemanager.dto.*;
import com.julio.expensemanager.security.JwtService;
import com.julio.expensemanager.service.AuthService;

import com.julio.expensemanager.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;
	private final RefreshTokenService refreshTokenService;
	private final JwtService jwtService;
	
	@PostMapping("/register")
	public AuthResponse register(@RequestBody RegisterRequest request) {
		return authService.register(request);
	}
	
	@PostMapping("/login")
	public AuthResponse login(@RequestBody LoginRequest request) {
		return authService.login(request);
	}
	
	@PostMapping("/refresh")
	public AuthResponse refresh(@RequestBody RefreshRequest request) {
		var refreshToken = refreshTokenService.verifyToken(request.getRefreshToken());
		
		String newToken = jwtService.generateToken(refreshToken.getUser().getEmail());
		
		return AuthResponse.builder()
			.token(newToken)
			.refreshToken(refreshToken.getToken())
			.build();
	}
}