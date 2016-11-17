package br.unesp.exemplo.api.resources;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.unesp.exemplo.api.utils.EntityConverter;
import br.unesp.exemplo.api.valueobjects.InscricaoVO;
import br.unesp.exemplo.api.valueobjects.InscricaoVOPost;
import br.unesp.exemplo.api.valueobjects.InscricaoVOPut;
import br.unesp.exemplo.entities.Evento;
import br.unesp.exemplo.entities.Inscricao;
import br.unesp.exemplo.entities.enums.TamanhoCamiseta;
import br.unesp.exemplo.exceptions.InvalidAssociationException;
import br.unesp.exemplo.exceptions.InvalidEntityException;
import br.unesp.exemplo.exceptions.NotFoundException;
import br.unesp.exemplo.exceptions.ServiceValidationException;
import br.unesp.exemplo.services.EventoService;
import br.unesp.exemplo.services.InscricaoService;

/**
 * RESTful Resource Inscrições da API
 * @author Alessandro Moraes
 */
@RestController
@RequestMapping("/eventos/{idEvento}/inscricoes")
public class InscricaoController {
	
	@Autowired
	private EventoService eventoService;
	
	@Autowired
	private InscricaoService inscricaoService;
	
	/**
	 * Lista todos as Inscrições
	 * @return
	 * @throws NotFoundException 
	 */
	@RequestMapping(method = RequestMethod.GET)
	public List<InscricaoVO> listar(@PathVariable Long idEvento) throws NotFoundException{
		Evento evento = eventoService.buscarPorId(idEvento);
		if(evento == null){
			throw new NotFoundException(Evento.class,idEvento);
		}
		List<InscricaoVO> list = new ArrayList<>();
		inscricaoService.listarPorEvento(evento).forEach(inscricao -> {
			InscricaoVO vo = EntityConverter.converterParaVO(inscricao);
			list.add(vo);
		});
		return list;
	}
	
	/**
	 * Busca uma inscrição pelo Id
	 * @param idEvento
	 * @param idInscricao
	 * @return
	 * @throws NotFoundException
	 */
	@RequestMapping(value = "/{idInscricao}", method = RequestMethod.GET)
	public InscricaoVO buscarPorId(@PathVariable Long idEvento, @PathVariable Long idInscricao) throws NotFoundException{
		Evento evento = eventoService.buscarPorId(idEvento);
		if(evento == null){
			throw new NotFoundException(Evento.class,idEvento);
		}
		Inscricao inscricao = inscricaoService.buscarPorId(idInscricao);
		if(inscricao == null){
			throw new NotFoundException(Inscricao.class,idInscricao);
		}
		if(!evento.equals(inscricao.getEvento())){
			throw new InvalidAssociationException(inscricao,evento);
		}
		InscricaoVO vo = EntityConverter.converterParaVO(inscricao);
		return vo;
	}
		
	@RequestMapping(method = RequestMethod.POST)
	public InscricaoVO inserir(@PathVariable Long idEvento, @RequestBody @Valid InscricaoVOPost inscricaoVO, BindingResult bindingResult) throws NotFoundException, ServiceValidationException, MessagingException{
		if(bindingResult.hasErrors()){
			throw new InvalidEntityException(InscricaoVOPost.class, bindingResult);
		}
		Evento evento = eventoService.buscarPorId(idEvento);
		if(evento == null){
			throw new NotFoundException(Evento.class,idEvento);
		}
		Inscricao inscricao = new Inscricao();
		inscricao.setCpf(inscricaoVO.getCpf());
		inscricao.setNome(inscricaoVO.getNome());
		inscricao.setEmail(inscricaoVO.getEmail());
		inscricao.setTamanhoCamiseta(TamanhoCamiseta.valueOf(inscricaoVO.getTamanhoCamiseta()));
		inscricao.setEvento(evento);
		
		return EntityConverter.converterParaVO(inscricaoService.salvar(inscricao));
	}
	
	//@Secured({ROLE_ADMINISTRADOR})
	@RequestMapping(value = "/{idInscricao}", method = RequestMethod.PUT)
	public InscricaoVO atualizar(@PathVariable Long idEvento, @PathVariable Long idInscricao, @RequestBody @Valid InscricaoVOPut inscricaoVO, BindingResult bindingResult) throws NotFoundException, ServiceValidationException, MessagingException{
		if(bindingResult.hasErrors()){
			throw new InvalidEntityException(InscricaoVOPut.class, bindingResult);
		}
		Evento evento = eventoService.buscarPorId(idEvento);
		if(evento == null){
			throw new NotFoundException(Evento.class,idEvento);
		}
		Inscricao inscricao = inscricaoService.buscarPorId(idInscricao);
		if(inscricao == null){
			throw new NotFoundException(Inscricao.class,idInscricao);
		}
		if(!evento.equals(inscricao.getEvento())){
			throw new InvalidAssociationException(inscricao,evento);
		}
		inscricao.setCpf(inscricaoVO.getCpf());
		inscricao.setNome(inscricaoVO.getNome());
		inscricao.setEmail(inscricaoVO.getEmail());
		inscricao.setTamanhoCamiseta(TamanhoCamiseta.valueOf(inscricaoVO.getTamanhoCamiseta()));
		
		return EntityConverter.converterParaVO(inscricaoService.salvar(inscricao));
	}
	
	//@Secured({ROLE_ADMINISTRADOR})
	@RequestMapping(value = "/{idInscricao}", method = RequestMethod.DELETE)
	public void excluir(@PathVariable Long idEvento, @PathVariable Long idInscricao) throws NotFoundException{
		Evento evento = eventoService.buscarPorId(idEvento);
		if(evento == null){
			throw new NotFoundException(Evento.class,idEvento);
		}
		Inscricao inscricao = inscricaoService.buscarPorId(idInscricao);
		if(inscricao == null){
			throw new NotFoundException(Inscricao.class,idInscricao);
		}
		if(!evento.equals(inscricao.getEvento())){
			throw new InvalidAssociationException(inscricao,evento);
		}
		inscricaoService.excluir(idInscricao);
	}
	

}
