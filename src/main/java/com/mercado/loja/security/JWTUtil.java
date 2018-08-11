package com.mercado.loja.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {
	@Value("${security.jwt.secret}")
	private String secret;
	
	@Value("${security.jwt.expiration}")
	private Long milis;
	
	public String generateToken(String userNome) {
		return Jwts.builder()
				.setSubject(userNome)
				.setExpiration(new Date(System.currentTimeMillis() + milis))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
	}
}
