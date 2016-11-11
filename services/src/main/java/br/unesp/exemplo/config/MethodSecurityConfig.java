package br.unesp.exemplo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;

/**
 * Configuração da segurança individual dos métodos da aplicação baseado nas Roles ou Scopes
 * do usuário autenticado por OAuth 2.0
 * @author Alessandro Moraes
 */
@Configuration //esta classe é uma configuração e será carregada durante a inicialização da aplicação
@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true, proxyTargetClass=true) //habilitar segurança nos métodos
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration{

	@Override
	protected MethodSecurityExpressionHandler createExpressionHandler() {
		return new OAuth2MethodSecurityExpressionHandler();
	}
	
}