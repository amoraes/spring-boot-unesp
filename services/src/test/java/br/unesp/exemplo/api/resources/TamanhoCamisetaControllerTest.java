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
import br.unesp.exemplo.api.valueobjects.TamanhoCamisetaVO;
import br.unesp.exemplo.entities.Evento;
import br.unesp.exemplo.entities.EventoDummy;
import br.unesp.exemplo.entities.enums.TamanhoCamiseta;
import br.unesp.exemplo.exceptions.InvalidEntityException;
import br.unesp.exemplo.services.EventoService;
import br.unesp.exemplo.utils.RestErrorsControllerAdvice;
import br.unesp.exemplo.utils.TestHelper;

public class TamanhoCamisetaControllerTest {
	
	private MockMvc mockMvc;
	
	private final String BASE_URL = "/camisetas/tamanhos";
	
	@InjectMocks
	private TamanhoCamisetaController tamanhoCamisetaController;
	
	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
		RestErrorsControllerAdvice advice = new RestErrorsControllerAdvice();
		this.mockMvc = standaloneSetup(tamanhoCamisetaController)
				.setControllerAdvice(advice)
			.build();
	}
	
	/**
	 * listar: verifica se retorna corretamente
	 */
	@Test
	public void testListar() throws Exception{
		this.mockMvc
			.perform(get(BASE_URL+"/")
				.accept(KGlobal.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].tamanho", equalTo(TamanhoCamiseta.P.name())))
				.andExpect(jsonPath("$[0].descricao", equalTo(TamanhoCamiseta.P.getDescricao())));
	}
			
}