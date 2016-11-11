package br.unesp.exemplo.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.unesp.exemplo.entities.enums.TamanhoCamiseta;

/**
 * Mapeamento Objeto Relacional da entidade Inscricao
 * @author Alessandro Moraes
 */
@Entity
@Table(name="perfil", schema="central")
public class Inscricao implements Serializable {
	private static final long serialVersionUID = 8853148012477575858L;

	@Id
	@SequenceGenerator(name = "seq_inscricao_generator", sequenceName = "exemplo.seq_inscricao", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_inscricao_generator")
	@Column(name = "id", nullable = false)
	private Long id;
	
	@NotNull
	private String nome;
	
	@NotNull
	private String cpf;
	
	@NotNull
	private String email;
	
	@Column(name = "tamanho_camiseta")
	@Enumerated(EnumType.STRING)
	private TamanhoCamiseta tamanhoCamiseta;

	@ManyToOne(optional = false)
    @JoinColumn(name = "id_evento", referencedColumnName = "id", nullable = false)
    private Evento evento;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public TamanhoCamiseta getTamanhoCamiseta() {
		return tamanhoCamiseta;
	}

	public void setTamanhoCamiseta(TamanhoCamiseta tamanhoCamiseta) {
		this.tamanhoCamiseta = tamanhoCamiseta;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
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
		Inscricao other = (Inscricao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Inscricao [id=" + id + "]";
	}


}
