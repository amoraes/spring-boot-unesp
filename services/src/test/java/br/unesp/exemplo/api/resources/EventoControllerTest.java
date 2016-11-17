package br.unesp.exemplo.api.resources;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import br.unesp.exemplo.KGlobal;
import br.unesp.exemplo.api.valueobjects.EventoVOPost;
import br.unesp.exemplo.api.valueobjects.EventoVOPut;
import br.unesp.exemplo.entities.Evento;
import br.unesp.exemplo.entities.EventoDummy;
import br.unesp.exemplo.exceptions.InvalidEntityException;
import br.unesp.exemplo.services.EventoService;
import br.unesp.exemplo.utils.RestErrorsControllerAdvice;
import br.unesp.exemplo.utils.TestHelper;

public class EventoControllerTest {
	
	private MockMvc mockMvc;
	
	private final String BASE_URL = "/eventos";
	
	@Mock
	private EventoService eventoService;
	
	@InjectMocks
	private EventoController eventoController;
	
	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
		RestErrorsControllerAdvice advice = new RestErrorsControllerAdvice();
		this.mockMvc = standaloneSetup(eventoController)
				.setControllerAdvice(advice)
			.build();
	}
	
	/**
	 * listar: verifica se retorna corretamente
	 */
	@Test
	public void testListar() throws Exception{
		Evento evento = new EventoDummy();
		List<Evento> list = Arrays.asList(new Evento[]{evento});
		
		when(eventoService.listar()).thenReturn(list);
		
		this.mockMvc
			.perform(get(BASE_URL+"/")
				.accept(KGlobal.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].idEvento", equalTo(new Long(evento.getId()).intValue())))
				.andExpect(jsonPath("$[0].titulo", equalTo(evento.getTitulo())))
				.andExpect(jsonPath("$[0].local", equalTo(evento.getLocal())))
				.andExpect(jsonPath("$[0].email", equalTo(evento.getEmail())))
				.andExpect(jsonPath("$[0].inicio", equalTo(KGlobal.DATE_FORMATTER.format(evento.getInicio()))))
				.andExpect(jsonPath("$[0].termino", equalTo(KGlobal.DATE_FORMATTER.format(evento.getTermino()))))
				.andExpect(jsonPath("$[0].inicioInscricao", equalTo(KGlobal.DATE_FORMATTER.format(evento.getInicioInscricao()))))
				.andExpect(jsonPath("$[0].terminoInscricao", equalTo(KGlobal.DATE_FORMATTER.format(evento.getTerminoInscricao()))));
	}
	
	/**
	 * buscarPorId: verifica se retorna corretamente
	 */
	@Test
	public void testBuscarPorId() throws Exception{
		Evento evento = new EventoDummy();
		when(eventoService.buscarPorId(evento.getId())).thenReturn(evento);
		this.mockMvc
			.perform(get(BASE_URL+"/{idEvento}",evento.getId())
				.accept(KGlobal.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.idEvento", equalTo(new Long(evento.getId()).intValue())))
				.andExpect(jsonPath("$.titulo", equalTo(evento.getTitulo())))
				.andExpect(jsonPath("$.local", equalTo(evento.getLocal())))
				.andExpect(jsonPath("$.email", equalTo(evento.getEmail())))
				.andExpect(jsonPath("$.inicio", equalTo(KGlobal.DATE_FORMATTER.format(evento.getInicio()))))
				.andExpect(jsonPath("$.termino", equalTo(KGlobal.DATE_FORMATTER.format(evento.getTermino()))))
				.andExpect(jsonPath("$.inicioInscricao", equalTo(KGlobal.DATE_FORMATTER.format(evento.getInicioInscricao()))))
				.andExpect(jsonPath("$.terminoInscricao", equalTo(KGlobal.DATE_FORMATTER.format(evento.getTerminoInscricao()))));
				
	}
	/**
	 * buscarPorId: verifica se retorna nulo para id inexistente
	 */	
	@Test
	public void testBuscarPorIdInexistente() throws Exception{
		final long idEvento = 1L;
		when(eventoService.buscarPorId(idEvento)).thenReturn(null);
		this.mockMvc.perform(get(BASE_URL+"/{idEvento}",idEvento)
			.accept(KGlobal.APPLICATION_JSON_UTF8))
			.andExpect(status().isNotFound());
	}
	
	/**
	 * inserir: verifica se insere corretamente
	 */	
	@Test
	public void testInserir() throws Exception{
		//entity para retorno do service
		Evento evento = new EventoDummy();
		//vo para post
		EventoVOPost eventoVO = new EventoVOPost();
		eventoVO.setTitulo(evento.getTitulo());
		eventoVO.setLocal(evento.getLocal());
		eventoVO.setEmail(evento.getEmail());
		eventoVO.setInicio(evento.getInicio());
		eventoVO.setTermino(evento.getTermino());
		eventoVO.setInicioInscricao(evento.getInicioInscricao());
		eventoVO.setTerminoInscricao(evento.getTerminoInscricao());
				
		when(eventoService.salvar(any())).thenReturn(evento);
		this.mockMvc
			.perform(post(BASE_URL)
					.contentType(KGlobal.APPLICATION_JSON_UTF8)
					.content(TestHelper.convertObjectToJsonString(eventoVO))	
					)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.idEvento", equalTo(evento.getId().intValue())))
				.andExpect(jsonPath("$.titulo", equalTo(evento.getTitulo())))
				.andExpect(jsonPath("$.local", equalTo(evento.getLocal())))
				.andExpect(jsonPath("$.email", equalTo(evento.getEmail())))
				.andExpect(jsonPath("$.inicio", equalTo(KGlobal.DATE_FORMATTER.format(evento.getInicio()))))
				.andExpect(jsonPath("$.termino", equalTo(KGlobal.DATE_FORMATTER.format(evento.getTermino()))))
				.andExpect(jsonPath("$.inicioInscricao", equalTo(KGlobal.DATE_FORMATTER.format(evento.getInicioInscricao()))))
				.andExpect(jsonPath("$.terminoInscricao", equalTo(KGlobal.DATE_FORMATTER.format(evento.getTerminoInscricao()))));
		verify(eventoService, atLeastOnce()).salvar(any());
	}
	
	/**
	 * inserir: verifica se lança erro de validação de campos (entidade inválida)
	 */	
	@Test
	public void testInserirCamposInvalidos() throws Exception{
		
		//vo para post sem campos requeridos
		EventoVOPost eventoVO = new EventoVOPost();
		eventoVO.setTitulo("Apenas o título");			
		
		this.mockMvc
			.perform(post(BASE_URL)
					.contentType(KGlobal.APPLICATION_JSON_UTF8)
					.content(TestHelper.convertObjectToJsonString(eventoVO))	
					)
				.andExpect(status().isUnprocessableEntity())
				.andExpect(jsonPath("$.code", equalTo(InvalidEntityException.class.getSimpleName())));
		verify(eventoService, never()).salvar(any());
	}
	
	/**
	 * atualizar: verifica se atualiza corretamente
	 */	
	@Test
	public void testAtualizar() throws Exception{
		//entity para retorno do service
		Evento evento = new EventoDummy();
		//vo para put
		String novoEmail = "novoemaildoevento@unesp.br";
		EventoVOPut eventoVO = new EventoVOPut();
		eventoVO.setTitulo(evento.getTitulo());
		eventoVO.setLocal(evento.getLocal());
		eventoVO.setEmail(novoEmail);
		eventoVO.setInicio(evento.getInicio());
		eventoVO.setTermino(evento.getTermino());
		eventoVO.setInicioInscricao(evento.getInicioInscricao());
		eventoVO.setTerminoInscricao(evento.getTerminoInscricao());
		
		when(eventoService.buscarPorId(evento.getId())).thenReturn(evento);
		when(eventoService.salvar(any())).thenReturn(evento);
		this.mockMvc
			.perform(put(BASE_URL+"/{idEvento}",evento.getId())
					.contentType(KGlobal.APPLICATION_JSON_UTF8)
					.content(TestHelper.convertObjectToJsonString(eventoVO))	
					)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.idEvento", equalTo(new Long(evento.getId()).intValue())))
				.andExpect(jsonPath("$.titulo", equalTo(evento.getTitulo())))
				.andExpect(jsonPath("$.local", equalTo(evento.getLocal())))
				.andExpect(jsonPath("$.email", equalTo(novoEmail)))
				.andExpect(jsonPath("$.inicio", equalTo(KGlobal.DATE_FORMATTER.format(evento.getInicio()))))
				.andExpect(jsonPath("$.termino", equalTo(KGlobal.DATE_FORMATTER.format(evento.getTermino()))))
				.andExpect(jsonPath("$.inicioInscricao", equalTo(KGlobal.DATE_FORMATTER.format(evento.getInicioInscricao()))))
				.andExpect(jsonPath("$.terminoInscricao", equalTo(KGlobal.DATE_FORMATTER.format(evento.getTerminoInscricao()))));
		verify(eventoService, atLeastOnce()).salvar(any());
	}
	
	/**
	 * atualizar: verifica se lança erro ao atualizar evento inexistente
	 */	
	@Test
	public void testAtualizarEventoInexistente() throws Exception{
		//entity para retorno do service
		Evento evento = new EventoDummy();
		//vo para put
		EventoVOPut eventoVO = new EventoVOPut();
		eventoVO.setTitulo(evento.getTitulo());
		eventoVO.setLocal(evento.getLocal());
		eventoVO.setEmail(evento.getEmail());
		eventoVO.setInicio(evento.getInicio());
		eventoVO.setTermino(evento.getTermino());
		eventoVO.setInicioInscricao(evento.getInicioInscricao());
		eventoVO.setTerminoInscricao(evento.getTerminoInscricao());
		when(eventoService.buscarPorId(evento.getId())).thenReturn(null); //null <-- o service não encontrou o evento no banco de dados
		this.mockMvc
			.perform(put(BASE_URL+"/{idEvento}",evento.getId())
					.contentType(KGlobal.APPLICATION_JSON_UTF8)
					.content(TestHelper.convertObjectToJsonString(eventoVO))	
					)
				.andExpect(status().isNotFound());
		verify(eventoService, never()).salvar(any());
	}
	
	/**
	 * atualizar: verifica se lança erro de validação de campos (entidade inválida)
	 */	
	@Test
	public void testAtualizarCamposInvalidos() throws Exception{
		Long idEvento = 1L;
		//vo para put sem campos requeridos
		EventoVOPut eventoVO = new EventoVOPut();
		eventoVO.setTitulo("Apenas o título");			
		
		this.mockMvc
			.perform(put(BASE_URL+"/{idEvento}",idEvento)
					.contentType(KGlobal.APPLICATION_JSON_UTF8)
					.content(TestHelper.convertObjectToJsonString(eventoVO))	
					)
				.andExpect(status().isUnprocessableEntity())
				.andExpect(jsonPath("$.code", equalTo(InvalidEntityException.class.getSimpleName())));
		verify(eventoService, never()).salvar(any());
		
	}
	
	
	/**
	 * excluir: verifica se remove corretamente
	 */	
	@Test
	public void testExcluir() throws Exception{
		//entity para retorno do service
		Evento evento = new EventoDummy();
				
		when(eventoService.buscarPorId(evento.getId())).thenReturn(evento);
		this.mockMvc
			.perform(delete(BASE_URL+"/{idEvento}",evento.getId()))
				.andExpect(status().isOk());
		verify(eventoService, atLeastOnce()).excluir(evento.getId());
	}
	
	/**
	 * excluir: verifica se lança erro ao excluir evento inexistente
	 */	
	@Test
	public void testExcluirEventoInexistente() throws Exception{
		//entity para retorno do service
		Evento evento = new EventoDummy();
		when(eventoService.buscarPorId(evento.getId())).thenReturn(null); //null <-- o service não encontrou o evento no banco de dados
		this.mockMvc
			.perform(delete(BASE_URL+"/{idEvento}",evento.getId()))
				.andExpect(status().isNotFound());
		verify(eventoService, never()).excluir(evento.getId());
	}
	
}