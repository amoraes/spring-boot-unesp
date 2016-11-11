package br.unesp.exemplo.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Mapeamento Objeto Relacional da entidade Evento 
 * @author Alessandro Moraes
 */
@Entity
@Table(name = "evento", schema = "exemplo")
public class Evento implements Serializable {

    private static final long serialVersionUID = 3455562940983241751L;

    @Id
    @SequenceGenerator(name = "seq_evento_generator", sequenceName = "exemplo.seq_evento", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_evento_generator")
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    private String titulo;
    
    @NotNull
    private String local;
    
    @NotNull
    private String email;
    
    @NotNull
    private Date inicio;
    
    @NotNull
    private Date termino;
    
    @Column(name = "inicio_inscricao")
    @NotNull
    private Date inicioInscricao;
    
    @Column(name = "termino_inscricao")
    @NotNull
    private Date terminoInscricao;
   
    public Evento() {
    	super();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Evento other = (Evento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Evento [id=" + id + "]";
	}
}