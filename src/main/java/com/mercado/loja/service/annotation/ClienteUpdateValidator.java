package com.mercado.loja.service.annotation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.mercado.loja.dto.ClienteDTO;
import com.mercado.loja.model.Cliente;
import com.mercado.loja.repository.ClienteRepository;
import com.mercado.loja.resource.exception.ValidationMessageError;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	HttpServletRequest request;
	
	@Override
	public void initialize(ClienteUpdate ann) {
	}

	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		List<ValidationMessageError> list = new ArrayList<>();
		
		@SuppressWarnings("unchecked")
		Map<String, String> idClinte = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer id = Integer.parseInt(idClinte.get("id"));
		
		Cliente cliente = clienteRepository.findByEmail(objDto.getEmail());
		if (cliente != null && !cliente.getId().equals(id)) {
			list.add(new ValidationMessageError("email", "Email já está em uso por outro usuário"));
		}
		for (ValidationMessageError e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
