package br.unesp.exemplo.exceptions;

/**
 * Exceção para tratamento de erros lógicos previstos no sistema (Ex.: E-mail já cadastrado)
 * @author Alessandro Moraes
 */
public class ServiceValidationException extends Exception {

	private static final long serialVersionUID = -1653277188052905881L;

	public ServiceValidationException(String msg){
		super(msg);
	}
}
