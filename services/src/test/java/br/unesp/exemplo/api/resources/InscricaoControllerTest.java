package br.unesp.exemplo.api.resources;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
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

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;

import br.unesp.exemplo.KGlobal;
import br.unesp.exemplo.api.valueobjects.EventoVOPost;
import br.unesp.exemplo.api.valueobjects.EventoVOPut;
import br.unesp.exemplo.api.valueobjects.InscricaoVOPost;
import br.unesp.exemplo.entities.Evento;
import br.unesp.exemplo.entities.EventoDummy;
import br.unesp.exemplo.entities.Inscricao;
import br.unesp.exemplo.entities.InscricaoDummy;
import br.unesp.exemplo.entities.enums.TamanhoCamiseta;
import br.unesp.exemplo.exceptions.InvalidEntityException;
import br.unesp.exemplo.services.EventoService;
import br.unesp.exemplo.services.InscricaoService;
import br.unesp.exemplo.utils.RestErrorsControllerAdvice;
import br.unesp.exemplo.utils.TestHelper;

public class InscricaoControllerTest {
	
	private MockMvc mockMvc;
	
	private final String BASE_URL = "/eventos/{idEvento}/inscricoes";
	
	@Mock
	private EventoService eventoService;
	
	@Mock
	private InscricaoService inscricaoService;
	
	@InjectMocks
	private InscricaoController inscricaoController;
	
	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
		RestErrorsControllerAdvice advice = new RestErrorsControllerAdvice();
		this.mockMvc = standaloneSetup(inscricaoController)
				.setControllerAdvice(advice)
			.build();
	}
	
	/**
	 * listar: verifica se retorna corretamente
	 */
	@Test
	public void testListar() throws Exception{
		Evento evento = new EventoDummy();
		Inscricao inscricao1 = new InscricaoDummy(evento);
		Inscricao inscricao2 = new Inscricao();
		inscricao2.setId(2L);
		inscricao2.setCpf("81439929343");
		inscricao2.setNome("Juliana Ferrarezi");
		inscricao2.setEmail("ju@unesp.br");
		inscricao2.setTamanhoCamiseta(TamanhoCamiseta.P);
		inscricao2.setEvento(evento);
		
		List<Inscricao> list = Arrays.asList(new Inscricao[]{ inscricao1, inscricao2 });
		
		when(eventoService.buscarPorId(evento.getId())).thenReturn(evento);
		when(inscricaoService.listarPorEvento(evento)).thenReturn(list);
		
		this.mockMvc
			.perform(get(BASE_URL+"/",evento.getId())
				.accept(KGlobal.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].idInscricao", equalTo(new Long(inscricao1.getId()).intValue())))
				.andExpect(jsonPath("$[0].idEvento", equalTo(new Long(inscricao1.getEvento().getId()).intValue())))
				.andExpect(jsonPath("$[0].cpf", equalTo(inscricao1.getCpf())))
				.andExpect(jsonPath("$[0].nome", equalTo(inscricao1.getNome())))
				.andExpect(jsonPath("$[0].email", equalTo(inscricao1.getEmail())))
				.andExpect(jsonPath("$[0].tamanhoCamiseta", equalTo(inscricao1.getTamanhoCamiseta().name())))
				.andExpect(jsonPath("$[0].tamanhoCamisetaDescricao", equalTo(inscricao1.getTamanhoCamiseta().getDescricao())))
				.andExpect(jsonPath("$[1].idInscricao", equalTo(new Long(inscricao2.getId()).intValue())))
				.andExpect(jsonPath("$[1].idEvento", equalTo(new Long(inscricao2.getEvento().getId()).intValue())))
				.andExpect(jsonPath("$[1].cpf", equalTo(inscricao2.getCpf())))
				.andExpect(jsonPath("$[1].nome", equalTo(inscricao2.getNome())))
				.andExpect(jsonPath("$[1].email", equalTo(inscricao2.getEmail())))
				.andExpect(jsonPath("$[1].tamanhoCamiseta", equalTo(inscricao2.getTamanhoCamiseta().name())))
				.andExpect(jsonPath("$[1].tamanhoCamisetaDescricao", equalTo(inscricao2.getTamanhoCamiseta().getDescricao())));
	}
	
	/**
	 * listar: verifica se retorna erro para evento inexistente
	 */
	@Test
	public void testListarEventoInexistente() throws Exception{
		Evento evento = new EventoDummy();

		when(eventoService.buscarPorId(evento.getId())).thenReturn(null); //null <-- o service não encontrou o evento no banco de dados
		
		this.mockMvc
			.perform(get(BASE_URL+"/",evento.getId())
				.accept(KGlobal.APPLICATION_JSON_UTF8))
				.andExpect(status().isNotFound());
	}
	
	/**
	 * listar: verifica se contar corretamente
	 */
	@Test
	public void testContar() throws Exception{
		Long quantidade = 10L;
		Evento evento = new EventoDummy();	
		when(eventoService.buscarPorId(evento.getId())).thenReturn(evento);
		when(inscricaoService.contarPorEvento(evento)).thenReturn(quantidade);
		
		this.mockMvc
			.perform(get(BASE_URL+"/count",evento.getId())
				.accept(KGlobal.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.count", equalTo(new Long(quantidade).intValue())));
	}
	
	/**
	 * listar: verifica se retorna erro para evento inexistente
	 */
	@Test
	public void testContarEventoInexistente() throws Exception{
		Evento evento = new EventoDummy();
		when(eventoService.buscarPorId(evento.getId())).thenReturn(null); //null <-- o service não encontrou o evento no banco de dados
		
		this.mockMvc
			.perform(get(BASE_URL+"/count",evento.getId())
				.accept(KGlobal.APPLICATION_JSON_UTF8))
				.andExpect(status().isNotFound());
	}
	
	
	/**
	 * buscarPorId: verifica se retorna corretamente
	 */
	@Test
	public void testBuscarPorId() throws Exception{
		Evento evento = new EventoDummy();
		Inscricao inscricao = new InscricaoDummy(evento);
		
		when(eventoService.buscarPorId(evento.getId())).thenReturn(evento);
		when(inscricaoService.buscarPorId(inscricao.getId())).thenReturn(inscricao);
		
		this.mockMvc
			.perform(get(BASE_URL+"/{idInscricao}",evento.getId(),inscricao.getId())
				.accept(KGlobal.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.idInscricao", equalTo(new Long(inscricao.getId()).intValue())))
				.andExpect(jsonPath("$.idEvento", equalTo(new Long(inscricao.getEvento().getId()).intValue())))
				.andExpect(jsonPath("$.cpf", equalTo(inscricao.getCpf())))
				.andExpect(jsonPath("$.nome", equalTo(inscricao.getNome())))
				.andExpect(jsonPath("$.email", equalTo(inscricao.getEmail())))
				.andExpect(jsonPath("$.tamanhoCamiseta", equalTo(inscricao.getTamanhoCamiseta().name())))
				.andExpect(jsonPath("$.tamanhoCamisetaDescricao", equalTo(inscricao.getTamanhoCamiseta().getDescricao())));
				
	}
	
	/**
	 * buscarPorId: verifica se retorna erro para inscrição inexistente
	 */	
	@Test
	public void testBuscarPorIdEventoInexistente() throws Exception{
		final long idEvento = 1L;
		final long idInscricao = 1L;
		when(eventoService.buscarPorId(idEvento)).thenReturn(null);
		this.mockMvc.perform(get(BASE_URL+"/{idInscricao}",idEvento,idInscricao)
			.accept(KGlobal.APPLICATION_JSON_UTF8))
			.andExpect(status().isNotFound());
	}
	
	/**
	 * buscarPorId: verifica se retorna erro para inscrição inexistente
	 */	
	@Test
	public void testBuscarPorIdInexistente() throws Exception{
		Evento evento = new EventoDummy();
		final long idInscricao = 1L;
		when(eventoService.buscarPorId(evento.getId())).thenReturn(evento);
		when(inscricaoService.buscarPorId(idInscricao)).thenReturn(null);
		this.mockMvc.perform(get(BASE_URL+"/{idInscricao}",evento.getId(),idInscricao)
			.accept(KGlobal.APPLICATION_JSON_UTF8))
			.andExpect(status().isNotFound());
	}
	
	/**
	 * buscarPorId: verifica se retorna erro para inscrição vinculada a outro evento
	 */	
	@Test
	public void testBuscarPorIdVinculoErrado() throws Exception{
		Evento evento = new EventoDummy();
		Inscricao inscricao = new InscricaoDummy(evento);
		Evento outroEvento = new EventoDummy();
		outroEvento.setId(2L);
		
		when(eventoService.buscarPorId(outroEvento.getId())).thenReturn(outroEvento);
		when(inscricaoService.buscarPorId(inscricao.getId())).thenReturn(inscricao);
		this.mockMvc.perform(get(BASE_URL+"/{idInscricao}",outroEvento.getId(),inscricao.getId())
			.accept(KGlobal.APPLICATION_JSON_UTF8))
			.andExpect(status().isBadRequest());
	}
	
	/**
	 * inserir: verifica se insere corretamente
	 */	
	@Test
	public void testInserir() throws Exception{
		Evento evento = new EventoDummy();
		Inscricao inscricao = new InscricaoDummy(evento);
		//vo para post
		InscricaoVOPost inscricaoVO = new InscricaoVOPost();
		inscricaoVO.setCpf(inscricao.getCpf());
		inscricaoVO.setNome(inscricao.getNome());
		inscricaoVO.setEmail(inscricao.getEmail());
		inscricaoVO.setTamanhoCamiseta(inscricao.getTamanhoCamiseta().name());
				
		when(eventoService.buscarPorId(evento.getId())).thenReturn(evento);
		when(inscricaoService.salvar(any())).thenReturn(inscricao);
		this.mockMvc
			.perform(post(BASE_URL+"/", evento.getId())
					.contentType(KGlobal.APPLICATION_JSON_UTF8)
					.content(TestHelper.convertObjectToJsonString(inscricaoVO))	
					)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.idInscricao", equalTo(inscricao.getId().intValue())))
				.andExpect(jsonPath("$.idEvento", equalTo(evento.getId().intValue())))
				.andExpect(jsonPath("$.cpf", equalTo(inscricao.getCpf())))
				.andExpect(jsonPath("$.nome", equalTo(inscricao.getNome())))
				.andExpect(jsonPath("$.email", equalTo(inscricao.getEmail())))
				.andExpect(jsonPath("$.tamanhoCamiseta", equalTo(inscricao.getTamanhoCamiseta().name())))
				.andExpect(jsonPath("$.tamanhoCamisetaDescricao", equalTo(inscricao.getTamanhoCamiseta().getDescricao())))
				;
		verify(inscricaoService, atLeastOnce()).salvar(any());
	}
	
	/**
	 * inserir: verifica se lança erro de validação de campos (entidade inválida)
	 */	
	@Test
	public void testInserirCamposInvalidos() throws Exception{
		Evento evento = new EventoDummy();
		Inscricao inscricao = new InscricaoDummy(evento);
		//vo para post
		InscricaoVOPost inscricaoVO = new InscricaoVOPost();
		inscricaoVO.setCpf(null);
		inscricaoVO.setNome(inscricao.getNome());
		inscricaoVO.setEmail(inscricao.getEmail());
		inscricaoVO.setTamanhoCamiseta(inscricao.getTamanhoCamiseta().name());		
		
		this.mockMvc
			.perform(post(BASE_URL+"/", evento.getId())
					.contentType(KGlobal.APPLICATION_JSON_UTF8)
					.content(TestHelper.convertObjectToJsonString(inscricaoVO))	
					)
				.andExpect(status().isUnprocessableEntity())
				.andExpect(jsonPath("$.code", equalTo(InvalidEntityException.class.getSimpleName())));
		verify(inscricaoService, never()).salvar(any());
	}
	
	/**
	 * atualizar: verifica se atualiza corretamente
	 */	
	@Test
	public void testAtualizar() throws Exception{
		Evento evento = new EventoDummy();
		Inscricao inscricao = new InscricaoDummy(evento);
		//vo para post
		InscricaoVOPost inscricaoVO = new InscricaoVOPost();
		inscricaoVO.setCpf(inscricao.getCpf());
		inscricaoVO.setNome(inscricao.getNome());
		inscricaoVO.setEmail(inscricao.getEmail());
		inscricaoVO.setTamanhoCamiseta(inscricao.getTamanhoCamiseta().name());
				
		when(eventoService.buscarPorId(evento.getId())).thenReturn(evento);
		when(inscricaoService.buscarPorId(inscricao.getId())).thenReturn(inscricao);
		when(inscricaoService.salvar(any())).thenReturn(inscricao);
		this.mockMvc
			.perform(put(BASE_URL+"/{idInscricao}", evento.getId(), inscricao.getId())
					.contentType(KGlobal.APPLICATION_JSON_UTF8)
					.content(TestHelper.convertObjectToJsonString(inscricaoVO))	
					)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.idInscricao", equalTo(inscricao.getId().intValue())))
				.andExpect(jsonPath("$.idEvento", equalTo(evento.getId().intValue())))
				.andExpect(jsonPath("$.cpf", equalTo(inscricao.getCpf())))
				.andExpect(jsonPath("$.nome", equalTo(inscricao.getNome())))
				.andExpect(jsonPath("$.email", equalTo(inscricao.getEmail())))
				.andExpect(jsonPath("$.tamanhoCamiseta", equalTo(inscricao.getTamanhoCamiseta().name())))
				.andExpect(jsonPath("$.tamanhoCamisetaDescricao", equalTo(inscricao.getTamanhoCamiseta().getDescricao())))
				;
		verify(inscricaoService, atLeastOnce()).salvar(any());
	}
	
	/**
	 * atualizar: verifica se lança erro ao atualizar inscricao de outro evento
	 */	
	@Test
	public void testAtualizarErroVinculo() throws Exception{
		Evento evento = new EventoDummy();
		Inscricao inscricao = new InscricaoDummy(evento);
		//vo para post
		InscricaoVOPost inscricaoVO = new InscricaoVOPost();
		inscricaoVO.setCpf(inscricao.getCpf());
		inscricaoVO.setNome(inscricao.getNome());
		inscricaoVO.setEmail(inscricao.getEmail());
		inscricaoVO.setTamanhoCamiseta(inscricao.getTamanhoCamiseta().name());
		Evento outroEvento = new EventoDummy();
		outroEvento.setId(2L);
				
		when(eventoService.buscarPorId(outroEvento.getId())).thenReturn(outroEvento);
		when(inscricaoService.buscarPorId(inscricao.getId())).thenReturn(inscricao);
		this.mockMvc
			.perform(put(BASE_URL+"/{idInscricao}", outroEvento.getId(), inscricao.getId())
					.contentType(KGlobal.APPLICATION_JSON_UTF8)
					.content(TestHelper.convertObjectToJsonString(inscricaoVO))	
					)
				.andExpect(status().isBadRequest())
				;
		
		verify(inscricaoService, never()).salvar(any());
	}
	
	/**
	 * atualizar: verifica se lança erro ao atualizar evento inexistente
	 */	
	@Test
	public void testAtualizarEventoInexistente() throws Exception{
		Evento evento = new EventoDummy();
		Inscricao inscricao = new InscricaoDummy(evento);
		//vo para post
		InscricaoVOPost inscricaoVO = new InscricaoVOPost();
		inscricaoVO.setCpf(inscricao.getCpf());
		inscricaoVO.setNome(inscricao.getNome());
		inscricaoVO.setEmail(inscricao.getEmail());
		inscricaoVO.setTamanhoCamiseta(inscricao.getTamanhoCamiseta().name());
				
		when(eventoService.buscarPorId(evento.getId())).thenReturn(null);
		when(inscricaoService.buscarPorId(inscricao.getId())).thenReturn(inscricao);
		this.mockMvc
			.perform(put(BASE_URL+"/{idInscricao}", evento.getId(), inscricao.getId())
					.contentType(KGlobal.APPLICATION_JSON_UTF8)
					.content(TestHelper.convertObjectToJsonString(inscricaoVO))	
					)
				.andExpect(status().isNotFound());
		verify(inscricaoService, never()).salvar(any());
	}
	
	/**
	 * atualizar: verifica se lança erro ao atualizar inscricao inexistente
	 */	
	@Test
	public void testAtualizarInscricaoInexistente() throws Exception{
		Evento evento = new EventoDummy();
		Inscricao inscricao = new InscricaoDummy(evento);
		//vo para post
		InscricaoVOPost inscricaoVO = new InscricaoVOPost();
		inscricaoVO.setCpf(inscricao.getCpf());
		inscricaoVO.setNome(inscricao.getNome());
		inscricaoVO.setEmail(inscricao.getEmail());
		inscricaoVO.setTamanhoCamiseta(inscricao.getTamanhoCamiseta().name());
				
		when(eventoService.buscarPorId(evento.getId())).thenReturn(evento);
		when(inscricaoService.buscarPorId(inscricao.getId())).thenReturn(null);
		this.mockMvc
			.perform(put(BASE_URL+"/{idInscricao}", evento.getId(), inscricao.getId())
					.contentType(KGlobal.APPLICATION_JSON_UTF8)
					.content(TestHelper.convertObjectToJsonString(inscricaoVO))	
					)
				.andExpect(status().isNotFound());
		verify(inscricaoService, never()).salvar(any());
	}
	
	/**
	 * atualizar: verifica se lança erro de validação de campos (entidade inválida)
	 */	
	@Test
	public void testAtualizarCamposInvalidos() throws Exception{
		Evento evento = new EventoDummy();
		Inscricao inscricao = new InscricaoDummy(evento);
		//vo para post
		InscricaoVOPost inscricaoVO = new InscricaoVOPost();
		inscricaoVO.setCpf(null);
		inscricaoVO.setNome(inscricao.getNome());
		inscricaoVO.setEmail(inscricao.getEmail());
		inscricaoVO.setTamanhoCamiseta(inscricao.getTamanhoCamiseta().name());
				
		when(eventoService.buscarPorId(evento.getId())).thenReturn(evento);
		when(inscricaoService.buscarPorId(inscricao.getId())).thenReturn(inscricao);
		this.mockMvc
			.perform(put(BASE_URL+"/{idInscricao}", evento.getId(), inscricao.getId())
					.contentType(KGlobal.APPLICATION_JSON_UTF8)
					.content(TestHelper.convertObjectToJsonString(inscricaoVO))	
					)
				.andExpect(status().isUnprocessableEntity())
				.andExpect(jsonPath("$.code", equalTo(InvalidEntityException.class.getSimpleName())));
		verify(inscricaoService, never()).salvar(any());
		
	}
	
	
	/**
	 * excluir: verifica se exclui corretamente
	 */	
	@Test
	public void testExcluir() throws Exception{
		Evento evento = new EventoDummy();
		Inscricao inscricao = new InscricaoDummy(evento);
				
		when(eventoService.buscarPorId(evento.getId())).thenReturn(evento);
		when(inscricaoService.buscarPorId(inscricao.getId())).thenReturn(inscricao);
		this.mockMvc
			.perform(delete(BASE_URL+"/{idInscricao}", evento.getId(), inscricao.getId()))
				.andExpect(status().isOk());
		verify(inscricaoService, atLeastOnce()).excluir(inscricao.getId());
	}
	
	/**
	 * excluir: verifica se lança erro ao excluir inscricao de outro evento
	 */	
	@Test
	public void testExcluirErroVinculo() throws Exception{
		Evento evento = new EventoDummy();
		Inscricao inscricao = new InscricaoDummy(evento);
		Evento outroEvento = new EventoDummy();
		outroEvento.setId(2L);
				
		when(eventoService.buscarPorId(outroEvento.getId())).thenReturn(outroEvento);
		when(inscricaoService.buscarPorId(inscricao.getId())).thenReturn(inscricao);
		this.mockMvc
			.perform(delete(BASE_URL+"/{idInscricao}", outroEvento.getId(), inscricao.getId()))
				.andExpect(status().isBadRequest());
		
		verify(inscricaoService, never()).excluir(inscricao.getId());
	}
	
	/**
	 * excluir: verifica se lança erro ao excluir evento inexistente
	 */	
	@Test
	public void testExcluirEventoInexistente() throws Exception{
		Evento evento = new EventoDummy();
		Inscricao inscricao = new InscricaoDummy(evento);
				
		when(eventoService.buscarPorId(evento.getId())).thenReturn(null);
		when(inscricaoService.buscarPorId(inscricao.getId())).thenReturn(inscricao);
		this.mockMvc
			.perform(delete(BASE_URL+"/{idInscricao}", evento.getId(), inscricao.getId()))
				.andExpect(status().isNotFound());
		verify(inscricaoService, never()).excluir(inscricao.getId());
	}
	
	/**
	 * atualizar: verifica se lança erro ao atualizar inscricao inexistente
	 */	
	@Test
	public void testExcluirInscricaoInexistente() throws Exception{
		Evento evento = new EventoDummy();
		Inscricao inscricao = new InscricaoDummy(evento);
				
		when(eventoService.buscarPorId(evento.getId())).thenReturn(evento);
		when(inscricaoService.buscarPorId(inscricao.getId())).thenReturn(null);
		this.mockMvc
			.perform(delete(BASE_URL+"/{idInscricao}", evento.getId(), inscricao.getId()))
				.andExpect(status().isNotFound());
		verify(inscricaoService, never()).excluir(inscricao.getId());
	}
}