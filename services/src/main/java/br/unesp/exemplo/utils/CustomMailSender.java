package br.unesp.exemplo.utils;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import br.unesp.exemplo.entities.Inscricao;

/**
 * Envio de notificações para usuários através de e-mail
 * @author Alessandro Moraes
 */
@Component
public class CustomMailSender {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
    private VelocityEngine velocityEngine;

	@Value("${spring.mail.from}")
	private String mailFrom;
		
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}
	
	/**
	 * Envia e-mail de confirmação da inscrição
	 * @param inscricao
	 * @throws MessagingException
	 */
	public void sendConfirmacaoInscricao(Inscricao inscricao) throws MessagingException {
		Map<String, Object> params = new HashMap<>();
		params.put("nome", inscricao.getNome());
		params.put("evento", inscricao.getEvento().getTitulo());
		params.put("idInscricao", inscricao.getId());
		send("email_inscricao.html","[SisICA] Inscrição confirmada", inscricao.getEmail(), params);
	}
	
	/**
	 * Envia o e-mail
	 * @param template
	 * @param subject
	 * @param message
	 * @param to
	 * @param params
	 * @throws MessagingException
	 */
	@SuppressWarnings("deprecation") //está marcado como deprecated, mas o substituto é o FreeMarker, não muito legal
	private void send(String template, String subject, String to, Map<String,Object> params) throws MessagingException {
		String htmlMsg = VelocityEngineUtils.mergeTemplateIntoString(
	            velocityEngine, "templates/"+template, "utf-8", params);
	
	    Multipart multipart = new MimeMultipart("related");
	    
	    MimeBodyPart messageBodyPart = new MimeBodyPart();
	    messageBodyPart.setContent(htmlMsg, "text/html");
	    multipart.addBodyPart(messageBodyPart);

	    MimeMessage mail = javaMailSender.createMimeMessage();
	    
	    mail.setRecipients(RecipientType.TO, to);
    	mail.setSubject(subject);
    	mail.setFrom(mailFrom);
    	mail.setContent(multipart);

    	javaMailSender.send(mail);
    }
}
