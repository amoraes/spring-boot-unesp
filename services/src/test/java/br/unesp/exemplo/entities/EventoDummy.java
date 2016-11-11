package br.unesp.exemplo.entities;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

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
		Calendar calendar = Calendar.getInstance(Locale.getDefault());
		calendar.set(2016, 11, 1);
		super.setInicio(calendar.getTime());
		calendar.set(2016, 11, 10);
		super.setTermino(calendar.getTime());
		calendar.set(2016, 10, 1);
		super.setInicioInscricao(calendar.getTime());
		calendar.set(2016, 10, 15);
		super.setTerminoInscricao(calendar.getTime());
	}

}