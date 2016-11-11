package br.unesp.exemplo.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.unesp.exemplo.entities.Evento;
import br.unesp.exemplo.entities.Inscricao;
import br.unesp.exemplo.exceptions.NotFoundException;
import br.unesp.exemplo.exceptions.ServiceValidationException;
import br.unesp.exemplo.repositories.InscricaoRepository;

/**
 * Service para operações relacionadas a entidade Inscricao 
 * @author Alessandro Moraes
 */
@Component //indica um componente que pode ser injetado através do @Autowire
@Transactional //indica que usará transações neste serviço
public class InscricaoService {
	
	private final Logger log = LoggerFactory.getLogger(InscricaoService.class);
	
	@Autowired
	private InscricaoRepository repo;
	
	
	public Inscricao buscarPorId(long id) {
		return repo.findOne(id);
	}

	public Inscricao salvar(Inscricao evento) throws ServiceValidationException {
		return repo.save(evento);
	}

	public void excluir(long id) throws NotFoundException{
		repo.delete(id);
	}

	public List<Inscricao> listarPorEvento(Evento evento) {
		return repo.findByEvento(evento);
	}
}
