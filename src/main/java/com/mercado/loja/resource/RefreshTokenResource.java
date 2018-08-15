package com.mercado.loja.resource;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercado.loja.security.JWTUtil;
import com.mercado.loja.security.UserSS;
import com.mercado.loja.service.UserService;

@RestController
@RequestMapping("/auth")
public class RefreshTokenResource {
	@Autowired
	private JWTUtil jwtUtil;
	
	//Usamos post pq os demais metodos são idempotentes
	@PostMapping("/refresh_token")
	public ResponseEntity<Void> refresh(HttpServletResponse response){
		UserSS user = UserService.authenticated();
		response.addHeader("Authorization", "Bearer " + jwtUtil.generateToken(user.getUsername()));
		return ResponseEntity.noContent().build();
	}
}
