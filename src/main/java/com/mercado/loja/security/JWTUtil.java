package com.mercado.loja.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
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

	public boolean tokenValido(String token) {
		Claims claims = getClaims(token);
		
		if(claims != null) {
			String username = claims.getSubject();
			Date date = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());
			return username != null &&  date != null && now.before(date);
		}
		return false;
	}

	private Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			return null;
		}
	}

	public String getUsername(String token) {
		String user = getClaims(token).getSubject(); 
		return  user;
	}
}
