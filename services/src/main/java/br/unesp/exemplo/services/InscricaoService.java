package br.unesp.exemplo.services;

import java.util.List;

import javax.mail.MessagingException;

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
import br.unesp.exemplo.utils.CustomMailSender;

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
	
	@Autowired
	private CustomMailSender mailSender;
	
	
	public Inscricao buscarPorId(long id) {
		return repo.findOne(id);
	}

	public Inscricao salvar(Inscricao inscricao) throws ServiceValidationException, MessagingException {
		//verifica se é uma nova inscrição (quando é atualização o Id vem preenchido)
		if(inscricao.getId() == null){
			//verificar se este usuário já não está inscrito
			Inscricao inscricaoExistente = repo.findByEventoAndCpf(inscricao.getEvento(), inscricao.getCpf());
			if(inscricaoExistente != null){
				throw new ServiceValidationException(String.format("CPF %s já inscrito", inscricao.getCpf()));
			}
		}
		Inscricao inscricaoSalva = repo.save(inscricao);
		mailSender.sendConfirmacaoInscricao(inscricaoSalva);
		return inscricaoSalva;
	}

	public void excluir(long id) throws NotFoundException{
		repo.delete(id);
	}

	public List<Inscricao> listarPorEvento(Evento evento) {
		return repo.findByEvento(evento);
	}

	public Long contarPorEvento(Evento evento) {
		return repo.countByEvento(evento);
	}
}
