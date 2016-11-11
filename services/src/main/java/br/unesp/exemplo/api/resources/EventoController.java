package br.unesp.exemplo.api.resources;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.unesp.exemplo.api.utils.EntityConverter;
import br.unesp.exemplo.api.valueobjects.EventoVO;
import br.unesp.exemplo.api.valueobjects.EventoVOPost;
import br.unesp.exemplo.api.valueobjects.EventoVOPut;
import br.unesp.exemplo.entities.Evento;
import br.unesp.exemplo.exceptions.InvalidEntityException;
import br.unesp.exemplo.exceptions.NotFoundException;
import br.unesp.exemplo.exceptions.ServiceValidationException;
import br.unesp.exemplo.services.EventoService;

/**
 * RESTful Resource Evento da API
 * @author Alessandro Moraes
 */
@RestController
@RequestMapping("/eventos")
public class EventoController {
	
	@Autowired
	private EventoService eventoService;
	
	/**
	 * Lista todos os Eventos
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public List<EventoVO> listar(){
		List<EventoVO> list = new ArrayList<>();
		eventoService.listar().forEach(sistema -> {
			EventoVO vo = EntityConverter.converterParaVO(sistema);
			list.add(vo);
		});
		return list;
	}
	
	/**
	 * Busca um evento pelo Id
	 * @param idEvento
	 * @return
	 * @throws NotFoundException
	 */
	@RequestMapping(value = "/{idEvento}", method = RequestMethod.GET)
	public EventoVO buscarPorId(@PathVariable Long idEvento) throws NotFoundException{
		Evento evento = eventoService.buscarPorId(idEvento);
		if(evento == null){
			throw new NotFoundException(Evento.class,idEvento);
		}
		EventoVO vo = EntityConverter.converterParaVO(evento);
		return vo;
	}
		
	//@Secured({ROLE_ADMINISTRADOR})
	@RequestMapping(method = RequestMethod.POST)
	public EventoVO inserir(@RequestBody @Valid EventoVOPost eventoVO, BindingResult bindingResult) throws NotFoundException, ServiceValidationException{
		if(bindingResult.hasErrors()){
			throw new InvalidEntityException(EventoVOPost.class, bindingResult);
		}
		Evento evento = new Evento();
		evento.setTitulo(eventoVO.getTitulo());
		evento.setLocal(eventoVO.getLocal());
		evento.setEmail(eventoVO.getEmail());
		evento.setInicio(eventoVO.getInicio());
		evento.setTermino(eventoVO.getTermino());
		evento.setInicioInscricao(eventoVO.getInicioInscricao());
		evento.setTerminoInscricao(eventoVO.getTerminoInscricao());
		
		return EntityConverter.converterParaVO(eventoService.salvar(evento));
	}
	
	//@Secured({ROLE_ADMINISTRADOR})
	@RequestMapping(value = "/{idEvento}", method = RequestMethod.PUT)
	public EventoVO atualizar(@PathVariable Long idEvento, @RequestBody @Valid EventoVOPut eventoVO, BindingResult bindingResult) throws NotFoundException, ServiceValidationException{
		if(bindingResult.hasErrors()){
			throw new InvalidEntityException(EventoVOPut.class, bindingResult);
		}
		Evento evento = eventoService.buscarPorId(idEvento);
		if(evento == null){
			throw new NotFoundException(Evento.class,idEvento);
		}
		evento.setTitulo(eventoVO.getTitulo());
		evento.setLocal(eventoVO.getLocal());
		evento.setEmail(eventoVO.getEmail());
		evento.setInicio(eventoVO.getInicio());
		evento.setTermino(eventoVO.getTermino());
		evento.setInicioInscricao(eventoVO.getInicioInscricao());
		evento.setTerminoInscricao(eventoVO.getTerminoInscricao());
		
		return EntityConverter.converterParaVO(eventoService.salvar(evento));
	}
	
	//@Secured({ROLE_ADMINISTRADOR})
	@RequestMapping(value = "/{idEvento}", method = RequestMethod.DELETE)
	public void excluir(@PathVariable Long idEvento) throws NotFoundException{
		Evento evento = eventoService.buscarPorId(idEvento);
		if(evento == null){
			throw new NotFoundException(Evento.class,idEvento);
		}
		eventoService.excluir(idEvento);
	}
	

}
