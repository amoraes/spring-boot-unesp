package br.unesp.exemplo.api.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.unesp.exemplo.api.valueobjects.PessoaVO;

/**
 * RESTful Resource Pessoas da API
 * @author Alessandro Moraes
 */
@RestController
@RequestMapping("/pessoas")
public class PessoasController {
	
	/**
	 * Buscar uma pessoa, única opção neste caso é CPF
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public List<PessoaVO> filtrar(@RequestParam("cpf") String cpf){
		List<PessoaVO> list = new ArrayList<>();
		//TODO buscar no serviço do RH ou Acadêmico
		if(cpf.equals("30568397851")){
			list.add(new PessoaVO("Alessandro Moraes", "amoraes@feb.unesp.br", "30568397851"));
		}
		return list;
	}

}
