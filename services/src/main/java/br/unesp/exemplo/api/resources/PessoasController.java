package br.unesp.exemplo.api.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.unesp.exemplo.api.valueobjects.PessoaVO;
import br.unesp.exemplo.webservices.RHWebService;

/**
 * RESTful Resource Pessoas da API
 * @author Alessandro Moraes
 */
@RestController
@RequestMapping("/pessoas")
public class PessoasController {
	
	@Autowired
	private RHWebService rhWebService;
	
	/**
	 * Buscar uma pessoa, única opção neste caso é CPF
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public List<PessoaVO> filtrar(@RequestParam("cpf") String cpf){
		List<PessoaVO> list = new ArrayList<>();
		PessoaVO pessoa = rhWebService.buscarPessoaPorCPF(cpf);
		if(pessoa != null){
			list.add(pessoa);
		}
		return list;
	}

}
