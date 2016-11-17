package br.unesp.exemplo.api.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.unesp.exemplo.api.valueobjects.TamanhoCamisetaVO;
import br.unesp.exemplo.entities.enums.TamanhoCamiseta;

/**
 * RESTful Resource Tamanho de Camisetas da API
 * @author Alessandro Moraes
 */
@RestController
@RequestMapping("/camisetas/tamanhos")
public class TamanhoCamisetaController {
	
	/**
	 * Lista todos os Tamanhos
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public List<TamanhoCamisetaVO> listar(){
		List<TamanhoCamisetaVO> list = new ArrayList<>();
		for(TamanhoCamiseta t : TamanhoCamiseta.values()){
			list.add(new TamanhoCamisetaVO(t.name(), t.getDescricao()));
		}
		return list;
	}

}
