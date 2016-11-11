package br.unesp.exemplo.exceptions;

/**
 * Exception para tratamento de erros de entidades não encontradas
 * @author Alessandro Moraes
 */
public class NotFoundException extends Exception {

	private static final long serialVersionUID = 3584501147718284777L;

	public NotFoundException(String entityName){
		super(entityName+" não encontrado(a)!");
	}
	
	public NotFoundException(Class<?> entity){
		this(entity.getSimpleName());
	}
	
	public NotFoundException(String entityName, long entityId){
		super(entityName+"["+entityId+"]  não encontrado(a)!");
	}
	
	public NotFoundException(Class<?> entity, long entityId){
		this(entity.getSimpleName(), entityId);
	}
	
	public NotFoundException(String entityName, String field, String value){
		super(entityName+"[" + field + "=" + value + "]  não encontrado(a)!");
	}
	
	public NotFoundException(Class<?> entity, String field, String value){
		this(entity.getSimpleName(), field, value);
	}
	
	public NotFoundException(String entityName, String[] fields, Object[] values){
		super(createMessageWithMultipleFields(entityName, fields, values));
	}
	
	public NotFoundException(Class<?> entity, String[] fields, Object[] values){
		this(entity.getSimpleName(), fields, values);
	}
	
	private static String createMessageWithMultipleFields(String entityName, String[] field, Object[] values) {
		StringBuilder msg = new StringBuilder(entityName).append("[");
		for (int i = 0; i < field.length; i++) {
			msg.append(field[i]).append("=").append(values[i]);
			if (i < (field.length - 1)) {
				msg.append(", ");
			}
		}
		msg.append("]  não encontrado(a)!");
		
		return msg.toString();
	}
	
}