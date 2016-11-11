package br.unesp.exemplo.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.unesp.exemplo.KGlobal;
import br.unesp.exemplo.exceptions.InvalidEntityException;
import br.unesp.exemplo.exceptions.NotFoundException;
import br.unesp.exemplo.exceptions.ServiceValidationException;

/**
 * ControllerAdvice para tratamento das exceções, gerando um retorno coerente com a exceção
 * em um formato apropriado (JSON)
 * @author Alessandro Moraes
 */
@ControllerAdvice
public class RestErrorsControllerAdvice extends ResponseEntityExceptionHandler 
{
	private final Logger log = LoggerFactory.getLogger(RestErrorsControllerAdvice.class);
	
	/**
	 * This handler transforms any NotFoundException thrown by controllers into a 404 HTTP response
	 * @param ex
	 */
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<?> handleException(NotFoundException ex){
		log.debug("Handling NotFoundException: "+ex.getMessage());
		return ResponseEntity.notFound().build();
	}
	
	/**
	 * This handler transforms any IllegalArgumentException thrown by controllers into a 400 HTTP response
	 * @param ex
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> handleException(IllegalArgumentException ex){
		log.debug("Handling "+ex.getClass().getSimpleName()+":"+ex.getMessage());
		ErrorResource error = new ErrorResource();
        error.setCode(ex.getClass().getSimpleName());
        error.setMessage("Parâmetro inválido");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(KGlobal.APPLICATION_JSON_UTF8);
        return ResponseEntity.badRequest().headers(headers).body(error);
	}
	
	/**
	 * This handler transforms any ServiceValidationException thrown by controllers into a 400 HTTP response
	 * @param ex
	 */
	@ExceptionHandler(ServiceValidationException.class)
	public ResponseEntity<?> handleException(ServiceValidationException ex){
		log.debug("Handling "+ex.getClass().getSimpleName()+":"+ex.getMessage());
		ErrorResource error = new ErrorResource();
        error.setCode(ex.getClass().getSimpleName());
        error.setMessage(ex.getMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(KGlobal.APPLICATION_JSON_UTF8);
        return ResponseEntity.badRequest().headers(headers).body(error);
	}

	/**
	 * This handler transforms any InvalidRequestException thrown by controllers into a 422 HTTP response (Unprocessable Entity)
	 * @param ex
	 * @throws IOException 
	 */
	@ExceptionHandler(InvalidEntityException.class)
    @ResponseBody
    public ResponseEntity<?> handleException(InvalidEntityException ex, HttpServletRequest request) {
		log.debug("Handling InvalidEntityException: "+ex.getMessage()+" (Field Errors: "+ex.getErrors().getFieldErrors().size()+")");
		List<FieldErrorResource> fieldErrorResources = new ArrayList<>();
        List<FieldError> fieldErrors = ex.getErrors().getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            FieldErrorResource fieldErrorResource = new FieldErrorResource();
            String objectName = fieldError.getObjectName();
            if(objectName.endsWith("VO")){
            	objectName = objectName.substring(0, objectName.length()-2);
            }
            fieldErrorResource.setResource(objectName);
            fieldErrorResource.setField(fieldError.getField());
            fieldErrorResource.setCode(fieldError.getCode());
            fieldErrorResource.setMessage(fieldError.getDefaultMessage());
            fieldErrorResources.add(fieldErrorResource);
        }
        ErrorResource error = new ErrorResource();
        error.setCode(InvalidEntityException.class.getSimpleName());
        error.setMessage(ex.getMessage());
        error.setFieldErrors(fieldErrorResources);     
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(KGlobal.APPLICATION_JSON_UTF8);
        
        return ResponseEntity.unprocessableEntity().headers(headers).body(error);
    }
	
}

/**
 * Representação para retorno em JSON de erros de validação de campos, argumentos inválidos e erros lógicos
 * @author Alessandro Moraes
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value=Include.NON_EMPTY)
class ErrorResource {
	
	private String code;
    private String message;
    private List<FieldErrorResource> fieldErrors;

    public ErrorResource() { }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<FieldErrorResource> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<FieldErrorResource> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}
}

/**
 * Representação para retorno em JSON de erros específicos de cada campo de uma entidade
 * @author Alessandro Moraes
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class FieldErrorResource {
	
	private String resource;
    private String field;
    private String code;
    private String message;
    
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}