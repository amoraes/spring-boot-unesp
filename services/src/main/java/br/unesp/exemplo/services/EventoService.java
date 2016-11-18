package br.unesp.exemplo.services;

import java.util.List;

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
	
	@Autowired
	private EventoRepository repo;
	
	
	public Evento buscarPorId(long id) {
		return repo.findOne(id);
	}

	public Evento salvar(Evento evento) throws ServiceValidationException {
		//verificar as datas
		if(evento.getInicio().after(evento.getTermino())){
			throw new ServiceValidationException("Data de início do evento não pode ser depois da data de término do evento");
		}
		if(evento.getInicioInscricao().after(evento.getInicio())){
			throw new ServiceValidationException("Data de início das inscrições não pode ser depois da data de início do evento");
		}
		if(evento.getInicioInscricao().after(evento.getTerminoInscricao())){
			throw new ServiceValidationException("Data de início das inscrições não pode ser depois da data de término das inscrições");
		}
		if(evento.getTerminoInscricao().after(evento.getTermino())){
			throw new ServiceValidationException("Data de término das inscrições não pode ser depois da data de término do evento");
		}
		return repo.save(evento);
	}

	public void excluir(long id) throws NotFoundException{
		repo.delete(id);
	}

	public List<Evento> listar() {
		return repo.findAll();
	}
}
