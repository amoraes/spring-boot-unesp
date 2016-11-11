package br.unesp.exemplo.exceptions;

import java.lang.annotation.Annotation;

import javax.validation.Valid;

import org.springframework.validation.Errors;

import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Exception para tratamento de erros de binding do Spring ({@link Valid})
 * @author Alessandro Moraes
 */
public class InvalidEntityException extends RuntimeException {
	
	private static final long serialVersionUID = 3404217895647005909L;
	
	private String entityName;
	private Errors errors;

    public InvalidEntityException(String entityName, Errors errors) {
    	this.entityName = entityName;
        this.errors = errors;
    }
    
    public InvalidEntityException(Class<?> klass, Errors errors){
    	this(getEntityNameFromVO(klass), errors);
    }

    public String getEntityName() { return entityName; }
    
    public Errors getErrors() { return errors; }
    
    private static String getEntityNameFromVO(Class<?> klass){
    	Annotation ann = klass.getAnnotation(JsonTypeName.class);
    	if(ann == null){
    		throw new RuntimeException("Adicione a anotação @JsonTypeName na classe "+klass.getName());
    	}
    	return ((JsonTypeName)ann).value();
    }
    
    
}