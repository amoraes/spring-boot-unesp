package br.unesp.exemplo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.unesp.exemplo.entities.Evento;

/**
 * Repositório JPA para manipulação junto ao banco de dados da entidade Evento
 * @author Alessandro Moraes
 */
public interface EventoRepository extends JpaRepository<Evento, Long> {
	
		
}