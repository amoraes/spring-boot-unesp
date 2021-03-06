package br.unesp.exemplo;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.springframework.http.MediaType;

/**
 * Constantes
 * @author Alessandro Moraes
 */
public class KGlobal {
	public static final String DATE_FORMAT = "dd/MM/yyyy";
	public static final String TIMEZONE = "GMT-2";
	public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT);
	public static final MediaType APPLICATION_JSON_UTF8 = 
								new MediaType(MediaType.APPLICATION_JSON.getType(),
											MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
}
