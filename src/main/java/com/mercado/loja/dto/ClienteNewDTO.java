package com.mercado.loja.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.mercado.loja.service.annotation.ClienteInsert;

@ClienteInsert
public class ClienteNewDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	// Dados cliente
	@NotEmpty(message = "O campo nome obrigatório")
	@Length(min = 4, max = 80, message = "O nome deve ter de 5 a 80 caractéris")
	private String nome;
	
	@NotEmpty(message = "O campo nome obrigatório")
	@Email(message = "Email inválido")
	private String email;
	
	@NotEmpty(message = "O campo cpfOuCnpj obrigatório")
	private String cpfOuCnpj;
	private Integer tipoCliente;

	// Endereço
	@NotEmpty(message = "O campo logradouro obrigatório")
	private String logradouro;
	
	@NotEmpty(message = "O campo número obrigatório")
	private String numero;
	private String complemento;
	private String bairro;
	@NotEmpty(message = "O campo cep obrigatório")
	private String cep;
	private Integer cidade;

	// telefones
	@NotEmpty(message = "Primeiro telofone é obrigatório")
	private String telefone1;
	private String telefone2;
	private String telefone3;

	public ClienteNewDTO() {
		super();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public Integer getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(Integer tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Integer getCidade() {
		return cidade;
	}

	public void setCidade(Integer cidade) {
		this.cidade = cidade;
	}

	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public String getTelefone3() {
		return telefone3;
	}

	public void setTelefone3(String telefone3) {
		this.telefone3 = telefone3;
	}
}
