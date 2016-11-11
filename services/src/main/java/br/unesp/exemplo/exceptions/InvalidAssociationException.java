package br.unesp.exemplo.exceptions;

/**
 * Exceção para tratamentos de associações errôneas (alterar perfil 10 no sistema 1, sendo que o sistema 1 não é dono do perfil 10)
 * @author Alessandro Moraes
 */
@SuppressWarnings("serial")
public class InvalidAssociationException extends IllegalArgumentException {
	
	private Object child;
	private Object parent;
	
	public InvalidAssociationException(Object child, Object parent){
		this.child = child;
		this.parent = parent;
	}

	public Object getChild() {
		return child;
	}

	public Object getParent() {
		return parent;
	}

	@Override
	public String getMessage() {
		return String.format("%s não pertence a %s", child, parent);
	}
	
	
	
}
