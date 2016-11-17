package br.unesp.exemplo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.unesp.exemplo.entities.Evento;
import br.unesp.exemplo.entities.Inscricao;

/**
 * Repositório JPA para manipulação junto ao banco de dados da entidade Inscricao
 * @author Alessandro Moraes
 */
public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {
	
	public List<Inscricao> findByEvento(@Param("evento") Evento evento);
	
	@Query("SELECT i FROM Inscricao i WHERE i.evento.id = :idEvento ORDER BY id ASC")
	public List<Inscricao> findByIdEvento(@Param("idEvento") Long idEvento);
	
	public Long countByEvento(Evento evento);
	
}