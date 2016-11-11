package br.unesp.exemplo.api.utils;

import br.unesp.exemplo.api.valueobjects.EventoVO;
import br.unesp.exemplo.api.valueobjects.InscricaoVO;
import br.unesp.exemplo.entities.Evento;
import br.unesp.exemplo.entities.Inscricao;

/**
 * Conversor de Entidades para VOs
 * @author Alessandro Moraes
 */
public class EntityConverter {

	/**
	 * Converte uma entidade Evento para um value object
	 * @param entity
	 * @return
	 */
	public static EventoVO converterParaVO(Evento entity){
		EventoVO vo = new EventoVO();
		vo.setIdEvento(entity.getId());
		vo.setTitulo(entity.getTitulo());
		vo.setLocal(entity.getLocal());
		vo.setEmail(entity.getEmail());
		vo.setInicio(entity.getInicio());
		vo.setTermino(entity.getTermino());
		vo.setInicioInscricao(entity.getInicioInscricao());
		vo.setTerminoInscricao(entity.getTerminoInscricao());
		return vo;
	}
	
	/**
	 * Converte uma entidade Inscricao para um value object
	 * @param entity
	 * @return
	 */
	public static InscricaoVO converterParaVO(Inscricao entity){
		InscricaoVO vo = new InscricaoVO();
		vo.setIdInscricao(entity.getId());
		vo.setCpf(entity.getCpf());
		vo.setNome(entity.getNome());
		vo.setEmail(entity.getEmail());
		vo.setTamanhoCamiseta(entity.getTamanhoCamiseta().name());
		vo.setTamanhoCamisetaDescricao(entity.getTamanhoCamiseta().getDescricao());
		vo.setIdEvento(entity.getEvento().getId());
		return vo;
	}
	
}
