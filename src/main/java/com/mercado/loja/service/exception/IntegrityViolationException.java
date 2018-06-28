package com.mercado.loja.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class IntegrityViolationException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public IntegrityViolationException(String msg) {
		super(msg);
	}
	
	public IntegrityViolationException(String msg, Throwable e) {
		super(msg, e);
	}

}
