package br.unesp.exemplo.entities;

import java.util.GregorianCalendar;

/**
 * RESTful Classe que gera dados para os testes
 * @author Alessandro Moraes
 */
public class EventoDummy extends Evento {
	
	private static final long serialVersionUID = -5586453492942822072L;

	public EventoDummy() {
		super.setId(1L);
		super.setTitulo("Evento XYZ");
		super.setLocal("Faculdade de Engenharia de Bauru - Sala 101");
		super.setEmail("eventoxyz@feb.unesp.br");
		super.setInicio(new GregorianCalendar(2016, 12, 01).getTime());
		super.setTermino(new GregorianCalendar(2016, 12, 10).getTime());
		super.setInicioInscricao(new GregorianCalendar(2016, 11, 01).getTime());
		super.setTerminoInscricao(new GregorianCalendar(2016, 11, 15).getTime());
	}

}