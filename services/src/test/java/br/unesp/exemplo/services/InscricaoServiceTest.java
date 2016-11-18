package br.unesp.exemplo.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.mail.MessagingException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.unesp.exemplo.entities.Evento;
import br.unesp.exemplo.entities.Inscricao;
import br.unesp.exemplo.entities.enums.TamanhoCamiseta;
import br.unesp.exemplo.exceptions.ServiceValidationException;
import br.unesp.exemplo.repositories.InscricaoRepository;
import br.unesp.exemplo.utils.CustomMailSender;

/**
 * Testes da classe InscricaoService 
 * @author Alessandro MOraes
 */
public class InscricaoServiceTest {
	
	@InjectMocks
	private InscricaoService inscricaoService;
	
	@Mock 
	private InscricaoRepository inscricaoRepository;
	
	@Mock
	private CustomMailSender customMailSender;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * Testar inserir uma inscrição
	 * @throws ServiceValidationException 
	 * @throws MessagingException 
	 */
	@Test
	public void testInserir() throws ServiceValidationException, MessagingException {
		Evento evento = new Evento();
		evento.setId(1L);
		evento.setTitulo("Evento teste");
		Inscricao inscricao = new Inscricao();
		inscricao.setCpf("87622237124");
		inscricao.setNome("José da Silva");
		inscricao.setEmail("josesilva@gmail.com");
		inscricao.setTamanhoCamiseta(TamanhoCamiseta.M);
		inscricao.setEvento(evento);
		//não há ninguém inscrito com esse cpf para este evento, repositório retorna null
		when(inscricaoRepository.findByEventoAndCpf(evento, inscricao.getCpf())).thenReturn(null);
		inscricaoService.salvar(inscricao);
		//certifica que chamou o salvar
		verify(inscricaoRepository, times(1)).save(inscricao);
	}
	
	/**
	 * Testar inserir uma inscrição mas o CPF já está inscrito nesse evento (erro esperado)
	 * @throws ServiceValidationException 
	 * @throws MessagingException 
	 */
	@Test(expected=ServiceValidationException.class)
	public void testInserirJáInscrito() throws ServiceValidationException, MessagingException {
		Evento evento = new Evento();
		evento.setId(1L);
		evento.setTitulo("Evento teste");
		Inscricao inscricao = new Inscricao();
		inscricao.setCpf("87622237124");
		inscricao.setNome("José da Silva");
		inscricao.setEmail("josesilva@gmail.com");
		inscricao.setTamanhoCamiseta(TamanhoCamiseta.M);
		inscricao.setEvento(evento);
		//não há ninguém inscrito com esse cpf para este evento, repositório retorna null
		when(inscricaoRepository.findByEventoAndCpf(evento, inscricao.getCpf())).thenReturn(inscricao);
		inscricaoService.salvar(inscricao);
		//certifica que chamou o salvar
		verify(inscricaoRepository, times(0)).save(inscricao);
	}
	
	/**
	 * Testar atualizar uma inscrição
	 * @throws ServiceValidationException 
	 * @throws MessagingException 
	 */
	@Test
	public void testAtualizar() throws ServiceValidationException, MessagingException {
		Evento evento = new Evento();
		evento.setId(1L);
		evento.setTitulo("Evento teste");
		Inscricao inscricao = new Inscricao();
		inscricao.setId(1L);
		inscricao.setCpf("87622237124");
		inscricao.setNome("José da Silva");
		inscricao.setEmail("josesilva@gmail.com");
		inscricao.setTamanhoCamiseta(TamanhoCamiseta.M);
		inscricao.setEvento(evento);
		inscricaoService.salvar(inscricao);
		//certifica que chamou o salvar
		verify(inscricaoRepository, times(1)).save(inscricao);
	}
	
}
