package br.unesp.exemplo.api.valueobjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Value Object para comunicação REST para entidade externa Pessoa
 * 
 * @author Alessandro Moraes
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("Pessoa")
public class PessoaVO {

	private String nome;
	private String email;
	private String cpf;
	
	public PessoaVO(){
		
	}

	public PessoaVO(String nome, String email, String cpf) {
		super();
		this.nome = nome;
		this.email = email;
		this.cpf = cpf;
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

}
