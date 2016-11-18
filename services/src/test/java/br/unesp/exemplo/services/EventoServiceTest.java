package br.unesp.exemplo.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.unesp.exemplo.entities.Evento;
import br.unesp.exemplo.exceptions.ServiceValidationException;
import br.unesp.exemplo.repositories.EventoRepository;

/**
 * Testes da classe EventoService 
 * @author Alessandro MOraes
 */
public class EventoServiceTest {
	
	@InjectMocks
	private EventoService eventoService;
	
	@Mock 
	private EventoRepository eventoRepository;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * Testar salvar um evento com datas corretas
	 * @throws ServiceValidationException 
	 */
	@Test
	public void testSalvar() throws ServiceValidationException {
		Evento evento = new Evento();
		evento.setInicio(new GregorianCalendar(2016, Calendar.JANUARY, 9).getTime());
		evento.setTermino(new GregorianCalendar(2016, Calendar.JANUARY, 10).getTime());
		evento.setInicioInscricao(new GregorianCalendar(2016, Calendar.JANUARY, 5).getTime());
		evento.setTerminoInscricao(new GregorianCalendar(2016, Calendar.JANUARY, 6).getTime());
		eventoService.salvar(evento);
		//certifica que chamou o salvar
		verify(eventoRepository, times(1)).save(evento);

	}
	
	/**
	 * Testar salvar um evento com data de início depois do término (erro esperado)
	 * @throws ServiceValidationException 
	 */
	@Test(expected=ServiceValidationException.class)
	public void testSalvarDatasInvalidas1() throws ServiceValidationException {
		Evento evento = new Evento();
		evento.setInicio(new GregorianCalendar(2016, Calendar.JANUARY, 10).getTime());
		evento.setTermino(new GregorianCalendar(2016, Calendar.JANUARY, 9).getTime());
		evento.setInicioInscricao(new GregorianCalendar(2016, Calendar.JANUARY, 5).getTime());
		evento.setTerminoInscricao(new GregorianCalendar(2016, Calendar.JANUARY, 6).getTime());
		eventoService.salvar(evento);
		//certifica que não chamou o salvar
		verify(eventoRepository, times(0)).save(evento);

	}
	
	/**
	 * Testar salvar um evento com data de início das inscrições depois do início do evento(erro esperado)
	 * @throws ServiceValidationException 
	 */
	@Test(expected=ServiceValidationException.class)
	public void testSalvarDatasInvalidas2() throws ServiceValidationException {
		Evento evento = new Evento();
		evento.setInicio(new GregorianCalendar(2016, Calendar.JANUARY, 9).getTime());
		evento.setTermino(new GregorianCalendar(2016, Calendar.JANUARY, 10).getTime());
		evento.setInicioInscricao(new GregorianCalendar(2016, Calendar.JANUARY, 11).getTime());
		evento.setTerminoInscricao(new GregorianCalendar(2016, Calendar.JANUARY, 12).getTime());
		eventoService.salvar(evento);
		//certifica que não chamou o salvar
		verify(eventoRepository, times(0)).save(evento);

	}
		
	/**
	 * Testar salvar um evento com data de início das inscrições depois do término das inscrições (erro esperado)
	 * @throws ServiceValidationException 
	 */
	@Test(expected=ServiceValidationException.class)
	public void testSalvarDatasInvalidas3() throws ServiceValidationException {
		Evento evento = new Evento();
		evento.setInicio(new GregorianCalendar(2016, Calendar.JANUARY, 9).getTime());
		evento.setTermino(new GregorianCalendar(2016, Calendar.JANUARY, 10).getTime());
		evento.setInicioInscricao(new GregorianCalendar(2016, Calendar.JANUARY, 12).getTime());
		evento.setTerminoInscricao(new GregorianCalendar(2016, Calendar.JANUARY, 11).getTime());
		eventoService.salvar(evento);
		//certifica que não chamou o salvar
		verify(eventoRepository, times(0)).save(evento);

	}
	
	/**
	 * Testar salvar um evento com data de término das inscrições depois do término do evento (erro esperado)
	 * @throws ServiceValidationException 
	 */
	@Test(expected=ServiceValidationException.class)
	public void testSalvarDatasInvalidas4() throws ServiceValidationException {
		Evento evento = new Evento();
		evento.setInicio(new GregorianCalendar(2016, Calendar.JANUARY, 9).getTime());
		evento.setTermino(new GregorianCalendar(2016, Calendar.JANUARY, 10).getTime());
		evento.setInicioInscricao(new GregorianCalendar(2016, Calendar.JANUARY, 5).getTime());
		evento.setTerminoInscricao(new GregorianCalendar(2016, Calendar.JANUARY, 12).getTime());
		eventoService.salvar(evento);
		//certifica que não chamou o salvar
		verify(eventoRepository, times(0)).save(evento);

	}
	
}
