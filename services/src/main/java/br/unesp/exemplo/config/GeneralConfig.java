package br.unesp.exemplo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configurações gerais que não necessitam de customização. 
 * @author Alessandro Moraes
 */
@Configuration //esta classe é uma configuração e será carregada durante a inicialização da aplicação
@EnableResourceServer //habilita esta aplicação como servidor de recursos RESTful
@EnableTransactionManagement //habilita gerenciamento de transações (sucesso = commit, exception = rollback)
public class GeneralConfig {

}
