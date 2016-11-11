package br.unesp.exemplo.entities;

import br.unesp.exemplo.entities.enums.TamanhoCamiseta;

/**
 * RESTful Classe que gera dados para os testes
 * @author Alessandro Moraes
 */
public class InscricaoDummy extends Inscricao {
	
	private static final long serialVersionUID = -5586453492942822072L;

	public InscricaoDummy() {
		super.setId(1L);
		super.setCpf("42563741327");
		super.setNome("Alessandro Moraes");
		super.setEmail("alessandro.moraes@unesp.br");
		super.setTamanhoCamiseta(TamanhoCamiseta.M);
	}

}