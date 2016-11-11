package br.unesp.exemplo.api.valueobjects;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;

import br.unesp.exemplo.KGlobal;

/**
 * Value Object para comunicação REST da entidade Evento via PUT (atualização)
 * @author Alessandro Moraes
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("Evento")
public class EventoVOPut implements Serializable {

    private static final long serialVersionUID = 3455562940983241751L;
    
    @NotNull
    private String titulo;
    
    @NotNull
    private String local;
    
    @NotNull
    private String email;
    
    @NotNull
    @JsonFormat(pattern = KGlobal.DATE_FORMAT, timezone = KGlobal.TIMEZONE)
    private Date inicio;
    
    @NotNull
    @JsonFormat(pattern = KGlobal.DATE_FORMAT, timezone = KGlobal.TIMEZONE)
    private Date termino;
    
    @NotNull
    @JsonFormat(pattern = KGlobal.DATE_FORMAT, timezone = KGlobal.TIMEZONE)
    private Date inicioInscricao;
    
    @NotNull
    @JsonFormat(pattern = KGlobal.DATE_FORMAT, timezone = KGlobal.TIMEZONE)
    private Date terminoInscricao;
   
    public EventoVOPut() {
    	super();
    }
    
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getTermino() {
		return termino;
	}

	public void setTermino(Date termino) {
		this.termino = termino;
	}

	public Date getInicioInscricao() {
		return inicioInscricao;
	}

	public void setInicioInscricao(Date inicioInscricao) {
		this.inicioInscricao = inicioInscricao;
	}

	public Date getTerminoInscricao() {
		return terminoInscricao;
	}

	public void setTerminoInscricao(Date terminoInscricao) {
		this.terminoInscricao = terminoInscricao;
	}
	
}