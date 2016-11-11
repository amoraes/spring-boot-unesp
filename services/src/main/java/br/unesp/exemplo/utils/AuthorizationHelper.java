package br.unesp.exemplo.utils;

import java.util.Collection;
import java.util.HashMap;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;

/**
 * Helper para verificar se o usuário possui determinado privilégio
 * @author Alessandro Moraes
 */
@Component
public class AuthorizationHelper {
	
	/**
	 * Verifica se o usuário logado possui determinada role de acesso
	 * @param role
	 * @return
	 */
	public boolean hasRole(String role) {
		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder.getContext()
				.getAuthentication().getAuthorities();
		boolean hasRole = false;
		for (GrantedAuthority authority : authorities) {
			hasRole = authority.getAuthority().equals(role);
			if (hasRole) {
				break;
			}
		}
		return hasRole;
	}
	
	/**
	 * Verifica se a autenticação é do tipo client credentials
	 * @return
	 */
	public boolean isClientOnly(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null && auth instanceof OAuth2Authentication){
			OAuth2Authentication oauth = (OAuth2Authentication)auth;
			if(oauth.getUserAuthentication() != null && oauth.getUserAuthentication() instanceof UsernamePasswordAuthenticationToken){
				UsernamePasswordAuthenticationToken upt = (UsernamePasswordAuthenticationToken)oauth.getUserAuthentication();
				HashMap<Object, Object> details = (HashMap<Object, Object>) upt.getDetails();
				boolean clientOnly = (Boolean)details.get("clientOnly");
				return clientOnly;
			}
		}
		return false;
	}
	
	/**
	 * Retorna o nome do client logado (somente para autenticação do tipo client credentials)
	 * @return
	 */
	public String getClientId(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(isClientOnly()){
			return ((OAuth2Authentication)auth).getName();
		}
		return null;
	}
}
