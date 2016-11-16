package br.unesp.exemplo.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogoutController {
	
	@Value("${unespauth.base-url}")
	public String UNESPAUTH_BASE_URL;

	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest req, HttpServletResponse reponse){
		req.getSession().invalidate();
		SecurityContextHolder.getContext().setAuthentication(null);
		return "redirect:"+UNESPAUTH_BASE_URL;
	}
	
}
