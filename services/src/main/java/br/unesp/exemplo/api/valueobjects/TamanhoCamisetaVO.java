package br.unesp.exemplo.api.valueobjects;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Value Object para comunicação REST do enum TamanhoCamiseta 
 * @author Alessandro Moraes
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("TamanhoCamiseta")
public class TamanhoCamisetaVO implements Serializable {

	private static final long serialVersionUID = 3868082778438200587L;

	public TamanhoCamisetaVO(){
		
	}
	
	public TamanhoCamisetaVO(String tamanho, String descricao) {
		super();
		this.tamanho = tamanho;
		this.descricao = descricao;
	}

	@NotNull
    private String tamanho;

    @NotNull
    private String descricao;

	public String getTamanho() {
		return tamanho;
	}

	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
    

}