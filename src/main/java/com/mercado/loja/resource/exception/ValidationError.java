package com.mercado.loja.resource.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<ValidationMessageError> errors = new ArrayList<>();

	public ValidationError(Integer status, String msg, Long timeStamp) {
		super(status, msg, timeStamp);
	}

	public List<ValidationMessageError> getErrors() {
		return errors;
	}

	public void addErrors(String field, String message) {
		this.errors.add(new ValidationMessageError(field, message));
	}

}
