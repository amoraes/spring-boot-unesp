package br.unesp.exemplo.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.stereotype.Component;

import br.unesp.exemplo.utils.CsrfHeaderFilter;

@Configuration
@EnableOAuth2Sso
@EnableZuulProxy
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.logout().logoutUrl("/logout").invalidateHttpSession(true).permitAll();
		http.formLogin().permitAll();
		http.authorizeRequests().antMatchers("/login").permitAll();
		http.authorizeRequests().antMatchers("/bower_components/**").permitAll();
		http.authorizeRequests().antMatchers("/js/**").permitAll();
		http.authorizeRequests().antMatchers("/css/**").permitAll();
		http.authorizeRequests().antMatchers("/img/**").permitAll();
		http.authorizeRequests().antMatchers("/views/**").permitAll();
		http.authorizeRequests().antMatchers("/services-public/**").permitAll();
		http.authorizeRequests().antMatchers("/sessaoTerminada.html").permitAll();
		http.authorizeRequests().antMatchers("/index.html", "/")
        .permitAll().anyRequest().authenticated()
        .and().csrf().csrfTokenRepository(CsrfHeaderFilter.csrfTokenRepository())
        .and().addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class);

	}
	
	@Bean
	@Autowired
    public FilterRegistrationBean registerSessionTimeoutFilter(SessionTimeoutFilter filter) {
        FilterRegistrationBean reg = new FilterRegistrationBean(filter);
        reg.setOrder(3);
        reg.addUrlPatterns("/login");
        return reg;
    }
}

@Component
class SessionTimeoutFilter implements Filter {
	
	@Value("${server.session.timeout}")
	private int sessionTimeout;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		if(request.getSession(false) != null){
			request.getSession().setMaxInactiveInterval(sessionTimeout);
		}
		chain.doFilter(req, res);
	}

	public void init(FilterConfig filterConfig) {}

	public void destroy() {}

}

