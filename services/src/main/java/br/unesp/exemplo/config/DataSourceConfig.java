package br.unesp.exemplo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Configuração do Pool de Conexões com o Banco de Dados baseado nas
 * propriedades definiadas no application.properties
 * @author Alessandro Moraes
 */
@Configuration //esta classe é uma configuração e será carregada durante a inicialização da aplicação
public class DataSourceConfig {

	@Value("${spring.datasource.name}")
	private String dataSourceName;
	
	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;
	
	@Value("${spring.datasource.url}")
	private String url;

	@Value("${spring.datasource.connection-timeout:30000}")
	private long connectionTimeout;

	@Value("${spring.datasource.idle-timeout:600000}")
	private long idleTimeout;

	@Value("${spring.datasource.max-lifetime:600000}")
	private long maxLifetime;

	@Value("${spring.datasource.min-idle:1}")
	private int minIdle;

	@Value("${spring.datasource.maximum-pool-size:100}")
	private int maxPoolSize;
	
	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;
	
	@Bean
	@Primary
	public DataSource dataSource(){
		HikariConfig config = new HikariConfig();
		config.setPoolName(dataSourceName);
		config.setUsername(username);
		config.setPassword(password);
		config.setDriverClassName(driverClassName);
		config.setJdbcUrl(url);
		config.setConnectionTimeout(connectionTimeout);
		config.setIdleTimeout(idleTimeout);
		config.setMaxLifetime(maxLifetime);
		config.setMinimumIdle(minIdle);
		config.setMaximumPoolSize(maxPoolSize);
		return new HikariDataSource(config);
	}
}
