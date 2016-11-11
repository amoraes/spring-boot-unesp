package br.unesp.exemplo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class ServicesApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ServicesApplication.class, args);
	}
	
	/** Configuração necessária para deploy em Tomcat via aqruivo .war **/
    private static Class<ServicesApplication> applicationClass = ServicesApplication.class;
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }
}
