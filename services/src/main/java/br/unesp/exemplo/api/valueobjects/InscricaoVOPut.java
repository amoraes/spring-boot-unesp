package br.unesp.exemplo.api.valueobjects;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Value Object para comunicação REST da entidade Inscricao PUT (atualização)
 * @author Alessandro Moraes
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("Inscricao")
public class InscricaoVOPut implements Serializable {
	private static final long serialVersionUID = 8853148012477575858L;
	
	@NotNull
	private String nome;
	
	@NotNull
	private String cpf;
	
	@NotNull
	private String email;
	
	private String tamanhoCamiseta;
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTamanhoCamiseta() {
		return tamanhoCamiseta;
	}

	public void setTamanhoCamiseta(String tamanhoCamiseta) {
		this.tamanhoCamiseta = tamanhoCamiseta;
	}

}
