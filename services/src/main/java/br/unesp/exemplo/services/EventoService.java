package br.unesp.exemplo.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.unesp.exemplo.entities.Evento;
import br.unesp.exemplo.exceptions.NotFoundException;
import br.unesp.exemplo.exceptions.ServiceValidationException;
import br.unesp.exemplo.repositories.EventoRepository;

/**
 * Service para operações relacionadas a entidade Evento 
 * @author Alessandro Moraes
 */
@Component //indica um componente que pode ser injetado através do @Autowire
@Transactional //indica que usará transações neste serviço
public class EventoService {
	
	private final Logger log = LoggerFactory.getLogger(EventoService.class);
	
	@Autowired
	private EventoRepository repo;
	
	
	public Evento buscarPorId(long id) {
		return repo.findOne(id);
	}

	public Evento salvar(Evento evento) throws ServiceValidationException {
		return repo.save(evento);
	}

	public void excluir(long id) throws NotFoundException{
		repo.delete(id);
	}

	public List<Evento> listar() {
		return repo.findAll();
	}
}
