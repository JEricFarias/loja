package com.mercado.loja.service.annotation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.mercado.loja.dto.ClienteNewDTO;
import com.mercado.loja.model.enums.TipoCliente;
import com.mercado.loja.repository.ClienteRepository;
import com.mercado.loja.resource.exception.ValidationMessageError;
import com.mercado.loja.service.annotation.util.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	@Autowired
	ClienteRepository clienteRepository;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<ValidationMessageError> list = new ArrayList<>();

		// inclua os testes aqui, inserindo erros na lista
		if (objDto.getTipoCliente().equals(TipoCliente.PESSOAFISICA.getCodigo()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new ValidationMessageError("cpfOuCnpj", "CPF inválido"));
		}
		if (objDto.getTipoCliente().equals(TipoCliente.PESSOAJURIDICA.getCodigo()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new ValidationMessageError("cpfOuCnpj", "CNPJ inválido"));
		}
		if (clienteRepository.findByEmail(objDto.getEmail()) != null) {
			list.add(new ValidationMessageError("email", "Email já cadastrado"));
		}
		for (ValidationMessageError e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
